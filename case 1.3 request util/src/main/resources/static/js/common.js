/**
 * 公共工具类
 * @type {{storegeeditutil: comutils.storegeeditutil, setQueryString: (function(*=): string), GetQueryStringByEncode: comutils.GetQueryStringByEncode}}
 */
var rootUrl = "http://localhost:2101/";
var comutils = {
    storegeeditutil: function (name, value, option) {
        if (arguments.length == 1) {
            return window.localStorage.getItem(name);
        } else if (arguments.length == 2) {
            window.localStorage.setItem(name, value);
        } else if (arguments.length == 3) {
//设置参数
            storegeditOption(option);
            window.localStorage.setItem(name, value);
        }
    },
    setQueryString: function (data) {
        var params = "";
        if (this.isnotnull(data) && (typeof (data) == "object" && Object.keys(data).length > 0)) {
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
        return params; //返回参数值F
    },
    GetQueryStringByEncode: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null)
            return unescape(decodeURI(r[2]));
        return null; //返回参数值F
    },
    isnotnull: function (data) {
        if ("" == data || "undefined" == data || "null" == data || null == data || typeof data == "undefined") {
            return false;
        }
        return true;
    }
}

var cartUtils = {
    /**
     * data = {
     * labels:["Red", "Blue", "Cyan", "Green", "Purple", "Orange"],
     * datas:[100, 45, 87, 50, 77, 20]}
     * @param data
     */
    pieChart: function (data) {
        var pieChart = $('#pie-chart');
        if (pieChart.length > 0) {
            new Chart(pieChart, {
                type: 'pie',
                data: {
                    labels: data.labels,
                    datasets: [{
                        label: 'Users',
                        data: data.datas,
                        backgroundColor: [
                            'rgba(244, 88, 70, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(0, 188, 212, 0.5)',
                            'rgba(42, 185, 127, 0.5)',
                            'rgba(156, 39, 176, 0.5)',
                            'rgba(253, 178, 68, 0.5)'
                        ],
                        borderColor: [
                            'rgba(244, 88, 70, 0.5)',
                            'rgba(33, 150, 243, 0.5)',
                            'rgba(0, 188, 212, 0.5)',
                            'rgba(42, 185, 127, 0.5)',
                            'rgba(156, 39, 176, 0.5)',
                            'rgba(253, 178, 68, 0.5)'
                        ],
                        borderWidth: 1
                    }]
                }
            });
        }
    }
}
var htmlUtils = {
    create: function (temp, dt) {
        if (comutils.isnotnull(dt)) {
            return temp.createHTML(dt);
        }
    },
    append: function (id, ht) {
        $("#" + id).append(ht);
    },
    change: function (id, ht) {
        $("#" + id).html(ht);
    },
    format: function (list, param, type) {

    }
}
String.prototype.createHTML = function (dt) {
    for (var x in dt) {
        var argsObj = dt[x];
        console.log(argsObj);
    }
}

//循环生成
String.prototype.format = function (data) {
    var result = this;
    // if (arguments.length > 0) {
    // 如果是对象
    for (var key in data) {
        if (arguments.length === 1 && typeof (arguments[0]) === "object") {
            if (argsObj[key] !== undefined) {
                if ("object" != typeof (argsObj[key])) {
                    var reg = new RegExp("({" + key + "})", "g");
                    var value = argsObj[key];
                    result = result.replace(reg, value);
                } else {
                    for (var x in argsObj[key]) {
                        var reg = new RegExp("({)" + key + "." + x + "(})", "g");
                        var value = argsObj[key][x];
                        result = result.replace(reg, value);
                    }
                }
            }
        } else {
            
        }
    }
    // else {
    //         for (var i = 0; i < arguments.length; i++) {
    //             if (arguments[i] !== undefined) {
    //                 if ("object" != typeof (arguments[i])) {
    //                     var reg = new RegExp("({)" + i + "(})", "g");
    //                     var value = arguments[i];
    //                     result = result.replace(reg, value);
    //                 } else {
    //                     for (var x in arguments[i]) {
    //                         var reg = new RegExp("({)" + i + "." + x + "(})", "g");
    //                         var value = arguments[i][x];
    //                         result = result.replace(reg, value);
    //                     }
    //                 }
    //
    //             }
    //         }
    //     }
    // }
    return result;
};