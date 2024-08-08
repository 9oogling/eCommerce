
document.addEventListener('DOMContentLoaded', function () {
    const openModalButtons = document.querySelectorAll('.open-modal-button');
    const closeModalButtons = document.querySelectorAll('.close, .close2');

    // Open modal event listeners
    openModalButtons.forEach(button => {
        button.addEventListener('click', function () {
            const targetDialog = document.querySelector(button.getAttribute('data-target'));
            if (targetDialog) {
                targetDialog.showModal();
            }
        });
    });

    // Close button event listeners
    closeModalButtons.forEach(button => {
        button.addEventListener('click', function () {
            const dialog = button.closest('dialog');
            if (dialog && !dialog.classList.contains('no-close')) {
                dialog.classList.add("dialog__animate-out");
                dialog.addEventListener('animationend', () => {
                    dialog.classList.remove("dialog__animate-out");
                    dialog.close();
                    // 모달 닫힐 때 내용 초기화
                    dialog.querySelectorAll('input').forEach(input => input.value = '');
                }, { once: true });
            }
        });
    });

    // Click on background event listener
    document.querySelectorAll('dialog').forEach(dialog => {
        dialog.addEventListener('click', function (event) {
            if (event.target === dialog && !dialog.classList.contains('no-close')) {
                dialog.classList.add("dialog__animate-out");
                dialog.addEventListener('animationend', () => {
                    dialog.classList.remove("dialog__animate-out");
                    dialog.close();
                    // 모달 닫힐 때 내용 초기화
                    dialog.querySelectorAll('input').forEach(input => input.value = '');
                }, { once: true });
            }
        });
    });
});

// 비밀번호 변경 알림
document.addEventListener("DOMContentLoaded", () => {
    const saveButton = document.querySelector(".close1");

    saveButton.addEventListener("click", (event) => {
        event.preventDefault();

        const currentPw = document.getElementById("change0").value;
        const newPw = document.getElementById("change1").value;
        const confirmPw = document.getElementById("change2").value;

        // 비밀번호가 모두 입력되었는지 확인
        if (!currentPw || !newPw || !confirmPw) {
            alert("모든 필드를 입력해 주세요.");
            return;
        }

        // 비밀번호의 유효성을 검사 (영문, 숫자, 특수문자 포함 8~16자)
        const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,16}$/;
        if (!pwPattern.test(newPw)) {
            alert("영문, 숫자, 특수문자 포함하여 8~16자로 입력해 주세요.");
            return;
        }

        // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
        if (newPw !== confirmPw) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        // 서버에 현재 비밀번호를 보내서 확인 (여기에 서버 요청 코드 추가)
        $.ajax({
            url: '/check-password',  // 서버의 비밀번호 확인 엔드포인트
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ currentPw: currentPw }),
            success: function (response) {
                if (response.match === false) {
                    alert("현재 비밀번호가 일치하지 않습니다.");
                    return;
                }

                // 비밀번호 변경이 성공했을 때 모달이 닫히지 않도록 설정
                alert("비밀번호가 성공적으로 변경되었습니다.");
                // 비밀번호 변경 모달을 닫지 않음
            },
            error: function () {
                alert("비밀번호 확인 중 오류가 발생했습니다.");
            }
        });
    });
});

// 주소 변경 모달 저장 버튼 클릭 시 모달 닫히기
document.addEventListener('DOMContentLoaded', () => {
    const saveAddressButton = document.querySelector(".save-address");
    const savePhoneButton = document.querySelector(".save-phone");

    // 주소 변경 저장 버튼 클릭 시
    saveAddressButton.addEventListener("click", (event) => {
        event.preventDefault();
        const dialog = saveAddressButton.closest('dialog');
        if (dialog) {
            dialog.classList.add("dialog__animate-out");
            dialog.addEventListener('animationend', () => {
                dialog.classList.remove("dialog__animate-out");
                dialog.close();
                // 모달 닫힐 때 내용 초기화
                dialog.querySelectorAll('input').forEach(input => input.value = '');
            }, { once: true });
        }
    });

    // 휴대폰 번호 변경 저장 버튼 클릭 시
    savePhoneButton.addEventListener("click", (event) => {
        event.preventDefault();
        const dialog = savePhoneButton.closest('dialog');
        if (dialog) {
            dialog.classList.add("dialog__animate-out");
            dialog.addEventListener('animationend', () => {
                dialog.classList.remove("dialog__animate-out");
                dialog.close();
                // 모달 닫힐 때 내용 초기화
                dialog.querySelectorAll('input').forEach(input => input.value = '');
            }, { once: true });
        }
    });
});

// 휴대폰 번호 변환
const hypenTel = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

// 주소
const addrSearch = () => {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = '';
            let extraAddr = '';

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname))
                    extraAddr += data.bname;

                if (data.buildingName !== '' && data.apartment === 'Y')
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);

                if (extraAddr !== '')
                    extraAddr = ' (' + extraAddr + ')';

                document.getElementById("user_address").value = extraAddr;

            } else {
                document.getElementById("user_address").value = '';
            }

            document.getElementById("user_address").value = addr;
            document.getElementById("user_address").focus();
        }
    }).open();
}

// 상세주소
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            let addr = ''; // 주소 변수
            let extraAddr = ''; // 참고항목 변수

            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

$(document).width();
