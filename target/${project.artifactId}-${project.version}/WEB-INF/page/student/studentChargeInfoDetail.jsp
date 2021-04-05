<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<!-- 学生应缴费详情页面-->
<body>
<div style="padding:5px;height:auto">
	<table class="division">
		<tr>
			<th align="center" width="150px">学号</th>
			<th align="center" width="150px">学生姓名</th>
			<th align="center" width="150px">缴费项目</th>
			<th align="center" width="150px">应缴费金额</th>
			<th align="center" width="150px">实际缴费金额</th>
			<th align="center" width="150px">使用预缴费金额</th>
			<th align="center" width="150px">自定义优惠金额</th>
			<th align="center" width="130px">应缴费时间</th>
			<th align="center" width="130px">实际缴费时间</th>
			<th align="center" width="130px">缴费方式</th>
			<th align="center" width="130px">缴费状态</th>
		</tr>
		<c:forEach var="basic" items="${data}">
			<tr align="center">
				<td>${ basic.studentId }</td>
				<td>${ basic.studentName }</td>
				<td>${ basic.chargeProjectName }</td>
				<td>${ basic.chargeAmount }</td>
				<td>${ basic.actualChargeAmount }</td>
				<td>${ basic.useDepositAmount }</td>
				<td>${ basic.customOfferAmount }</td>
				<td>${ basic.chargeTimeStr }</td>
				<td>${ basic.actualChargeTimeStr }</td>
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
