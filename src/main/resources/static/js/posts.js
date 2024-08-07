// 게시물을 가져오는 함수
function fetchPosts() {
    $.ajax({
      url: '/api/posts',
      method: 'GET',
      dataType: 'json',
      success: function (response) {
        $('#loading').hide(); // 로딩 메시지 숨기기
        console.log('서버 응답:', response); // 응답 데이터 확인
        if (response && response.data && Array.isArray(response.data)) {
          displayPosts(response.data);
        } else {
          console.error('응답 데이터가 올바르지 않습니다:', response);
          $('#posts-container').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
        }
      },
      error: function (jqXHR, textStatus, errorThrown) {
        $('#loading').hide(); // 로딩 메시지 숨기기
        console.error('게시물 로딩 중 오류 발생:', textStatus, errorThrown);
        $('#posts-container').html('<p>게시물 로딩 중 오류가 발생했습니다.</p>');
      }
    });
  }

// 게시물을 페이지에 표시하는 함수
  function displayPosts(posts) {
    var postsContainer = $('#posts-container');
    postsContainer.empty(); // 기존 게시물 초기화

    if (posts.length === 0) {
      postsContainer.html('<p>게시물이 없습니다.</p>');
      return;
    }

    posts.forEach(function (post) {
      // 첫 번째 이미지 URL만 사용
      var imageUrl = post.imageUrls && post.imageUrls.length > 0
          ? post.imageUrls[0] : 'default-image-url.jpg'; // 이미지 URL이 없을 경우 기본 이미지 사용

      // 게시물 요소 생성
      var postElement = $(
          '<div class="post" data-post-id="' + post.id + '"></div>');
      postElement.html(`
            <div class="image-container">
                <img src="${imageUrl}" alt="Post Image" class="img-fluid" />
            </div>
            <div class="nickname">${post.nickname || 'Unknown'}</div>
        `);

      // 클릭 시 상세 페이지로 이동
      postElement.click(function () {
        var postId = $(this).data('post-id');
        window.location.href = `/posts/postDetail/${postId}`;
      });

      // 게시물 컨테이너에 추가
      postsContainer.append(postElement);
    });
  }

// 페이지 로드 시 게시물 불러오기
  $(document).ready(function () {
    fetchPosts();
  });

