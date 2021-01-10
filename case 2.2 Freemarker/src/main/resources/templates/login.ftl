<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="/style.css">
</head>

<body>
<div class="content">
    <form id="frm1" action="/start/doLogin" method="post">
        <div class="form sign-in">
            <h2>FreeMarker 游戏平台</h2>
            <#if errorMsg??>
                <span class="c-red">${errorMsg}</span>
            </#if>
            <label>
                <span>账号</span>
                <input type="text" name="username"/>
            </label>
            <label>
                <span>密码</span>
                <input type="password" name="password"/>
            </label>
            <p class="forgot-pass"><a href="javascript:">忘记密码？</a></p>
            <button type="button" class="submit" onclick="formSubmit()">登 录</button>
        </div>
    </form>
    <div class="sub-cont">
        <div class="img">
            <div class="img__btn">
                <span class="m--up">注 册</span>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    function formSubmit() {
        document.getElementById("frm1").submit();
    }
</script>

</html>
