const host = 'http://' + window.location.host;

function getToken() {
  return Cookies.get('Authorization');
}

$(document).ready(function () {

  const auth = getToken();

  if (auth !== undefined && auth !== '') {
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
      jqXHR.setRequestHeader('Authorization', auth);
    });
  } else {
    window.location.href = host + '/login-page';
    return;
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
