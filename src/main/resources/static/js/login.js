$(document).ready(function () {
  // 페이지 로드 시 토큰 삭제
  Cookies.remove('Authorization', { path: '/' });
});

// $.ajaxPrefilter를 사용하여 모든 AJAX 요청에 헤더를 추가합니다.
$.ajaxPrefilter(function (options, originalOptions, jqXHR) {
  const token = Cookies.get('Authorization');
  if (token) {
    jqXHR.setRequestHeader('Authorization', token);
  }
});

const href = location.href;
const host = 'http://' + window.location.host;

$('#login-form').on('submit', function (event) {
  event.preventDefault(); // 폼 제출 기본 동작 막기

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

    // 토큰을 로컬 스토리지에 저장합니다.
    localStorage.setItem('Authorization', token);

    // 홈 페이지로 리다이렉션합니다.
    window.location.href = host + '/home';
  })
  .fail(function (jqXHR, textStatus) {
    alert("Login Fail");
  });
});

// 모든 AJAX 요청에 Authorization 헤더 추가
$.ajaxSetup({
  beforeSend: function (xhr) {
    const token = localStorage.getItem('Authorization');
    if (token) {
      xhr.setRequestHeader('Authorization', 'Bearer ' + token);
    }
  }
});