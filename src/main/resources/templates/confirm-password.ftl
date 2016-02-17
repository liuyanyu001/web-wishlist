<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultraq.net.nz/web/thymeleaf/layout">
<head>
    <title>Welcome to your wish list!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="/css/main.css"/>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                        <#if msg??>
                            <p class="text-center alert alert-success">${msg}</p>
                            <a href="/">Main page</a>
                        <#else>
                            <form id="confirmpasswprd-form" method="POST" action="/password/confirm" role="form">
                            <#assign m_token = "${token}">
                            <#if m_token??>
                                    <input type="hidden" name="token" value="${m_token}">
                                </#if>
                                <div class="form-group">
                                    <input type="password" name="password" tabindex="1" class="form-control"
                                           placeholder="Enter new password" required="required"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="changepassword-submit" id="register-submit"
                                                   tabindex="4" class="form-control btn btn-register"
                                                   value="Confirm change"/>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/js/lib/jquery/jquery-2.2.0.min.js"></script>
<script src="/js/lib/bootstrap/bootstrap.min.js"></script>
<script src="/js/lib/bootstrap/bootstrap-modal.js"></script>
<script src="/js/lib/jquery/jquery.alert.js"></script>
<script src="/js/lib/jquery/jquery.confirm.js"></script>
<script src="/js/lib/html5.js"></script>
</body>
</html>