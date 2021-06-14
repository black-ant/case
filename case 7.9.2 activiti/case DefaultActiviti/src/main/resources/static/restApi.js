// var baseUrl = "http://127.0.0.1:8086/";
var baseUrl = "http://127.0.0.1:8087/";
var restApi = {
    doGetJSON: function (successFun) {
        return restUtils.get(baseUrl + "vac/list?userName=admin", successFun);
    },
    doSaveActiviti: function (successFun) {
        var data = {
            "applyUser": "admin",
            "days": "3",
            "reason": "test0001",
            "applyTime": new data,
            "applyStatus": "0",
            "auditor": "admin",
            "result": "",
            "auditTime": ""
        }
        return restUtils.post(baseUrl + "vac/vac?userName=admin", data, successFun);
    }
}

var restUtils = {
    post: function (url, data, successFun) {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: baseUrl + url,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: successFun
        });
    },
    get: function (url, successFun) {
        $.ajax({
            type: "GET",
            dataType: "json",
            url: url,
            contentType: "application/json",
            success: successFun
        });
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
