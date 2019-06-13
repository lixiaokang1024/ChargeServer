<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta content="IE=7" http-equiv="X-UA-Compatible" />
    <link rel="stylesheet" type="text/css" href="css/easyui.css" />
    <link rel="stylesheet" type="text/css" href="css/frame.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/menu.js"></script>
    <title>沙河小马幼儿园</title>
    <link rel="shortcut icon" href="http://corp.sfbest.com/favicon.ico" />
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
        $(function(){
            $('#editPass').live('click',function(){
                $.dialog({title:'修改密码',width:500,height:300,url:$(this).attr('href')});
                return false;
            });
        })

    </script>

</head>
<body class="easyui-layout">
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
                    <span>缴费管理</span>
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
            <img src="images/wms/lock_edit.gif"><a href="/default/editPass" id="editPass">修改密码</a>
            <img src="images/wms/page_go.gif"><a href="/charge/user/logout">退出</a>
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
						<a href="charge/index" title="费用项目配置" class="newTab">费用项目配置</a>
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
                    <span>
                        <a href="student/list" title="学生基础信息" class="newTab">学生基础信息</a>
					</span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_3">
            <a class="parent" href="javascript:void(0);">缴费管理</a>
            <ul>
                <li>
                    <span>
                        <a href="studentChargeInfo/index" title="学生预缴费" class="newTab">学生预缴费</a>
					</span>
                    <span>
                        <a href="/order/index" title="学生正常收费" class="newTab">学生正常收费</a>
					</span>
                    <span>
                        <a href="/order/index" title="退费管理" class="newTab">退费管理</a>
					</span>
                    <span>
                        <a href="/order/index" title="报表管理" class="newTab">报表管理</a>
					</span>
                </li>
            </ul>
        </li>

        <li class="leftMenu menu_4">
            <a class="parent" href="javascript:void(0);">系统管理</a>
            <ul>
                <li>
                    <span>
                        <a href="/greetingCards/index" title="用户管理" class="newTab">用户管理</a>
					</span>
                    <span>
                        <a href="/greetingCards/index" title="角色管理" class="newTab">角色管理</a>
					</span>
                    <span>
                        <a href="/greetingCards/index" title="权限管理" class="newTab">权限管理</a>
					</span>
                </li>
            </ul>
        </li>

    </ul>
</div>
<div region="center" style="background:#f1f1f1;padding:12px 12px 12px 4px;">
    <div class="easyui-tabs" fit="true" border="false" id="tab" >
        <div title="后台首页" style="background: url('images/school/main.jpg') no-repeat center 160px ">
            <h5 style="text-align: center;font-size:24px;margin-top:320px;color: orangered;">欢迎使用幼儿园收费系统</h5>
        </div>
    </div>
</div>
</body>
</html>