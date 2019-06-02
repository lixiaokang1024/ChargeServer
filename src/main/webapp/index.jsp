<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
    <script type="text/javascript" src="js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="js/easysui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/addTabs.js"></script>
</head>
<body>
<div style="margin:20px 0;"></div>
<div class="easyui-layout" style="width:100%;height:650px;">
    <div data-options="region:'north'" style="height:50px">

    </div>
    <div data-options="region:'south'" style="height:40px;"></div>
    <div data-options="region:'west',split:true" title="West" style="width:140px;background-color: #2e6da4;">
        <div>
            <ul class="easyui-tree">
                <li>
                    <span>学生信息管理</span>
                    <ul>
                        <li>
                            <a href="#" class="easyui-tree" onclick="addTab('二电饭锅','http://www.baidu.com/')">123</a>
                        </li>
                        <li>
                            <a href="#" class="easyui-tree" onclick="addTab('456','http://www.baidu.com/')">456</a>
                        </li>
                        <li>
                            <a href="#" class="easyui-tree" onclick="addTab('789','http://www.baidu.com/')">789</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div data-options="region:'center',title:'Main Title',iconCls:'icon-ok'" id="tt" class="easyui-tabs" style="width:400px;height:250px;">
        <div title="Home">
            <h5 style="position: relative;text-align: center;vertical-align:middle;font-size: larger">欢迎使用幼儿园收费系统</h5>
        </div>
    </div>
</div>
</body>
</html>
