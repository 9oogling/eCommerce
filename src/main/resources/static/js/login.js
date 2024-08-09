$(document).ready(function () {
  // 페이지 로드 시 토큰 삭제
  Cookies.remove('Authorization', {path: '/'});
});

// $.ajaxPrefilter를 사용하여 모든 AJAX 요청에 헤더를 추가합니다.

const href = location.href;
const host = 'http://' + window.location.host;

$('#login-form').on('submit', function (event) {
  event.preventDefault();  // 폼 제출 기본 동작 막기

  const email = $('#email').val();
  const password = $('#password').val();

  if (!email || !password) {
    alert('이메일과 비밀번호를 입력해주세요.');
    return;
  }

  const loginData = {
    email: email,
    password: password
  };

  $.ajax({
    url: '/api/users/login',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(loginData)
  })
  .done(function (res, status, xhr) {
    const token = xhr.getResponseHeader('Authorization');

    // 토큰을 쿠키에 저장합니다.
    Cookies.set('Authorization', token, {path: '/'});
    console.log("input!!!!");

    // 홈 페이지로 리다이렉션합니다.
    window.location.href = host + '/home';
  })
  .fail(function (jqXHR, textStatus) {
    alert("Login Fail");
  });
});