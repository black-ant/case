// 基础用法 :
//消息注册单位
function PushRegItem(gid, sid, type, onM, onO, onC, onE, onD) {
    this.gid = gid; //消息注册单位ID
    this.sid = sid;
    this.type = type || "MSG"; //MSG_正常消息,DU_数据唯一性检测刷新
    this.onMessage = onM; //来消息时执行
    this.onOpen = onO; //打开连接时执行
    this.onClose = onC; //关闭连接时执行
    this.onError = onE; //出错时执行
    this.onDU = onD; //DU消息类型的时执行
}

var MyPush = (function () {
    var regItems = [];
    var webSocket = {};
    var reConnItvl;

    //reconnectHeartbeat_断线重连执行间隔(毫秒); autoReconnect_是否断线重连; logDetail_是否显示更详细日志
    var _pushObj = {
        autoReconnect: true,
        reconnectHeartbeat: 5000,
        logDetail: true,
        open: function () {
            if (this.getState() > 1) {
                webSocket = new WebSocket((document.location.protocol === "https:" ? "wss:" : "ws:") + "//localhost:8086/push/msg");
                webSocket.onopen = _handleEvent("onOpen");
                webSocket.onmessage = _handleEvent("onMessage");
                webSocket.onerror = _handleEvent("onError");
                webSocket.onclose = _handleEvent("onClose");
            } else {
                console.log("Push is already open");
            }
        },
        //发送消息
        //gid=消息组id, content=消息内容,event=事件类型[REG_组注册,TRANS_消息传输,UNREG_组注销,CHECK_反馈], msgType=消息类型[MSG_正常消息,DU_数据唯一性检测刷新,RF_数据刷新]
        send: function (gid, sid, ctt, evt, mtp) {
            var _msg = {gid: gid, sid: sid, content: ctt, event: evt || "TRANS", msgType: mtp || "MSG"};
            webSocket.send(JSON.stringify(_msg));
        },
        register: function (_pushRegItem) {
            _register(_pushRegItem);
            return _pushRegItem;
        },
        // TODO : register again
        // unRegister: function (itemId) {
        //     var idx = _regItemIndexOf(itemId);
        //     if (idx === -1)
        //         return;
        //     regItems.splice(idx, 1);
        //     if (this.getState() === 1)
        //         this.send(itemId, null, "UNREG");
        // },
        close: function () {
            if (this.getState() <= 1)
                webSocket.close();
        },
        getState: function () {
            //返回连接状态 0_CONNECTING, 1_OPEN, 2_CLOSING, 3_CLOSED
            return webSocket.readyState || 3;
        },
        startReconnect: function () {
            console.log("reconnect after " + this.reconnectHeartbeat + "ms ...");
            this.autoReconnect = true;
            reConnItvl = setTimeout("MyPush.open()", this.reconnectHeartbeat);
        },
        stopReconnect: function () {
            console.log("stop reconnect");
            this.autoReconnect = false;
            clearTimeout(reConnItvl);
        }
    };


    function _handleEvent(_etp) {

        return function (event) {
            if (_pushObj.logDetail || event.type === "open")
                console.log("MyPush " + _etp + _eventLog(event));
            //没有注册单位,则不做任何操作
            if (regItems.length === 0)
                return;
            //处理对应事件并触发注册单位对应的事件
            for (var i in regItems) {
                var _item = regItems[i];
                if (_etp === "onOpen") {
                    //打开连接后统一注册已存储的注册组
                    _register(_item, true);
                }
                //触发注册单位里对应的方法
                if (_etp === "onMessage") {
                    console.log(event.data);
                } else {
                    if (typeof _item[_etp] === "function")
                        _item[_etp](event);
                }
            }
            //如果非手动关闭的连接, 则自动重新启动
            if (_pushObj.autoReconnect && _etp === "onClose" && event.code !== 1000) {
                _pushObj.startReconnect();
            }
        };
    }

    function _eventLog(event) {
        switch (event) {
            case"message":
                return ">> " + event.data;
            case "close":
                return ">> closeCode: " + event.code + ", reason: " + event.reason;
        }
        return "";
    }

    //注册消息单位
    function _register(_regItem, _isOpenReg) {
        if (_isOpenReg) {
            //为打开连接时候的统一注册时,则只发注册消息.  普通注册勿填写
            _pushObj.send(_regItem.gid, _regItem.sid, null, "REG", event);
            return;
        }
        //不存在于列表中就添加到列表
        if (_regItemIndexOf(_regItem) === -1)
            regItems.push(_regItem);
        //连接打开则注册到服务器
        if (_pushObj.getState() === 1)
            _pushObj.send(_regItem.gid, _regItem.sid, null, "REG", event);
        //页面加载完毕并且push连接为打开时, 执行打开操作
        if (document.readyState === "complete" && _pushObj.getState() > 1)
            _pushObj.open();
    }

    //根据id返回regItem的下标
    function _regItemIndexOf(r) {
        var _regItemId = (typeof r === "object") ? r.gid : r;
        if (regItems.length > 0) {
            for (var j in regItems) {
                if (regItems[j].gid === _regItemId)
                    return j;
            }
        }
        return -1;
    }

    function _domReady() {
        if (regItems.length !== 0)
            _pushObj.open();
    }

    //注册列表中有项时,文档加载完成后打开连接
    if (window.addEventListener) {
        window.addEventListener("load", _domReady, false);
    } else {
        window.attachEvent("onload", _domReady);
    }


    return _pushObj;
})();
