let email;
let nickName = null;
let token = null;
let oldSetMessageId = null;
let chattingroomsId = null;
let stompClient = null;
let chatSubscriptions = null;
let readSubscriptions = null;
let page = 1;
const size = 30;

document.addEventListener('DOMContentLoaded',
    () => {
        token = getToken();

        fetch('/api/user-info', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 좋지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                email = data.data.username;
                nickName = data.data.nickName;
                document.getElementById('userName').innerText = nickName;
                connectToSocket();
            })

        function connectToSocket() {
            const socket = new SockJS('/chatting', null, {transports: ["websocket", "xhr-streaming", "xhr-polling"]});
            stompClient = Stomp.over(socket);

            let headers = {Authorization: 'Bearer ' + token};

            stompClient.connect(headers, (frame) => {

                    stompClient.subscribe('/topic/user/' + email, (message) => {
                        const msg = JSON.parse(message.body);
                        const existingItem = document.getElementById('chat-room-' + msg.chattingRoomId);

                        var unreadCountElement;
                        if (existingItem) {
                            // 읽지 않은 메시지 수 업데이트
                            if (chattingroomsId !== msg.chattingRoomId) {
                                let unreadCountElement = existingItem.querySelector('.unread-count.hidden');
                                if (unreadCountElement) {
                                    // 'hidden' 클래스를 제거합니다.
                                    unreadCountElement.className = unreadCountElement.className.replace(' hidden', '');
                                } else {
                                    // 'hidden' 클래스가 없는 '.unread-count' 요소를 찾습니다.
                                    unreadCountElement = existingItem.querySelector('.unread-count');
                                }

                                let currentUnreadCount = 0;
                                if (unreadCountElement) {
                                    currentUnreadCount = parseInt(unreadCountElement.textContent) || 0;
                                }
                                const newUnreadCount = currentUnreadCount + 1;
                                unreadCountElement.textContent = newUnreadCount > 0 ? newUnreadCount : ''; // 0일 경우 빈 문자열로
                            } else {
                                unreadCountElement = existingItem.querySelector('.unread-count');
                                unreadCountElement.textContent = '';

                            }


                            // 메시지 내용 업데이트 (chat-preview 클래스를 가진 요소)
                            const messageElement = existingItem.querySelector('.chat-preview');
                            messageElement.textContent = msg.message || ''; // 새로운 메시지 내용으로 업데이트

                            // 마지막 타임스탬프 업데이트
                            const lastTimestampElement = existingItem.querySelector('.chat-lastTimeStamp');
                            lastTimestampElement.textContent = msg.timestamp
                                ? new Date(msg.timestamp).toLocaleTimeString([], {
                                    hour: '2-digit',
                                    minute: '2-digit'
                                })
                                : '';
                            // 채팅룸 리스트 아이템을 맨 위로 이동
                            const chatList = document.querySelector('.chat-list');
                            chatList.insertBefore(existingItem, chatList.firstChild);
                        } else {
                            // 기존 아이템이 없을 때 fetch 요청
                            fetch(`/api/chattingrooms/${msg.chattingRoomId}`, {
                                method: 'GET',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Bearer ' + token
                                }
                            })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('네트워크 응답이 실패했습니다.');
                                    }
                                    return response.json();
                                })
                                .then(data => {
                                        // 새 채팅 아이템 생성 로직
                                        let newResponse = data.data;
                                        const chatList = document.querySelector('.chat-list');
                                        const listItem = document.createElement('li');
                                        listItem.className = 'chat-item';
                                        listItem.id = 'chat-room-' + newResponse.chattingRoomId;

                                        // 새로운 메시지 내용으로 설정
                                        listItem.innerHTML = `
                                <img src="../images/minji.jfif" alt="Avatar" class="chat-avatar">
                                <div class="chat-info">
                                    <div class="chat-name">
                                        ${newResponse.partnerNickname} <span class="unread-count">${newResponse.unReadMessageCount}</span>
                                    </div>
                                    <div class="chat-preview">${newResponse.lastMessage}</div>
                                </div>
                                <div class="chat-lastTimeStamp">${new Date(newResponse.lastTimestamp).toLocaleTimeString([], {
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        })}</div>
                            `;
                                        chatList.insertBefore(listItem, chatList.firstChild);
                                    }
                                )
                                .catch(error => {
                                    console.error('Fetch 중 오류 발생:', error);
                                });
                        }

                    })

                }

                ,
                (error) => {
                    console.error("소켓 연결 에러", error);
                }
            );
        }
        chattingRoomList(page, size);


    });


