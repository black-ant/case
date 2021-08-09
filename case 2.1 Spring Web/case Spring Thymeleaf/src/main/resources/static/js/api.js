$.ajaxSetup({
    beforeSend: function () {
        console.log(arguments);//我们先来看看这里面有什么好玩的东西
    }
});
var ajaxutil = {
    init: function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajaxSetup({
            crossDomain: true,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            error: function (erro) {

            }
        });
    },
    sendPostParam: function (url, data, callback) {
        $.post(url + comutils.setQueryString(data), {}, callback);
    },
    sendPostData: function (url, data, callback) {
        $.post(url, data, callback);
    }
}
ajaxutil.init();