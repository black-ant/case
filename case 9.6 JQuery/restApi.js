// var baseUrl = "http://127.0.0.1:8086/";
var baseUrl = "http://127.0.0.1:8087/";
var restApi = {
    doGetJSON: function () {
        return restUtils.get("vac/list?userName=test001");
    }
}

var restUtils = {
    get: function (url, param = {}, dataType = 'json') {
        return new Promise((resolve, reject) = > {
            $.ajax({
                url: baseUrl + url,
                type: "GET",
                data: param,
                dataType: dataType,
                async: true,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
                    xhr.setRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
                },
                success: function (result) {
                    resolve(result)
                },

                error: function (result) {
                    reject(result)
                }
            });
    })
    },
    post: function (url, param = {}, dataType = 'json') {
        return new Promise((resolve, reject) = > {
            $.ajax({
                type: "post",
                url: baseUrl + url,
                contentType: "application/json;charset=UTF-8",
                data: JSON.stringify(param),
                dataType: dataType,
                crossDomain: true,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
                    xhr.setRequestHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT");
                },
                success: function (result) {
                    resolve(result);
                },
                error: function (err) {
                    reject(result);
                }
            });
    })
    },
    urlCode: function (param, key, encode) {
        var urlEncode = function (param, key, encode) {
            if (param == null)
                return '';
            var paramStr = '';
            var t = typeof (param);
            if (t == 'string' || t == 'number' || t == 'boolean') {
                paramStr += '&' + key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param);
            } else {
                for (var i in param) {
                    var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);
                    paramStr += urlEncode(param[i], k, encode);
                }
            }
            return paramStr;
        };
    },
    baseUrl: function () {
        var curPageUrl = window.document.location.href;
        var rootPath = curPageUrl.split("//")[0] + curPageUrl.split("//")[1].split("/")[0]
            + "/";
        return rootPath;

    },
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return unescape(r[2]);
        } else {
            return null;
        }
    },
    setQueryString: function (data) {
        var params = "";
        if (!isnull(data) && (typeof (data) == "object" && Object.keys(data).length > 0)) {
            params += "?";
            var num = 0;
            for (var x in data) {
                num++;
                params += x + "=" + data[x];
                if (num < Object.keys(data).length) {
                    params += "&";
                }
            }
        }
        return params; //返回参数值
    }
}
