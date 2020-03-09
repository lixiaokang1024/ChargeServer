<%@page  contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<c:set var="contextPath" value="<%=basePath %>" scope="request"></c:set>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="IE=7" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="css/frame.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery-form.js"></script>
    <script type="text/javascript" src="js/menu.js"></script>
    <title>EMA系统</title>
    <link type="text/css" href="css/easyui/themes/default/easyui.css" rel="stylesheet" />
    <link type="text/css" href="css/admpanel.css" rel="stylesheet" media="screen, projection" />
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript" src="js/common.js"></script>

    <script type="text/javascript">
        function addTab(title, url,flag){
            if ($('#tab').tabs('exists', title)){
                //$('#tab').tabs('select', title);
                $('#tab').tabs('close', title);
            }
            var content = "<iframe scrolling='auto' frameborder='0'  src='"+url+"' style='width:100%;height:100%;'></iframe>";
            $('#tab').tabs('add',{
                title:title,
                content:content,
                closable:flag,
                cache:false
            });
        }
        // $(function(){
        //     $('#editPass').live('click',function(){
        //         $.dialog({title:'修改密码',width:500,height:300,url:$(this).attr('href')});
        //         return false;
        //     });
        // })
        $(function(){
            $(".pw").blur(function () {
                if($(this).val() == ''){
                    var name = $(this).attr("name") + "Remin";
                    $("#"+name).css("display","block");
                }
            });
            $(".pw").focus(function () {
                var name = $(this).attr("name") + "Remin";
                $("#"+name).css("display","none");
            });
        })
        $('#editPass').live('click',function(){
            $("#mpwDialog").dialog("open");
            $('#oldPassword').val('');
            $('#newPassword').val('');
            $('#confirmPW').val('');
            return false;
        });
        $('#cancel').live('click',function(){
            $("#mpwDialog").dialog("close");
            return false;
        });

        $('#save').live('click',function(){
            var url = "${contextPath}/user/modifyPW";
            var oldPassword = $('#oldPassword').val();
            var newPassword = $('#newPassword').val();
            var confirmPW = $('#confirmPW').val();
            if(newPassword != confirmPW){
                $.messager.alert('系统消息', '确认密码不一致', "error");
                return false;
            }
            $('#mpwForm').ajaxSubmit({
                url: url,
                cache:false,
                dataType:'json',
                beforeSend: function() {
                    $("#save").attr("disabled",true);
                    $("#cancel").attr("disabled", true);
                } ,
                success: function (data) {
                    $("#mpwDialog").dialog("close");
                    if (data.success) {
                        $.messager.alert('系统消息', '已完成', "info");
                    } else {
                        layer.alert(data.msg);
                    }
                } ,
                complete: function(){
                    $("#save").attr("disabled",false);
                    $("#cancel").attr("disabled", false);
                },
                error:function(data){
                    $.messager.alert('系统消息', data.msg,'error');
                }
            });
        });

    </script>

</head>
<body class="easyui-layout">
<!-- 修改密码 -->
<div id="mpwDialog" class="easyui-dialog" title="修改密码" closed="true"
     style="width:350px; height:250px;overflow: auto;" iconCls="icon-edit">
    <form name="mpwForm" action="" id="mpwForm" method="post">
        <div style="margin:11px 11px 0px 25px">
            原密码：
            <input name="oldPassword" class="pw" id="oldPassword" type="password" style="width: 150px;"/>
            <span id="oldPasswordRemin" style="color: red" hidden="hidden">原密码不能为空</span>
            <br/><br/>
            新密码：
            <input name="newPassword" class="pw" id="newPassword" type="password" style="width: 150px;"/>
            <span id="newPasswordRemin" style="color: red" hidden="hidden">新密码不能为空</span>
            <br/><br/>
            确认密码：
            <input name="confirmPW" class="pw" id="confirmPW" type="password" style="width: 150px;"/>
            <span id="confirmPWRemin" style="color: red" hidden="hidden">确认密码不能为空</span>
            <br/><br/>
            <p align="center">
                <input id="save" type="button" value="确认"/>
                <input id="cancel" type="button" value="取消"/>
            </p>
        </div>
    </form>
</div>
<div region="north"  style="height:auto;padding:0px; overflow:hidden;">
    <div id="header" class="header" >
        <div class="wms_logo"></div>
        <div class="hd2" id="topMenu" >
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_1">
                    <span>基础信息管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_2">
                    <span>学生管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_3">
                    <span>费用管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_5">
                    <span>统计管理</span>
                </a>
            </div>
            <div class="top-menu" >
                <a class="tab" href="javascript:void(0)" id="menu_4">
                    <span>系统管理</span>
                </a>
            </div>
        </div>
        <div class="top-link" style="width:300px;">
            <img src="images/wms/contact_blue.gif">欢迎:${user.userName}登陆
            <img src="images/wms/lock_edit.gif"><a href="#" id="editPass">修改密码</a>
            <img src="images/wms/page_go.gif"><a href="<%=request.getContextPath()%>/user/logout">退出</a>
        </div>
    </div>
