<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/1
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<c:set var="contextPath" value="<%=basePath %>" scope="request"></c:set>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta name="description" content="幼儿园EMA系统！">
    <title>缴费系统 - 用户登录</title>
    <link rel="stylesheet" href="${contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/css/font-awesome.min.css">
    <style type="text/css">
        html, body {
            height: 100%;
        }

        .box {
            background: url("${contextPath}/images/loginBg.jpg") no-repeat center center;
            background-size: cover;

            margin: 0 auto;
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .login-box {
            width: 100%;
            max-width: 500px;
            height: 400px;
            position: absolute;
            top: 50%;

            margin-top: -200px;
            /*设置负值，为要定位子盒子的一半高度*/

        }

        @media screen and (min-width: 500px) {
            .login-box {
                left: 50%;
                /*设置负值，为要定位子盒子的一半宽度*/
                margin-left: -250px;
            }
        }

        .form {
            width: 100%;
            max-width: 500px;
            height: 275px;
            margin: 2px auto 0px auto;
        }

        .login-content {
            border-bottom-left-radius: 8px;
            border-bottom-right-radius: 8px;
            height: 250px;
            width: 100%;
            max-width: 500px;
            background-color: rgba(255, 250, 2550, .6);
            float: left;
        }

        .input-group {
            margin: 30px 0px 0px 0px !important;
        }

        .form-control,
        .input-group {
            height: 40px;
        }

        .form-actions {
            margin-top: 30px;
        }

        .form-group {
            margin-bottom: 0px !important;
        }

        .login-title {
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            padding: 20px 10px;
            background-color: rgba(0, 0, 0, .6);
        }

        .login-title h1 {
            margin-top: 10px !important;
        }

        .login-title small {
            color: #fff;
        }

        .link p {
            line-height: 20px;
            margin-top: 30px;
        }

        .btn-sm {
            padding: 8px 24px !important;
            font-size: 16px !important;
        }

        .flag {
            position: absolute;
            top: 10px;
            right: 10px;
            color: #fff;
            font-weight: bold;
            font: 14px/normal "microsoft yahei", "Times New Roman", "宋体", Times, serif;
        }
        .glyphicon-user:before{content:none;}
        .glyphicon-user{width:18px;height:18px;background:url("${contextPath}/images/login/login-n.png") no-repeat center center;}
        .glyphicon-lock:before{content:none;}
        .glyphicon-lock{width:18px;height:18px;background:url("${contextPath}/images/login/login-pwd.png") no-repeat center center;}
    </style>
</head>
<body>
<div class="box">
    <div class="login-box">
        <div class="login-title text-center">
            <h1>
                <small>幼儿园EMA系统</small>
            </h1>
        </div>
        <div class="login-content ">
            <div class="form" style="font-family: 'Glyphicons Halflings';">
                <form id="modifyPassword" class="form-horizontal" action="#" method="post">
                    <span id="referer" name="referer"></span>
                    <div class="form-group">
                        <div class="col-xs-10 col-xs-offset-1">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" id="username" name="username" class="form-control" placeholder="用户名">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-10 col-xs-offset-1">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                <input type="password" id="password" name="password" class="form-control" placeholder="密码">
                            </div>
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-12 text-center">
                            <button type="button" id="login" class="btn btn-sm btn-success">
                                登录
                            </button>
                            <button type="button" id="reset" class="btn btn-sm btn-danger">
                                重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 引入jQuery -->
<script src="${contextPath}/js/hm.js"></script><script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/jquery.cookie.js"></script>
<script type="text/javascript">
    $(function () {
        $('#password').keyup(function (event) {
            if (event.keyCode == "13") {
                $("#login").trigger("click");
                return false;
            }
        });

        $("#login").on("click", function () {
            submitForm();
        });

        function submitForm() {
            var formData = {
                userName: $('#username').val(),
                password: $('#password').val()
            };
            $.ajax({
                type: 'POST',
                url: '<%=request.getContextPath()%>/user/login',
                data: formData,
                success: function (data) {
                    if (!data.status) {
                        $('#referer').html(data.msg).css("color","red");
                    } else {
                        top.location.href="<%=request.getContextPath()%>/index.jsp";
                    }
                },
                error: function () {
                    alert("error！");
                }
            });
        }

        $("#reset").on("click", function () {
            $("#username").val("");
            $("#password").val("");
            $('#referer').html("");
        });
    });
</script>
</body>
</html>
