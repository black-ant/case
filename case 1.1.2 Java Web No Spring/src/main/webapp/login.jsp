<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<html>
<head>
    <title>登录注册页面</title>
</head>
<body>

<form action="loginServlet" method="post" style="padding-top:-700px;">
    用户名：<input type="text" name="name" value=""><br><br>
    密码： <input type="password" name="password" value=""><br><br>
    <input type="submit" value="登录" name="login"><input type="reset" value="重置"><br>
</form>

<form action="register.jsp">
    <input type="submit" value="新用户注册">
</form>

</body>
</html>
