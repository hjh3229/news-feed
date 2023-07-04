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

    $.ajax({
        type: 'GET',
        url: `/newsfeed/user-info`,
        contentType: 'application/json',
    })
        .done(function (res) {
            const username = res.username;
            $('#username').text(username);
        })

    // 삭제 기능
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let id = document.getElementById("feed-id").value;
            fetch(`/newsfeed/feed/${id}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert('삭제가 완료되었습니다.');
                    location.replace('/');
                });
        });
    }

    // 수정 기능
    const modifyButton = document.getElementById('modify-btn');

    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            let params = new URLSearchParams(location.search);
            let id = params.get('id');

            fetch(`/newsfeed/feed/${id}`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: document.getElementById('title').value,
                    content: document.getElementById('content').value
                })
            })
                .then(() => {
                    alert('수정이 완료되었습니다.');
                    location.replace(`/newsfeed/feed/${id}`);
                });
        });
    }
})

function getToken() {
    let auth = Cookies.get('Authorization');

    if(auth === undefined) {
        return '';
    }
    return auth;
}
