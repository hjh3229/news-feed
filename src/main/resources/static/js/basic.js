const host = 'http://' + window.location.host;
let targetId;
let folderTargetId;
$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
    }
    // else {
    //     window.location.href = host + '/api/user/login-page';
    //     return;
    // }

    $.ajax({
        type: 'GET',
        url: `/newsfeed/user/user-info`,
        contentType: 'application/json',
    })
        .done(function (res) {
            const username = res.username;

            $('#username').text(username);

        })

})

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }

    return auth;
}