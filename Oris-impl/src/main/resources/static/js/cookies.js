function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}
function getAccessToken() {
    var check = getCookie("tokenType");
    var token = getCookie("refreshToken");
    alert(token)
    // if (check == null && token != null) {
    //     $.ajax({
    //         url: '/auth/token',
    //         type: 'POST',
    //         data: JSON.stringify({refreshToken: token}),
    //         contentType: 'application/json; charset=utf-8',
    //         dataType: "json",
    //         header: {'Authorization': 'Bearer ' + getCookie("accessToken")},
    //         success: function (data) {
    //             var values = Object.values(data)
    //             document.cookie = "tokenType=" + encodeURIComponent(values[0])+ "; max-age=240";
    //             document.cookie = "accessToken=" + encodeURIComponent(values[1])+ "; max-age=300";
    //         },
    //         error: function (jqXHR, exception) {
    //             // Выводим ошибку
    //             alert(jqXHR.responseText);
    //         }
    //     });
    // }
    // else {
    //
    // }
}
