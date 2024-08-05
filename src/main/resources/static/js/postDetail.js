$(document).ready(function() {
    // URL의 경로에서 ID 가져오기
    const pathSegments = window.location.pathname.split('/');
    const postId = pathSegments[pathSegments.length - 1]; // URL의 마지막 부분을 postId로 사용

    if (postId) {
        // 게시물 정보 요청
        $.ajax({
            url: `/api/posts/` + postId,
            method: 'GET',
            dataType: 'json',
            success: function(response) {
                if (response && response.data) {
                    const { title, imageUrls, nickname, content, likes } = response.data;

                    // 서버 응답 데이터 확인 (디버깅 목적)
                    console.log('서버 응답 데이터:', response.data);

                    // 첫 번째 이미지 URL만 사용
                    const imageUrl = imageUrls.length > 0 ? imageUrls[0] : 'default-image-url.jpg'; // 이미지 URL이 없을 경우 기본 이미지 사용

                    $('#post-detail').html(`
                        <h1>${title}</h1>
                        <div class="image-container">
                            <img src="${imageUrl}" alt="${nickname}" class="img-fluid" />
                        </div>
                        <div>${nickname}</div>
                        <div>${content}</div>
                    `);
                    $('#post-likes').text(`${likes} likes`); // 게시물 정보에서 좋아요 수를 가져와서 표시
                } else {
                    console.error('올바르지 않은 응답 데이터:', response);
                    $('#post-detail').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('게시물 로딩 중 오류 발생:', textStatus, errorThrown);
                $('#post-detail').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
            }
        });

        // 좋아요 버튼 클릭 시 처리
        $('#like-button').click(function() {
            console.log(`좋아요 요청 URL: /api/posts/${postId}/likes`); // URL 확인
            $.ajax({
                url: `/api/posts/${postId}/likes`, // 좋아요 요청 URL
                method: 'POST',
                success: function() {
                    // 성공 시 좋아요 수 증가
                    let likes = parseInt($('#post-likes').text().split(' ')[0]);
                    $('#post-likes').text(`${likes + 1} likes`);
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    $('#post-likes').text('좋아요 추가 중 오류가 발생했습니다.');
                    console.error('좋아요 추가 중 오류 발생:', textStatus, errorThrown);
                }
            });
        });
    } else {
        $('#post-detail').html('<p>게시물 ID가 제공되지 않았습니다.</p>');
    }
});
