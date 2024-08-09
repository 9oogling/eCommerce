$(document).ready(function() {
  // 페이지 로드 시 검색창 텍스트 초기화
  const inputField = $('.search input');
  inputField.val(''); // 페이지 로드 시 입력된 텍스트를 지움

  // 페이지가 뒤로 가기로 돌아왔을 때 검색 텍스트 초기화
  window.onpageshow = function(event) {
    if (event.persisted || (window.performance && window.performance.navigation.type === 2)) {
      inputField.val(''); // 뒤로 가기 시 텍스트 초기화
    }
  };

  const token = Cookies.get('Authorization');
  console.log('Authorization token:', token);

  // 로그인 상태에 따라 링크 텍스트 및 기능 설정
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
  function performSearch() {
    const searchQuery = $('.search input').val().trim();
    let searchType = $('.search-options select').val().toUpperCase() || 'TITLE';

    if (searchQuery === '') {
      alert('검색어를 입력해 주세요.');
      return;
    }

    window.location.href = `/posts/search?type=${searchType}&keyword=${encodeURIComponent(searchQuery)}&page=1&size=10`;
  }

  // 돋보기를 클릭했을 때 검색창이 확장되고 돋보기는 사라지도록 설정
  $('.search input, .search-icon').click(function(event) {
    event.stopPropagation(); // 클릭 이벤트가 부모 요소로 전파되지 않도록 방지
    const inputField = $('.search input');
    const searchSvg = $('.search input + div svg');

    inputField.addClass('expanded');
    searchSvg.css('opacity', '1'); // 돋보기를 클릭했을 때 선이 보이도록 설정
  });

  // 페이지 외 다른 부분 클릭 시 검색창 및 돋보기 아이콘 상태 복원
  $(document).click(function(event) {
    const inputField = $('.search input');
    const searchSvg = $('.search input + div svg');

    // 클릭한 요소가 검색창이나 드롭다운 메뉴가 아닐 때 검색창을 닫음
    if (!$(event.target).closest('.all-search').length) {
      inputField.removeClass('expanded');
      inputField.val(''); // 입력된 텍스트를 지움
      searchSvg.css('opacity', '1'); // 검색창을 닫을 때 돋보기가 다시 나타나도록 설정
    }

  });

  // 텍스트 입력 시 선이 계속 보이도록 설정
  $('.search input').on('input', function() {
    const searchSvg = $('.search input + div svg');
    searchSvg.css('opacity', '1');
  });

  // 검색 버튼 클릭 및 Enter 키 입력 시 검색 수행
  $('.search-bar button').click(performSearch);
  $('.search input').keypress(function(e) {
    if (e.key === 'Enter') {
      performSearch();
    }
  });

  // 페이지 변경 시 URL 업데이트 함수
  function changePage(newPage) {
    const searchQuery = $('.search input').val().trim();
    let searchType = $('.search-options select').val().toUpperCase() || 'TITLE';
    window.location.href = `/posts/search?keyword=${encodeURIComponent(searchQuery)}&type=${searchType}&page=${newPage}&size=10`;
  }

  // 메인 카테고리 링크에 호버 효과 추가
  $('.main-categories a').hover(
      function() {
        $(this).css('color', '#4CAF50');
      },
      function() {
        $(this).css('color', '#333');
      }
  );

  // 왼쪽 메뉴바 토글
  $('#toggle').click(function(event) {
    event.stopPropagation(); // 클릭 이벤트가 부모 요소로 전파되지 않도록 방지
    $('#toggle .bar').toggleClass('animate');
    $('#page').toggleClass('overlay');
  });

  // 오버레이의 nav 외부를 클릭했을 때 토글 닫기
  $('#overlay').click(function(event) {
    if (!$(event.target).closest('nav').length) {
      $('#toggle .bar').removeClass('animate');
      $('#page').removeClass('overlay');
    }
  });

});