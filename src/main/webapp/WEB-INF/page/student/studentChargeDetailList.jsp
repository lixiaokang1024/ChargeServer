<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/count" title="学生应缴费信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" sortable="true" align="center" width="50px">学号</th>
		<th field="studentName" sortable="true" align="center" width="130px">学生姓名</th>
		<th field="gradeName" sortable="true" align="center" width="60px">年级</th>
		<th field="className" sortable="true" align="center" width="60px">班级</th>
		<th field="chargeProjectName" sortable="true" align="center" width="130px">缴费项目</th>
		<th field="chargeAmount" sortable="true" align="center" width="130px">应缴费金额</th>
		<th field="actualChargeAmount" sortable="true" align="center" width="130px">实际缴费金额</th>
		<th field="useDepositAmount" align="center" width="130px" >使用预缴费金额</th>
		<th field="customOfferAmount" align="center" width="130px" >自定义优惠金额</th>
		<th field="chargeTimeStr" align="center" width="130px" >应缴费时间</th>
		<th field="actualChargeTimeStr" align="center" width="130px" >实际缴费时间</th>
		<th field="payTypeStr" align="center" width="130px" >缴费方式</th>
		<th field="statusStr" align="center" width="130px" >缴费状态</th>
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

				<td style="text-align: right;">年级：</td>
				<td>
					<select id="gradeId" style="width: 150px;">
					</select>
				</td>

				<td style="text-align: right;">班级：</td>
				<td>
					<select id="classId" style="width: 150px;">
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">缴费状态：</td>
				<td>
					<select id="status" style="width: 150px;">
						<option value="-1">全部</option>
						<option value="0">未缴费</option>
						<option value="1">部分缴费</option>
						<option value="2">已缴费</option>
						<option value="3">已欠费</option>
					</select>
				</td>
				<td style="text-align: right;">缴费日期：</td>
				<td colspan="3">
					<input id="payTimeBegin" style="width: 125px" name="payTimeBegin" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'payTimeEnd\')}'})"
						   value=""/>&nbsp;-&nbsp;
					<input id="payTimeEnd" style="width: 125px" name="payTimeEnd" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'payTimeBegin\')}'})"
						   value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button export" value="批量导出" id="exportLink"/>
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

		$(function () {
			$("#gradeId").empty();
			var param={page:1,rows:2000}
			$.ajax({
				url:"${contextPath}/school/gradeList",
				dataType:'json',
				data:param,
				success:function(data){
					var gradeList = data.rows;
					$("#gradeId").append('<option value="-1">全部</option>');
					for(i=0;i<gradeList.length;i++){
						var grade = gradeList[i];
						$("#gradeId").append('<option value="'+grade.id+'">'+grade.name+'</option>');
					}
				}
			});
		});

	});

	$('#gradeId').live('click',function(){
		$("#classId").empty();
		var gradeId = $("#gradeId").val();
		var param={page:1,rows:2000,gradeId:gradeId}
		$.ajax({
			url:"${contextPath}/school/calssList",
			dataType:'json',
			data:param,
			success:function(data){
				var classList = data.rows;
				$("#classId").append('<option value="-1">全部</option>');
				for(i=0;i<classList.length;i++){
					var classInfo = classList[i];
					$("#classId").append('<option value="'+classInfo.id+'">'+classInfo.name+'</option>');
				}
			}
		});
		return false;
	});

	$('#cancel').live('click',function(){
		$("#chargeDialog").dialog("close");
		return false;
	});
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/studentChargeInfo/exportStudentChargeInfo';
		$.messager.confirm('系统消息', "是否导出！", function (r) {
			if (r) {
				$.ajax({
					url: url,
					cache: false,
					dataType: 'json',
					data: datas,
					type: "post",
					beforeSend: function () {
						showOrHideLoading(true, "导出中");
					},
					success: function (json) {
						var data = json.data;
						if (!json.success) {
							alert(json.msg);
							return;
						}
						window.location.href = '${contextPath}/download?fileName=' + data.fileName + '&filePath=' + data.filePath;
					},
					complete: function () {
						showOrHideLoading(false, "");
					}

				});
			}
		});
	});

	function getFormData() {
		var payTimeBegin = $('#payTimeBegin').val();
		if(payTimeBegin != ''){
			payTimeBegin = payTimeBegin + " 00:00:00";
		}
		var payTimeEnd = $('#payTimeEnd').val();
		if(payTimeEnd != ''){
			payTimeEnd = payTimeEnd + " 23:59:59";
		}
		var data = {
			name: $('#studentName').val(),
            gradeId:$("#gradeId option:selected").val(),
            classId:$("#classId option:selected").val(),
			status:$('#status').val(),
			payTimeBegin: payTimeBegin,
			payTimeEnd: payTimeEnd
		};
		return data;
	}

</script>
</body>
</html>
