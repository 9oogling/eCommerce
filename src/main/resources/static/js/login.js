const host = 'http://' + window.location.host;
let targetId;
let folderTargetId;

$(document).ready(function () {
  // 현재 페이지가 로그인 페이지인지 확인
  if (window.location.pathname === '/login-page') {
    return;
  }

  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
  } else {
    window.location.href = host + '/api/user/login-page';
    return;
  }

  $.ajax({
    type: 'GET',
    url: `/api/user-info`,
    contentType: 'application/json',
  })
  .done(function (res, status, xhr) {
    const username = res.username;
    const isAdmin = !!res.admin;

    if (!username) {
      window.location.href = '/api/login-page';
      return;
    }

    $('#username').text(username);
    if (isAdmin) {
      $('#admin').text(true);
      showProduct();
    } else {
      showProduct();
    }

  })
})


function logout() {
  // 토큰 삭제
  Cookies.remove('Authorization', {path: '/'});
  window.location.href = host + '/api/login-page';
}

function getToken() {

  let auth = Cookies.get('Authorization');

  if(auth === undefined) {
    return '';
  }

  // kakao 로그인 사용한 경우 Bearer 추가
  if(auth.indexOf('Bearer') === -1 && auth !== ''){
    auth = 'Bearer ' + auth;
  }

  return auth;
}