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
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
    <script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
    <link type="text/css" href="${contextPath}/css/admpanel.css" rel="stylesheet" media="screen, projection" />
    <link type="text/css" href="${contextPath}/css/easyui/themes/default/easyui.css" rel="stylesheet" />
    <link type="text/css" href="${contextPath}/css/easyui/themes/icon.css" rel="stylesheet" />
    <script type="text/javascript" src="${contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${contextPath}/js/jquery-form.js"></script>
    <script type="text/javascript" src="${contextPath}/js/My97DatePicker/WdatePicker.js"></script>

</head>
<script type="text/javascript">
    var contextPath = '${contextPath}';
    function exportExcelxpDatasource(method,parmas,datasource){
        document.getElementById("datasource").value = datasource ;
        document.getElementById("method").value = method;
        document.getElementById("parmas").value = parmas;
        var fileForm = document.forms['myFormxp'];
        fileForm.submit();
    }

    function exportExcelxp(method,parmas){
        document.getElementById("method").value = method;
        document.getElementById("parmas").value = parmas;
        var fileForm = document.forms['myFormxp'];
        fileForm.submit();
    }
</script>
<form method="post" action="<%=request.getContextPath() %>/project/manage/exportExcelxp" name="myFormxp" id='myFormxp'>
    <input id="method" name="method" type="hidden" >
    <input id="parmas" name="parmas" type="hidden" >
    <input id="datasource" name="datasource" type="hidden" >
</form>

<div id='loading-mask' hidden="hidden"></div>
<div id="loading" hidden="hidden">
    <div class="loading-indicator">
        <img src="${contextPath}/images/finder_loading.gif" width="32" height="32" /><br/>
        <span id="loading-msg">Loading ...</span>
    </div>
</div>
<script type="text/javascript">
    function showOrHideLoading(op,content){
        if(content == ""){
            content = "加载中";
            $("#loading-msg").html(content+" ...");
        }else{
            $("#loading-msg").html(content+" ...")
        }
        if(op == true){
            $("#loading-mask").show();
            $("#loading").show();
        }else{
            $("#loading-mask").hide();
            $("#loading").hide();
        }
    }
</script>