</div>
<div id="left" region="west" split="true" title="" style="padding:0px;overflow:hidden;margin:0;overflow:auto; background:#417eb7;">
    <ul id="menu">
        <li class="leftMenu menu_1">
            <a class="parent" href="javascript:void(0);">基础信息管理</a>
            <ul>
                <li>
                    <span>
                        <a href="school/gradeIndex" title="年级配置" class="newTab">年级配置</a>
                    </span>
                    <span>
                        <a href="school/classIndex" title="班级配置" class="newTab">班级配置</a>
                    </span>
                    <span>
						<a href="charge/index" title="缴费项目配置" class="newTab">缴费项目配置</a>
					</span>
                    <span>
						<a href="charge/payProjectIndex" title="日常支出项目配置" class="newTab">日常支出项目配置</a>
					</span>
                    <span>
						<a href="charge/incomeProjectIndex" title="日常收入项目配置" class="newTab">日常收入项目配置</a>
					</span>
                    <span>
                        <a href="discount/discountIndex" title="折扣配置" class="newTab">折扣配置</a>
                    </span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_12">
            <a class="parent" href="javascript:void(0);">待定</a>
            <ul>
                <li>
                    <span>
                        <a href="/brand/index" title="待定" class="newTab">待定</a>
					</span>
                </li>
            </ul>
        </li>


        <li class="leftMenu menu_2">
            <a class="parent" href="javascript:void(0);">学生管理</a>
            <ul>
                <li>
                    <span>
                        <a href="student/index" title="学生基础信息" class="newTab">学生基础信息</a>
					</span>
                    <span>
                        <a href="studentClassInfo/index" title="学生班级维护" class="newTab">学生班级维护</a>
					</span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_3">
            <a class="parent" href="javascript:void(0);">缴费管理</a>
            <ul>
                <li>
                    <span>
                        <a href="studentChargeInfo/index" title="应缴费学生" class="newTab">应缴费学生</a>
					</span>
                    <span>
                        <a href="studentChargeInfo/expireIndex" title="已欠费学生" class="newTab">已欠费学生</a>
					</span>
                    <span>
                        <a href="studentChargeInfo/historyIndex" title="学生历史缴费" class="newTab">学生历史缴费</a>
					</span>
                    <span>
                        <a href="studentChargeInfo/receiptIndex" title="补打小票" class="newTab">补打小票</a>
					</span>
                </li>
            </ul>
            <a class="parent" href="javascript:void(0);">日常收支管理</a>
            <ul>
                <li>
                    <span>
                        <a href="charge/payProjectIoIndex" title="日常支出" class="newTab">日常支出</a>
					</span>
                </li>
                <li>
                    <span>
                        <a href="charge/incomeProjectIoIndex" title="日常收入" class="newTab">日常收入</a>
					</span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_5">
            <a class="parent" href="javascript:void(0);">统计管理</a>
            <ul>
                <li>
                    <span>
                        <a href="studentChargeInfo/countIndex" title="学生缴费统计" class="newTab">学生缴费统计</a>
					</span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_4">
            <a class="parent" href="javascript:void(0);">系统管理</a>
            <ul>
                <li>
                    <span>
                        <a href="user/userIndex" title="用户管理" class="newTab">用户管理</a>
					</span>
                    <span>
                        <a href="user/roleIndex" title="角色管理" class="newTab">角色管理</a>
					</span>
                    <span>
                        <a href="user/userIndex" title="权限管理" class="newTab">权限管理</a>
					</span>
                </li>
            </ul>
        </li>

    </ul>
</div>
<div region="center" style="background:#f1f1f1;padding:12px 12px 12px 4px;">
    <div class="easyui-tabs" fit="true" border="false" id="tab" >
        <div title="后台首页" style="background: url('images/school/main.jpg') no-repeat center 160px ">
            <h5 style="text-align: center;font-size:24px;margin-top:120px;color: orangered;">欢迎使用汇通幼儿园收费系统</h5>
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                <h6 style="text-align: center">地址：xxxxxxxxx &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话：xxxxxxxxx</h6>
                <h6 style="text-align: center">网站：xxxxxxxxx &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱：xxxxxxxxx</h6>
                <h6 style="text-align: center">公众号：xxxxxxxxx</h6>
        </div>
    </div>
</div>
</body>
</html>