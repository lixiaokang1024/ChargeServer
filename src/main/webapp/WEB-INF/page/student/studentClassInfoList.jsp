<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentClassInfo/list" title="学生班级信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentName" sortable="true" width="150px">学生姓名</th>
		<th field="className" sortable="true" width="150px">班级</th>
		<th field="gradeName" sortable="true" width="150px">年级</th>
		<th field="isGraduateStr" sortable="true" width="130px">毕业状态</th>
		<th field="operator"  width="50px">操作</th>
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
					<input type="button" class="button export" value="批量导出" id="exportLink"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 学生信息批量导入 -->
<div id="dialogExcel" class="easyui-dialog" title="学生班级信息批量导入" closed="true"
	 style="width:500px; height:300px;overflow: auto;" iconCls="icon-edit">
	<form name="ExcelForm" action="" id="ExcelForm" method="post" enctype="multipart/form-data">
		<div style="margin:11px 11px 0px 25px">
        <span id="moban">
          <a href="javascript:;" onclick="location.href='${contextPath}/files/STUDENT_CLASS_INFO_TEMPLATE.xlsx'">导入模板下载</a>
        </span><br/><br/>
          <label>选择文件：</label>
          <input name="studentClassFileBuildInfo" id="studentClassFileBuildInfo" type="file" class="required"
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
		$('#studentClassFileBuildInfo').val("");
		return false;
	});
	$('#cancelExcel').live('click',function(){
		$("#dialogExcel").dialog("close");
		return false;
	});
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/student/export';
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
			studentName: $('#studentName').val(),
			admissionTimeBegin: $('#createTimeBegin').val(),
			admissionTimeEnd: $('#createTimeEnd').val(),
		};
		return data;
	}

	<!--批量导入-->
	$('#saveExcel').live('click',function(){
		var url = "${contextPath}/studentClassInfo/importStudentClassInfo";
		var selNum = $('#studentClassFileBuildInfo').length;
		var file = $('#studentClassFileBuildInfo').val();
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
						$('#studentClassFileBuildInfo').val("");
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

</script>
</body>
</html>
