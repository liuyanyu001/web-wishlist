<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Spring Boot and Thymeleaf example</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/icon?family=Material+Icons">
    <!-- Bootstrap Material Design -->
    <link rel="stylesheet" type="text/css" href="/css/bootstrap-material-design.css">
    <link rel="stylesheet" type="text/css" href="/css/ripples.css">
    <link rel="stylesheet" href="/css/profile.css"/>
    <link rel="stylesheet" href="/css/bootstrap.css"/>
    <link rel="stylesheet" href="/css/bootstrap-theme.css"/>
    <link rel="stylesheet" href="/css/font-awesome.css"/>

</head>
<body>
<!-- header logo: style can be found in header.less -->
<header class="header">
    <div class="navbar navbar-default ">
        <div class="container-fluid mainnav ">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand"  href="/">Your wish</a>
            </div>
            <div class="navbar-collapse collapse navbar-responsive-collapse">
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control col-md-8" placeholder="Search users">
                    </div>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown   user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-user"></i>
                            <span>${user.firstName} ${user.lastName} <i class="caret"></i></span>
                        </a>
                        <ul class="dropdown-menu dropdown-custom dropdown-menu-right">
                            <li class="dropdown-header text-center">Account</li>

                            <li>
                                <a href="#">
                                    <i class="fa fa-user fa-fw pull-right"></i>
                                    Profile
                                </a>
                                <a data-toggle="modal" href="#modal-user-settings">
                                    <i class="fa fa-cog fa-fw pull-right"></i>
                                    Settings
                                </a>
                            </li>

                            <li class="divider"></li>

                            <li>
                                <a href="/logout"><i class="fa fa-ban fa-fw pull-right"></i> Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>

<div id="wrapper" >

    <!-- Sidebar -->
    <div id="sidebar-wrapper">

        <ul class="sidebar-nav">
            <img src="https://pp.vk.me/c628222/v628222045/1dbf0/K_4HePQkPzE.jpg" class="img-thumbnail avatar center-block"
                 alt="User Image"/>
            <div class="text-center ">
                <p>${user.firstName} ${user.lastName}</p>
            </div>
            <li>
                <a href="#">My Page</a>
            </li>
            <li>
                <a href="#">My Followers</a>
            </li>
            <li>
                <a href="#">My News</a>
            </li>
        </ul>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <h1>Main content will be here</h1>
                    <p>This template has a responsive menu toggling system. The menu will appear collapsed on smaller screens, and will appear non-collapsed on larger screens. When toggled using the button below, the menu will appear/disappear. On small screens, the page content will be pushed off canvas.</p>

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
<script src="/js/lib/bootstrap/material.js"></script>
<script src="/js/lib/bootstrap/ripples.js"></script>
<script>
    $(function () {
        $.material.init();
    });</script>
</body>
</html>