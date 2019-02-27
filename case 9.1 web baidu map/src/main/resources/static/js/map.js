var _maplink = "https://api.map.baidu.com/api?v=3.0&ak=O3bxn3dkOmx0NSQqHNI4QAh4eZuDfRD5";
var baidumap;

//生成百度地图调用链接
function addScript() {
    //加载js API文件
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = _maplink + "&callback=_mapstart";
    document.head.appendChild(script);
}

addScript();

//window.onload加载多个函数
function addLoadEvent(func) {
    if (document.all) {
        window.attachEvent("onload", func); //对于IE
    } else {
        window.addEventListener("load", func, false); //对于FireFox
    }
}

function _mapstart() {
    console.log("map api loaded");
}

baidumap = {
    map: null,
    page_mapid: "container",
    centerpoint: "",
    longitude: 39.915,
    latitude: 116.404,
    defaultZoom: 13,
    init: function (page_mapid, centerpoint, zoom) {
        this.page_mapid = baidumap.errorcheck("isnull", page_mapid) ? "container" : page_mapid;
        this.centerpoint = baidumap.errorcheck("isnull", centerpoint) ? "" : centerpoint;
        baidumap.errorcheck("ok", "", this.createMap); //加载地图
    },
    createMap: function () {
        baidumap.map = new BMap.Map(baidumap.page_mapid);
        if (baidumap.errorcheck("isnull", baidumap.centerpoint)) {
            var point = new BMap.Point(baidumap.centerpoint);
            baidumap.map.centerAndZoom(point, baidumap.defaultZoom);
        } else {
            baidumap.map.centerAndZoom(new BMap.Point(baidumap.latitude, baidumap.longitude), baidumap.defaultZoom);
        }
    },
    getCenter: function () {
        return this.map.getCenter();
    },
    setCenter: function () {
        if (arguments.length <= 0)
            return;
        if (typeof arguments[0] === "object") {
            this.map.setCenter(arguments[0]);
        } else if (arguments.length >= 2) {
            this.map.setCenter(new BMap.Point(arguments[0], arguments[1]));
        }
    },
    errorcheck: function (code, obj, callback) {
        if ("ok" == code && typeof BMap === "undefined") {
            console.log("百度地图未成功加载,正在重新载入 ... ");
            baidumap.init();
            return false;
        } else if ("isnull" == code) {
            if (null == obj || typeof BMap === "undefined" || "" == obj || null == obj) {
                return false
            }
        }
        if (typeof callback == "undefined") {
            return true;
        } else {
            callback();
        }

    }

}