function chattingRoomList(page, size){
    const chattingRoomUrl = `/api/chattingrooms?page=${page}&size=${size}`;

    fetch(chattingRoomUrl, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            const chatList = document.querySelector('.chat-list');
            const chats = data.data;
            if(chats.length <= 0 ){
                document.getElementById('loadMoreChats').style.display = 'none';
                return;
            }else{
                document.getElementById('loadMoreChats').style.display = 'block';
            }
            chats.forEach(chat => {
                const listItemId ='chat-room-' + chat.chattingRoomId;
                if(document.getElementById(listItemId)){
                    return;
                }

                const listItem = document.createElement('li');
                listItem.className = 'chat-item';
                listItem.id = 'chat-room-' + chat.chattingRoomId;
                listItem.onclick = () => openChat(chat.chattingRoomId, chat.partnerNickname); // 클릭 시 openChat 호출

                const unreadCount =
                    `<span class="unread-count${chat.unReadMessageCount === 0 ||
                    chat.unReadMessageCount === null ? ' hidden' : ''}">
                      ${chat.unReadMessageCount !== null ? chat.unReadMessageCount : ''}
                    </span>`;
                const lastTimestamp = chat.lastTimestamp
                    ? new Date(chat.lastTimestamp).toLocaleTimeString([], {
                        hour: '2-digit',
                        minute: '2-digit'
                    })
                    : '';

                listItem.innerHTML = `
                <img src="../images/minji.jfif" alt="Avatar" class="chat-avatar">
                <div class="chat-info">
                    <div class="chat-name">
                        ${chat.partnerNickname} ${unreadCount}
                    </div>
                    <div class="chat-preview">${chat.lastMessage ? chat.lastMessage : ''}</div>
                </div>
                <div class="chat-lastTimeStamp">${lastTimestamp}</div>
            `;

                chatList.appendChild(listItem);
            })
        })
        .catch(error => {
            console.error(error);
        });
}

function loadMoreChats() {
    page++;
    chattingRoomList(page,size);
    console.log(page + " page " + size + " ");
}
function topicChattingRoom(chattingRoomId) {
    chatSubscriptions = stompClient.subscribe('/topic/' + chattingRoomId, (message) => {
        const container = document.querySelector('.chat-messages');

        const msg = JSON.parse(message.body);
        const messageDiv = document.createElement('div');
        const formattedTime = new Date(msg.timestamp).toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit'
        });

        if (msg.email !== email) {
            messageDiv.className = 'message';
            messageDiv.innerHTML = `
                    <div class="message-content" data-message-id="${msg.messageId}">${msg.message}</div>
                    <div class="message-info">${formattedTime}</div>
                `;
            sendMessageRead(chattingRoomId, msg.messageId);
        } else {
            messageDiv.className = 'message user-message';
            messageDiv.innerHTML = `
                    <div class="message-read" data-message-read-id="${msg.messageId}"></div>
                    <div class="message-info">${formattedTime}</div>
                    <div class="message-content" data-message-id="${msg.messageId}">${msg.message}</div>
                `;
        }

        container.prepend(messageDiv);
        container.scrollTop = container.scrollHeight;
    });
}

function sendMessageRead(chattingRoomId, messageId) {
    readSubscriptions = stompClient.send('/app/chattingrooms/read', {}, JSON.stringify({
        token: token,
        chattingRoomId: chattingRoomId,
        messageId: messageId
    }));
}

