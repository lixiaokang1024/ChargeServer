<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<!-- 学生应缴费详情页面-->
<body>
<div style="padding:5px;height:auto">
	<table class="division">
		<tr>
			<th width="150px">学生姓名</th>
			<th width="150px">缴费项目</th>
			<th width="150px">缴费金额</th>
			<th width="130px">缴费时间</th>
			<th width="130px">缴费方式</th>
			<th width="130px">缴费状态</th>
		</tr>
		<c:forEach var="basic" items="${data}">
			<tr align="center">
				<td>${ basic.studentName }</td>
				<td>${ basic.chargeProjectName }</td>
				<td>${ basic.chargeAmount }</td>
				<td>${ basic.chargeTimeStr }</td>
				<td>${ basic.payTypeStr }</td>
				<td>${ basic.statusStr }</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="m_navbox" style=" width:100%; text-align:center; margin:0 auto; height:40px;">
	<input type="button" value="返 回" class="button return"  id= "closeTab">
</div>
</body>
</html>
