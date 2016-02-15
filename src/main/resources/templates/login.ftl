<!DOCTYPE html>
<html >
<head>
    <title>Spring Security Example</title>
</head>
<body>
<#--<#if param.error??>
    Invalid username and password.
</#if>-->

<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><label> Remember me: <input type="checkbox" name="remember_me_checkbox"/></label></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
</body>
</html>