function topicMessageRead(chattingRoomId) {
    readSubscriptions = stompClient.subscribe('/topic/' + chattingRoomId + '/read/' + email, (message) => {

        const msg = JSON.parse(message.body);

        const existingItem = document.getElementById('chat-room-' + chattingRoomId);
        const unreadCountElement = existingItem.querySelector('.unread-count');

        if (unreadCountElement) {
            unreadCountElement.classList.add('hidden');
            unreadCountElement.textContent = '0';
        }
        const chatMessages = document.getElementById('chatMessages');

        const messageElements = chatMessages.querySelectorAll('.message-read');

        messageElements.forEach((messageReadElement) => {
            const messageReadId = parseInt(messageReadElement.getAttribute('data-message-read-id'), 10);
            if (messageReadId <= msg.messageId) {
                messageReadElement.textContent = '읽음'; // 읽음 처리
            }
        });
    });
}

// 클릭시 채팅방이 열린다.
function openChat(chattingRoomId, partnerNickname) {
    const chatHeader = document.getElementsByClassName('chat-header')[0]; // [0]을 추가하여 첫 번째 요소를 선택
    const button =document.getElementById('loadMoreButton');
    button.style.opacity = '1';
    button.style.pointerEvents = 'auto';
    button.textContent='더보기';
    // chatHeader의 display 속성을 'block'으로 변경
    if (chatHeader) {
        chatHeader.style.display = 'block'; // 또는 'flex', 'grid' 등 필요에 따라 변경
    }
    oldSetMessageId = null;
    chattingroomsId = chattingRoomId;
    const chatMessages = document.getElementById('chatMessages');
    chatMessages.innerHTML = ''; // 이전 내용 삭제
    document.getElementById('chatName').innerText = partnerNickname;
    fetchMessages(chattingRoomId, oldSetMessageId)
        .then(() => {
            const chatMessages = document.getElementById('chatMessages');
            chatMessages.scrollTop = chatMessages.scrollHeight; // 스크롤을 맨 아래로 이동
            setTimeout(() => {
                const firstUserMessage = chatMessages.querySelector('.user-message');
                let messageReadId;
                if (firstUserMessage) {
                    const messageCountElement = firstUserMessage.querySelector('.message-content');
                    messageReadId = messageCountElement.getAttribute('data-message-id');
                } else {
                    messageReadId = null;
                }

                const firstAllMessage = chatMessages.querySelector('.message');
                const allMessageCountElement = firstAllMessage ? firstAllMessage.querySelector('.message-content') : null;
                const allMessageReadId = allMessageCountElement ? allMessageCountElement.getAttribute('data-message-id') : null;


                if (allMessageReadId !== messageReadId) {
                    sendMessageRead(chattingRoomId, allMessageReadId);
                }
            }, 200);
        })

    if (chatSubscriptions) {
        chatSubscriptions.unsubscribe();
        chatSubscriptions = null;
    }
    if (readSubscriptions) {
        readSubscriptions.unsubscribe();
        readSubscriptions = null;
    }
    topicChattingRoom(chattingRoomId);
    topicMessageRead(chattingRoomId);

    const existingItem = document.getElementById('chat-room-' + chattingRoomId);
    const unreadCountElement = existingItem.querySelector('.unread-count');
    unreadCountElement.classList.add("hidden");
    unreadCountElement.textContent = '';
}

function fetchChattingRoomGetPost(chattingRoomId) {
    fetch(`/api/chattingrooms/${chattingRoomId}/post`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            const post = data.data;
            const postHTML = `
                    <div class="post-image">
                        <img src="${post.postFirstUrl ? post.postFirstUrl : '../images/minji.jfif'}" alt="Post Image" />
                    </div>
                    <div class="post-details">
                        <div class="post-title-price">
                            <h3 id="postTitle">${post.postTitle}</h3>
                            <p id="price">가격: ${post.price.toLocaleString()}원</p>
                        </div>
                        <div class="post-status">
                        <span id="postStatus">${post.postStatus === 'INPROGRESS' ? '판매 중' : '판매 완료'}</span>
                        </div>
                    </div>
                `;

            document.getElementById('chatting-room-post').innerHTML = postHTML;
        })
        .catch(error => {
            console.error('chattingRoom get Post', error);
        })
}

