<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/list" title="学生应缴费信息列表" toolbar="#tb"
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
					<input type="button" class="button add" value="批量导入" id="addExcel" />
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 学生收费信息批量导入 -->
<div id="dialogExcel" class="easyui-dialog" title="学生收费信息批量导入" closed="true"
	 style="width:500px; height:300px;overflow: auto;" iconCls="icon-edit">
	<form name="ExcelForm" action="" id="ExcelForm" method="post" enctype="multipart/form-data">
		<div style="margin:11px 11px 0px 25px">
        <span id="moban">
          <a href="javascript:;" onclick="location.href='${contextPath}/files/STUDENT_CHARGE_INFO_TEMPLATE.xlsx'">导入模板下载</a>
        </span><br/><br/>
          <label>选择文件：</label>
          <input name="studentChargeFileBuildInfo" id="studentChargeFileBuildInfo" type="file" class="required"
				 style="width: 200px;"/>
        </span><br/><br/>
			<p align="center">
				<input id="saveExcel" type="button" value="导入Excel"/>
				<input id="cancelExcel" type="button" value="取消"/>
			</p><br>
			<span style="display: none;" id="spanHidden">文件正在上传中.......</span>
		</div>
	</form>
</div>

<!-- 缴费弹框 -->
<div id="chargeDialog" class="easyui-dialog" title="缴费" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="chargeForm" action="" id="chargeForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="studentId" type="hidden" value=""/>
			<input id="isDeposit" type="hidden" value=""/>
			缴费金额：
			<input name="chargeAmount" id="chargeAmount" type="text" style="width: 150px;"/>
			<br/><br/>
			<span id="useDeposit" hidden="hidden">
				使用预缴费金额：
				<input type="radio" name="useDeposit" checked="checked" value="0"/>不使用
				<input type="radio" name="useDeposit" value="1"/>使用
				<br/><br/>
			</span>
			<p align="center">
				<input id="save" type="button" value="缴费"/>
				<input id="cancel" type="button" value="取消"/>
			</p><br>
		</div>
	</form>
</div>


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
	$('#addExcel').live('click',function(){
		$("#dialogExcel").dialog("open");
		$('#studentChargeFileBuildInfo').val("");
		return false;
	});
	$('#cancelExcel').live('click',function(){
		$("#dialogExcel").dialog("close");
		return false;
	});
	$('#cancel').live('click',function(){
		$("#chargeDialog").dialog("close");
		return false;
	});
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/studentChargeInfo/export';
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
		var data = {
			name: $('#studentName').val(),
			admissionTimeBegin: $('#createTimeBegin').val(),
			admissionTimeEnd: $('#createTimeEnd').val()
		};
		return data;
	}

	<!--批量导入-->
	$('#saveExcel').live('click',function(){
		var url = "${contextPath}/studentChargeInfo/importStudentChargeInfo";
		var selNum = $('#studentChargeFileBuildInfo').length;
		var file = $('#studentChargeFileBuildInfo').val();
		var index = file.lastIndexOf(".");
		var ext = file.substring(index + 1, file.length).toLowerCase();
		if(parseInt(selNum)==0 || file=="" || file == null){
			$.messager.alert('系统消息',"请选择Excel文件！！！！","info");
		}
		else if(ext != "xls" && ext != "xlsx"){
			$.messager.alert('系统消息',"请选择Excel文件上传！！！！","info");
		}else{
			$('#ExcelForm').ajaxSubmit({
				url: url,
				cache:false,
				dataType:'json',
				beforeSend: function() {
					$("#spanHidden").show();
					$("#saveExcel").attr("disabled",true);
					$("#cancelExcel").attr("disabled", true);
				} ,
				success: function (data) {
					if (data.success) {
						$.messager.alert('系统消息', '导入成功', "info");
						$('#studentChargeFileBuildInfo').val("");
						$("#datagrid").datagrid("reload");
					} else {
						$.messager.alert('系统消息', data.msg, "error");
					}
					$("#dialogExcel").dialog("close");
				} ,
				complete: function(){
					$("#spanHidden").hide();
					$("#saveExcel").attr("disabled",false);
					$("#cancelExcel").attr("disabled", false);
				},
				error:function(data){
					$.messager.alert('系统消息', data.msg,'error');
				}
			});
		}
	});

	function settings(value, row) {
		var url = '${contextPath}/studentChargeInfo/detail/'+row.studentId;
		var html = '<div style="text-align: center;">';
		html += '<span style="margin-right: 10px;"><a href="javascript:;" ';
		html += 'onclick="addTab(\'学生应缴费详情\',\''+url+'\',true)">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="查看详情" src="${contextPath}/images/m_view.gif">';
		html += '</a></span>';

		html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openChargeDialog(\''+row.studentId+'\',\''+1+'\')">预缴费</a>';
		html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openChargeDialog(\''+row.studentId+'\',\''+0+'\')">缴费</a>';

		html += '</div>';
		return html;
	}

	function openChargeDialog(studentId,deposit) {
		$("#chargeDialog").dialog("open");
		$("#chargeAmount").val("");
		$("#studentId").val(studentId);
		$("#isDeposit").val(deposit);
		$("#useDeposit").css("display","none");
		if(deposit == 0){
			$("#useDeposit").css("display","block");
		}
		return false;
	}

	$('#save').live('click',function(){
		var isDeposit = $("#isDeposit").val();
		var url = "${contextPath}/studentChargeInfo/doCharge";
		if(isDeposit == 1){
			url = "${contextPath}/studentChargeInfo/doDepositCharge";
		}
		var data = {
			studentId: $('#studentId').val(),
			chargeAmount: $('#chargeAmount').val(),
			isUseDeposit: $('input:radio[name="useDeposit"]:checked').val()
		}
		$.messager.confirm('系统消息', "确认缴费！", function (r) {
			if (r) {
				$.ajax({
					url: url,
					cache: false,
					dataType: 'json',
					data: data,
					type: "post",
					success: function (data) {
						if (data.success) {
							$.messager.alert('系统消息', '已完成', "info");
							$("#datagrid").datagrid("reload");
						} else {
							$.messager.alert('系统消息', data.msg,'error');
						}
						$("#chargeDialog").dialog("close");
					},
					error: function (data) {
						$("#chargeDialog").dialog("close");
						$.messager.alert('系统消息', data.msg,'error');
					}

				});
			}
		});
	});

</script>
</body>
</html>
