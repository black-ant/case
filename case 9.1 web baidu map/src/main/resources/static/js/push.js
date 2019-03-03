/**
 * push 核心方法
 */


function readyOnload() {
    socket_do.open();
}

//加载文件后启动WebSocket
if (window.addEventListener) {
    window.addEventListener("load", readyOnload, false);
} else {
    window.attachEvent("onload", readyOnload);
}
var webSocket = {};
var socket_do = {
    reconnection: true,
    open: function () {
        webSocket = new WebSocket("ws://127.0.0.1:8080/push");
    },
    //发送消息
    send: function (gid, ctt, evt, mtp) {
        var _msg = {groupid: gid, datacontent: ctt, pushtype: evt || "TRANS", msgtype: mtp || "MSG"};
        webSocket.send(JSON.stringify(_msg));
    },
    register: function (_pushRegItem) {
        // _register(_pushRegItem);
        pushHandle.injectSockMethod(webSocket,_pushRegItem);
        return _pushRegItem;
    },
    unRegister: function (itemId) {
        var idx = _regItemIndexOf(itemId);
        if (idx === -1)
            return;
        regItems.splice(idx, 1);
        if (this.getState() === 1)
            this.send(itemId, null, "UNREG");
    },
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
}
var profile = false; //debug 模式
function setMessageInnerHTML(msg) {
    console.log(msg);
    if (profile) {
        alert("信息" + msg);
    }
}

var pushHandle = {
    getSocketItem: function (gid, pType, onM, onO, onC, onE, onB) {
        this.gid = gid; //消息注册单位ID
        this.type = pType || "MSG"; //MSG_正常消息,DU_数据唯一性检测刷新
        this.onMessage = onM || function () {
            setMessageInnerHTML("WebSocket连接关闭");
        };
        this.onOpen = onO || function () {
            setMessageInnerHTML("WebSocket连接成功");
        };
        this.onClose = onC || function () {
            setMessageInnerHTML("WebSocket连接关闭");
        };
        this.onError = onE || function () {
            setMessageInnerHTML("WebSocket连接发生错误");
        };
        this.onBeforeUnload = onB || function () {
            setMessageInnerHTML("关闭websocket");
            closeWebSocket();
        }
    },
    injectSockMethod:function(webSocket,socketItem){
        webSocket.onopen = socketItem.onOpen;
        webSocket.onmessage =socketItem.onMessage;
        webSocket.onerror = socketItem.onError;
        webSocket.onclose =socketItem.onClose;
    }
}