function fetchMessages(chattingRoomId, messageId) {
    const url = messageId ? `/api/chattingrooms/${chattingRoomId}/messages?messageId=${messageId}`
        : `/api/chattingrooms/${chattingRoomId}/messages`;
    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
                renderMessages(data);
                if (messageId === null) {
                    fetchChattingRoomGetPost(chattingRoomId);
                }

            const button =document.getElementById('loadMoreButton');
            button.style.display ='block';

                if (data.data.length > 0) {
                    button.style.opacity = '1';
                    button.style.pointerEvents = 'auto'; // 클릭 가능
                } else {
                    button.style.opacity ='0.2';
                    button.style.pointerEvents = 'none'; // 클릭 불가능
                    button.textContent='끝!';
                }
            }
        )
        .catch(error => {
            console.error("API 호출 중 오류 발생", error);
        });
}

function messageOffset(chattingroomsId) {
    return fetch(`/api/chattingrooms/${chattingroomsId}/messagesoffset`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        });
}


function renderMessages(messages) {
    const container = document.querySelector('.chat-messages');

    let message = messages.data;
    oldSetMessageId = null; // 변수를 선언하고 초기화

    if (message.length > 0) {
        oldSetMessageId = message[message.length - 1].messageId;
    }

    messageOffset(chattingroomsId)
        .then(data => {
            const offsetMessageId = data.data.messageOffset.lastReadMessageId; // 수정된 부분

            // 메시지를 렌더링하는 부분을 여기로 이동
            message.forEach(message => {
                // 새로운 그룹을 시작
                const messageDiv = document.createElement('div');
                const formattedTime = new Date(message.timestamp).toLocaleTimeString([], {
                    hour: '2-digit',
                    minute: '2-digit'
                });

                if (message.email !== email) {
                    messageDiv.className = 'message';
                    messageDiv.innerHTML = `
                        <div class="message-content" data-message-id="${message.messageId}">${message.message}</div>
                        <div class="message-info">${formattedTime}</div>
                    `;
                } else {
                    messageDiv.className = 'message user-message';
                    messageDiv.innerHTML = `
                        <div class="message-read" data-message-read-id="${message.messageId}">${offsetMessageId >= message.messageId ? '읽음' : ''}</div>
                        <div class="message-info">${formattedTime}</div>
                        <div class="message-content" data-message-id="${message.messageId}">${message.message}</div>
                    `;
                }

                container.appendChild(messageDiv);
            }); // forEach 닫기
        }) // then 닫기
        .catch(error => {
            console.error('메세지 오프셋 오류 발생:', error);
        });
}


document.addEventListener('DOMContentLoaded', function () {
    const loadMoreButton = document.getElementById('loadMoreButton');
    if (loadMoreButton) {
        loadMoreButton.addEventListener('click', function () {
            fetchMessages(chattingroomsId, oldSetMessageId);
        });
    } else {
        console.error('loadMoreButton not found!');
    }
});

function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value;
    if (message) {
        stompClient.send('/app/message', {}, JSON.stringify({
            token: token,
            chattingRoomId: chattingroomsId,
            message: message
        }));
        messageInput.value = ''; // 입력 필드 초기화
    }
}

document.getElementById('messageInput').addEventListener('keypress', function (e) {
    if (e.key === 'Enter' && !e.shiftKey) {
        // Shift 키가 눌리지 않은 상태에서 Enter 키가 눌리면 메시지를 전송
        e.preventDefault(); // 기본 Enter 키의 동작을 방지
        sendMessage();
    } else if (e.key === 'Enter' && e.shiftKey) {
        // Shift 키가 눌린 상태에서 Enter 키가 눌리면 줄바꿈
        e.preventDefault(); // 기본 Enter 키의 동작을 방지
        const input = document.getElementById('messageInput');
        const start = input.selectionStart;
        const end = input.selectionEnd;

        // 현재 텍스트의 앞부분과 뒷부분을 가져온 후 줄바꿈 추가
        const value = input.value;
        input.value = value.substring(0, start) + '\n' + value.substring(end);

        // 커서를 줄바꿈한 후 텍스트 끝으로 이동
        input.selectionStart = input.selectionEnd = start + 1;
    }
});