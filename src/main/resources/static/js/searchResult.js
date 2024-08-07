$(document).ready(function() {
    // URL 쿼리 파라미터에서 검색 키워드와 타입을 가져오기
    const params = new URLSearchParams(window.location.search);
    const keyword = params.get('keyword') || '';
    const typeParam = params.get('type') || 'TITLE';
    const type = typeParam.toUpperCase();
    const page = parseInt(params.get('page')) || 1;
    const size = parseInt(params.get('size')) || 10;

    // 검색 입력 필드와 검색 타입 셀렉트 박스의 초기 값을 설정
    $('.search-bar input').val(keyword);
    $('.search-type-select').val(type);

    // 검색 결과를 가져와서 표시
    fetchResults(keyword, type, page, size);

    function fetchResults(searchQuery, searchType, page = 1, size = 10) {
        if (page < 1) {
            page = 1;
        }

        $.ajax({
            url: '/api/posts/search',
            type: 'GET',
            data: {
                type: searchType,
                keyword: searchQuery,
                page: page,
                size: size,
                sortBy: 'id', // 기본 정렬 필드
                descending: false // 기본 정렬 방향
            },
            success: function(response) {
                $('#loading').hide(); // 로딩 메시지 숨기기
                console.log('서버 응답:', response); // 응답 데이터 확인
                if (response && response.data && Array.isArray(response.data.content)) {
                    displayPosts(response.data.content);
                } else {
                    console.error('응답 데이터가 올바르지 않습니다:', response);
                    $('#search-results').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $('#loading').hide(); // 로딩 메시지 숨기기
                console.error('게시물 로딩 중 오류 발생:', textStatus, errorThrown);
                $('#search-results').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
            }
        });
    }

    function displayPosts(posts) {
        var postsContainer = $('#search-results');
        postsContainer.empty(); // 기존 게시물 초기화

        if (posts.length === 0) {
            postsContainer.html('<p>게시물이 없습니다.</p>');
            return;
        }

        posts.forEach(function(post) {
            // 첫 번째 이미지 URL만 사용
            var imageUrl = post.imageUrls && post.imageUrls.length > 0 ? post.imageUrls[0] : 'default-image-url.jpg'; // 기본 이미지 URL

            // 게시물 요소 생성
            var postElement = $('<div class="post" data-post-id="' + post.id + '"></div>');
            postElement.html(`
                <div class="image-container">
                    <img src="${imageUrl}" alt="Post Image" class="img-fluid" />
                </div>
                <div class="nickname">${post.nickname || 'Unknown'}</div>
            `);

            // 클릭 시 상세 페이지로 이동
            postElement.click(function() {
                var postId = $(this).data('post-id');
                window.location.href = `/posts/postDetail/${postId}`;
            });

            // 게시물 컨테이너에 추가
            postsContainer.append(postElement);
        });
    }



    // 검색 버튼 클릭 시 검색 수행
    $('.search-bar button').click(function() {
        const searchQuery = $('.search-bar input').val();
        let searchType = $('.search-type-select').val();
        searchType = searchType ? searchType.toUpperCase() : 'TITLE'; // 검색 타입을 대문자로 변환
        window.location.href = `/posts/search?keyword=${encodeURIComponent(searchQuery)}&type=${searchType}&page=1&size=10`;
    });

    // Enter 키 입력 시 검색 수행
    $('.search-bar input').keypress(function(e) {
        if (e.key === 'Enter') {
            const searchQuery = $(this).val();
            let searchType = $('.search-type-select').val();
            searchType = searchType ? searchType.toUpperCase() : 'TITLE'; // 검색 타입을 대문자로 변환
            window.location.href = `/posts/search?keyword=${encodeURIComponent(searchQuery)}&type=${searchType}&page=1&size=10`;
        }
    });

    // URL에서 쿼리 파라미터를 추출하는 함수
    function getQueryParam(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    $(document).ready(function() {
        const searchType = getQueryParam('type');
        if (searchType) {
            $('.search-type-select').val(searchType.toLowerCase());
        }
    });
});
