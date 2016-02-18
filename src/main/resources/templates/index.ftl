<!DOCTYPE HTML>
<html >
<head>
    <title>Welcome to your wish list!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- Material Design fonts -->
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-material-design.css">
    <link rel="stylesheet" type="text/css" href="/css/ripples.css">
    <link rel="stylesheet" href="/css/main.css"/>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <link rel="stylesheet" href="/css/bootstrap-theme.css"/>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
</head>
<body  >
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-login">
                <img width="300" height="300"  class="center-block" src="/images/Present1.png"/>
                <h2 align="center">Your wish list</h2>
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link">Sign in</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link">Sign up</a>
                        </div>
                    </div>
                    <hr/>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="/login" method="post" role="form" style="display: block;">
                                <div class="form-group">
                                    <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" />
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password"/>
                                </div>
                                <div class="form-group text-center">
                                    <div class="checkbox">
                                        <label>
                                            <label for="remember"> Remember Me</label>
                                        </label>
                                        <label>
                                            <input type="checkbox" tabindex="3"  name="remember_me_checkbox" id="remember_me_checkbox"/>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Sign In"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="text-center">
                                                <a href="/password/forgot" tabindex="5" class="forgot-password">Forgot Password?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form"   role="form" style="display: none;">
                                <div class="form-group">
                                    <input type="text" name="userLogin" id="userLogin" tabindex="1" class="form-control" placeholder="User login" required="required" />
                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email" required="required"  />
                                </div>
                                <div class="form-group">
                                    <input type="text" name="firstName" id="regUserName" tabindex="1" class="form-control" placeholder="Name" required="required"  />
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="regpassword" tabindex="2" class="form-control" placeholder="Password" required="required"/>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="Register Now"/>
                                        </div>
                                    </div>
                                </div>
                            </form>
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
<script src="/js/lib/bootstrap/material.js"></script>
<script src="/js/lib/bootstrap/ripples.js"></script>
 <script>
    $(function () {
        $.material.init();
    });
</script>

<script src="/js/lib/html5.js"></script>
<script src="/js/main.js"></script>

</body>
</html>