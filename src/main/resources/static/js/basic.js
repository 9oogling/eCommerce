const host = 'http://' + window.location.host;

function getToken() {
  return localStorage.getItem('Authorization');
}

$(document).ready(function () {
  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', 'Bearer ' + auth);
    });
  }

  $.ajax({
    type: 'GET',
    url: `/users/user-info`,
    contentType: 'application/json',
  })
  .done(function (res, status, xhr) {
    const nickname = res.nickname;
    const isAdmin = !!res.admin;

    if (!nickname) {
      window.location.href = '/login-page';
      return;
    }

    $('#navUsername').text(nickname);
    if (isAdmin) {
      // $('#admin').text(true);
      // showProduct();
    } else {
      // showProduct();
    }
  });
});
