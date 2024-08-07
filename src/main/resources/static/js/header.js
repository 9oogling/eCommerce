$(document).ready(function() {
  const token = Cookies.get('Authorization');
  console.log('Authorization token:', token);

  if (token) {
    $('#login-logout-link').text('로그아웃').attr('onclick', 'logout()');
  } else {
    $('#login-logout-link').text('로그인').attr('onclick', "location.href='login-page'");
  }

  // 로그아웃 함수
  window.logout = function() {
    Cookies.remove('Authorization', { path: '/' });
    alert('로그아웃 되었습니다.');
    location.href = '/home';
  };


  // 검색 기능
  $('.search-bar button').click(function() {
    const searchQuery = $('.search-bar input').val();
    let searchType = $('.search-type-select').val();
    if (searchQuery.trim() === '') {
      alert('검색어를 입력해 주세요.');
      return;
    }
    searchType = searchType.toUpperCase(); // 검색 타입을 대문자로 변환
    // 검색 페이지로 이동
    window.location.href = `/posts/search?&type=${searchType}&keyword=${encodeURIComponent(searchQuery)}&page=1&size=10`;
  });

  $('.search-bar input').keypress(function(e) {
    if (e.key === 'Enter') {
      const searchQuery = $(this).val();

      let searchType = $('.search-type-select').val();

      if (searchQuery.trim() === '') {
        alert('검색어를 입력해 주세요.');
        return;
      }
      searchType = searchType.toUpperCase(); // 검색 타입을 대문자로 변환
      // 검색 페이지로 이동
      window.location.href = `/posts/search?&type=${searchType}&keyword=${encodeURIComponent(searchQuery)}&page=1&size=10`;
    }
  });

  // 페이지 변경 시 URL 업데이트
  function changePage(newPage) {
    const searchQuery = $('.search-bar input').val();
    let searchType = $('.search-type-select').val();
    searchType = searchType ? searchType.toUpperCase() : 'TITLE'; // 검색 타입을 대문자로 변환
    window.location.href = `/posts/search?keyword=${encodeURIComponent(searchQuery)}&type=${searchType}&page=${newPage}&size=10`;
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

  // 메인 카테고리 링크에 호버 효과 추가
  const mainCategoryLinks = document.querySelectorAll('.main-categories a');
  mainCategoryLinks.forEach(link => {
    link.addEventListener('mouseover', function() {
      this.style.color = '#4CAF50';
    });
    link.addEventListener('mouseout', function() {
      this.style.color = '#333';
    });
  });
});
