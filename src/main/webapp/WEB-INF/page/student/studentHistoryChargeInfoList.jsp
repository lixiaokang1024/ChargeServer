<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/historyList" title="学生历史缴费信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" sortable="true" align="center" width="150px">学号</th>
		<th field="studentName" sortable="true" align="center" width="150px">学生姓名</th>
		<th field="chargeAmount" sortable="true" align="center" width="150px">应缴费金额</th>
		<th field="actualChargeAmount" sortable="true" align="center" width="150px">实际缴费金额</th>
		<th field="prepaymentAmount" sortable="true" align="center" width="150px">预缴费剩余金额</th>
		<th field="deposit" align="center" width="130px" >押金</th>
		<th field="operator" width="300px" align="center" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">学生姓名：</td>
				<td style="width: 150px">
					<input type="text" id="studentName" name="studentName" style="width: 110px;" value=""/>
				</td>
				<td style="text-align: right;">入学时间：</td>
				<td colspan="3">
					<input id="admissionTimeBegin" style="width: 125px" name="admissionBegin" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'admissionTimeEnd\')}'})"
						   value=""/>&nbsp;-&nbsp;
					<input id="admissionTimeEnd" style="width: 125px" name="admissionTimeEnd" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'admissionTimeBegin\')}'})"
						   value=""/>
				</td>
			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<script type="text/javascript">
	$(function() {
		/*搜索*/
		$(function(){
			$('#searchLink').click(function(){
				var data = getFormData();
				$('#datagrid').datagrid('load', data);
			});
		});

	});

	function getFormData() {
		var data = {
			studentName: $('#studentName').val(),
			admissionTimeBegin: $('#createTimeBegin').val(),
			admissionTimeEnd: $('#createTimeEnd').val(),
		};
		return data;
	}

	function settings(value, row) {
		var url = '${contextPath}/studentChargeInfo/historyDetail/'+row.studentId;
		var html = '<div style="text-align: center;">';
		html += '<span style="margin-right: 10px;"><a href="javascript:;" ';
		html += 'onclick="addTab(\'学生历史缴费详情\',\''+url+'\',true)">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="查看详情" src="${contextPath}/images/m_view.gif">';
		html += '</a></span></div>';
		return html;
	}

</script>
</body>
</html>
