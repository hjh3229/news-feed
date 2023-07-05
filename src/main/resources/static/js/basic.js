let host = "http://localhost:8080"
$(document).ready(function () {
    const auth = getToken();

    if (auth !== undefined && auth !== '') {
        $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
            jqXHR.setRequestHeader('Authorization', auth);
        });
        showProfile();
    }


    // 생성 기능
    const createButton = document.getElementById('create-btn');

    if (createButton) {
        createButton.addEventListener('click', event => {
            fetch('/newsfeed/feed', {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: document.getElementById('title').value,
                    url: document.getElementById('url').value,
                    content: document.getElementById('content').value
                })
            })
                .then(() => {
                    alert('등록 완료되었습니다.');
                    location.replace('/');
                });
        });
    }


    // 수정 기능
    const modifyButton = document.getElementById('modify-btn');

    if (modifyButton) {
        modifyButton.addEventListener('click', event => {
            let params = new URLSearchParams(location.search);
            let id = params.get('feed_id');

            fetch(`/newsfeed/feed/${id}`, {
                method: 'PUT',
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: document.getElementById('title').value,
                    url: document.getElementById('url').value,
                    content: document.getElementById('content').value
                })
            })
                .then(() => {
                    alert('수정이 완료되었습니다.');
                    location.replace(`/`);
                });
        });
    }

    // 삭제 기능
    const deleteButton = document.getElementById('delete-btn');

    if (deleteButton) {
        deleteButton.addEventListener('click', event => {
            let id = document.getElementById('item-id').value;
            fetch(`/newsfeed/feed/${id}`, {
                method: 'DELETE'
            })
                .then(() => {
                    alert('삭제가 완료되었습니다.');
                    location.replace('/');
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
function showProfile() {
    $.ajax({
        type: 'GET',
        url: `/newsfeed/user-info`,
        success: function (response) {
            $('#profile').empty();
            $('#profile').append(`
                <div class="header" style="float:left">
                    <div class="card-body p-5 text-center">
                        <p>${response.username}</p>
                        <p>${response.nickname}</p>
                        <p>${response.myContent}</p>
                      
                        <button type="button" class="btn btn-light" onclick="location.href='/newsfeed/user/loin-page'">프로필 수정</button><br><br>
                        <button type="button" class="btn btn-light" onclick="location.href='/newsfeed/user/loin-page'">비밀번호 변경</button><br><br>
                        <button type="button" onclick="location.href='/newsfeed/newFeed'" class="btn btn-dark">Feed 추가</button>
                    </div>
                </div>
                <div class="header" style="float:right">
                <div class="card-body p-5 text-center">
                    <button type="button" class="btn btn-secondary" onclick="location.href='/newsfeed/user/loin-page'">로그아웃</button>
                </div>
                </div>
            `)
        },
        error: function (error) {
            console.log('Error:', error);
        }
    })
}