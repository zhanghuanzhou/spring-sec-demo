<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>
    <link href="assets/plugins/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/login.css" rel="stylesheet">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/login.js"></script>
</head>
<body style="background-color: #fff !important;">
<div class="container">
    <form class="form-signin" id="myForm" onsubmit="return false;">
        <h3 class="form-signin-heading">登录您的账号</h3>
        <div class="alert alert-danger form-error hide">
            <!-- <a href="#" class="close" data-dismiss="alert" aria-hidden="true">
              &times;
               </a> -->
            <span class="message"></span>
        </div>
        <label for="username" class="sr-only">Email address</label> <input type="text" name="username"
                                                                          class="form-control" placeholder="登录名"
                                                                          autofocus>
        <label for="password" class="sr-only">Password</label> <input type="password" name="password"
                                                                      class="form-control" placeholder="密码">
        <%--<div class="hide verify">
            <input type="text" name="code" class="form-control" placeholder="验证码"><img src="#" id="verifyImage"><a
                id="changeVerifyImage">换一张</a>
        </div>--%>
        <div class="checkbox">
            <label> <input type="checkbox" name="remember" value="1">
                记住登录
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">立即登录</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div> <!-- /container -->
</body>
</html>