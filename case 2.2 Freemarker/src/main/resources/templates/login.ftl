[#ftl]
<html>
<head>
    <meta charset="GBK">
    <title>杭州天纵智能管理平台</title>
    <link rel="stylesheet" href="../css/layui.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/modules/layer/default/layer.css?v=3.1.1" rel="stylesheet">
    <style>
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
        }

        body {
            display: flex;
            align-items: center; /*定义body的元素垂直居中*/
            justify-content: center; /*定义body的里的元素水平居中*/
        }
    </style>
</head>
<body>
<form class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-inline">
            <input type="text" name="userName" lay-verify="required" autocomplete="off" placeholder="请输入用户名称"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-inline">
            <input type="password" name="passWord" lay-verify="required" autocomplete="off" placeholder="请输入密码"
                   class="layui-input"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="demo1">登录</button>
        </div>
    </div>
</form>
<script src="../js/layui.js"></script>
<script src="../js/jquery-1.12.4.js"></script>
<script>
    layui.use(['form'], function () {
        var form = layui.form
            , layer = layui.layer
        form.on('submit(demo1)', function (data) {
            $.ajax({
                url: "/start/login",
                type: "get",
                data: data.field,
                async: false,
                success: function (data) {
                    if (data > 0) {
                        alert("登录成功");
                        window.location.href = "/index.html";
                        event.preventDefault();
                    } else {
                        alert("登录失败");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest.status);//状态码
                    alert(XMLHttpRequest.readyState);//状态
                    alert(textStatus);//错误信息
                }
            })
        });
    });
</script>
</body>
</html>