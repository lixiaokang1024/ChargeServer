<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentClassInfo/list" title="学生班级信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" align="center" sortable="true" width="50px">学号</th>
		<th field="studentName" align="center" sortable="true" width="100px">学生姓名</th>
		<th field="className" align="center" sortable="true" width="100px">班级</th>
		<th field="gradeName" align="center" sortable="true" width="100px">年级</th>
		<th field="isGraduateStr" align="center" sortable="true" width="100px">毕业状态</th>
		<th field="admissionTime" align="center" sortable="true" width="150px">入学时间</th>
		<th field="graduateTime" align="center" sortable="true" width="150px">毕业时间</th>
		<th field="remark" align="center" sortable="true" width="130px">备注</th>
		<th field="operator" align="center" width="100px" formatter="settings">操作</th>
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
				<td style="text-align: right;">毕业状态：</td>
				<td style="width: 150px">
					<select id="graduateStatus">
						<option value="0">未毕业</option>
						<option value="1">已毕业</option>
						<option value="2">已退学</option>
						<option value="-1">全部</option>
					</select>
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
					<input type="button" class="button upgrade" value="年级一键升级" id="upGrade"/>
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
          <a href="javascript:;" onclick="location.href='${contextPath}/files/学生班级信息导入模板.xlsx'">导入模板下载</a>
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

<!-- 添加年级 -->
<div id="editClassInfoDialog" class="easyui-dialog" title="配置学生班级信息" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addForm" action="" id="addForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="studentId" type="hidden" value=""/>
			学生姓名：<input id="studentNameDialog" type="text" disabled="disabled" value=""/>
			<br/><br/>
			所属年级：
			<select id="gradeId" style="width: 150px;">
			</select>
			<br/><br/>
			所属班级：
			<select id="classId" style="width: 150px;">
			</select>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="更新"/>
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
		$('#studentClassFileBuildInfo').val("");
		return false;
	});
	$('#cancelExcel').live('click',function(){
		$("#dialogExcel").dialog("close");
		return false;
	});
	$('#cancel').live('click',function(){
		$("#editClassInfoDialog").dialog("close");
		return false;
	});
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/studentClassInfo/export';
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
		var admissionTimeBegin = '';
		if($("#admissionTimeBegin").val() != ''){
			admissionTimeBegin = $("#admissionTimeBegin").val() + " 00:00:00";
		}
		var admissionTimeEnd = '';
		if($("#admissionTimeEnd").val() != ''){
			admissionTimeEnd = $("#admissionTimeEnd").val() + " 23:59:59";
		}
		var data = {
			studentName: $('#studentName').val(),
			isGraduate: $('#graduateStatus').val(),
			admissionTimeBegin: admissionTimeBegin,
			admissionTimeEnd: admissionTimeEnd
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

    <!--一键升级-->
    $('#upGrade').live('click',function(){
        var url = "${contextPath}/studentClassInfo/upStudentClass";
		$.ajax({
			url: url,
			cache:false,
			dataType:'json',
			beforeSend: function() {
				$("#upGrade").attr("disabled",true);
			} ,
			success: function (data) {
				if (data.success) {
					$.messager.alert('系统消息', '升级成功', "info");
					$("#datagrid").datagrid("reload");
				} else {
					$.messager.alert('系统消息', data.msg, "error");
				}
			} ,
			complete: function(){
				$("#upGrade").attr("disabled",false);
			},
			error:function(data){
				$.messager.alert('系统消息', data.msg,'error');
			}
		});
    });

	function settings(value,row){
		var html = '<div style="text-align: center;">';
		if(row.isGraduate == 0){
			html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="编辑" a src="${contextPath}/images/m_edit.gif" href="javascript:;" onclick="editClassInfo('+row.studentId+',\''+row.studentName+'\','+row.gradeId+','+row.classId+')" />';
			html += '<input value="退学" style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;width: 40px;" type="button" onclick="leaveSchool('+row.studentId+')"/>';
		}
		html += '</div>';
		return html;
	}

	function leaveSchool(studentId){
		$.messager.confirm('系统消息', "是否确认退学！", function (r) {
			if (r) {
				var data = {"studentId":studentId};
				$.ajax({
					url: "${contextPath}/studentClassInfo/leaveSchool",
					cache: false,
					data: JSON.stringify(data),
					contentType : 'application/json;charset=utf-8',
					type: "post",
					success: function (data) {
						if (data.success) {
							$.messager.alert('系统消息', '已完成', "info");
							$("#datagrid").datagrid("reload");
						} else {
							$.messager.alert('系统消息', data.msg,'error');
						}
					},
					error: function (data) {
						$.messager.alert('系统消息', data.msg,'error');
					}
				});
			}
		});
	}

	function editClassInfo(studentId, studentName, gradeId, classId){
		$("#editClassInfoDialog").dialog("open");
		$("#studentId").val(studentId);
		$("#studentNameDialog").val(studentName);
		$("#classId").empty();
		$("#gradeId").empty();
		var param={page:1,rows:2000}
		$.ajax({
			url:"${contextPath}/school/gradeList",
			dataType:'json',
			data:param,
			success:function(data){
				var gradeList = data.rows;
				for(i=0;i<gradeList.length;i++){
					var grade = gradeList[i];
					if(gradeId == grade.id){
						$("#gradeId").append('<option selected=\'selected\' value="'+grade.id+'">'+grade.name+'</option>');
					}else{
						$("#gradeId").append('<option value="'+grade.id+'">'+grade.name+'</option>');
					}
				}
			}
		});
		var param={page:1,rows:2000,gradeId:gradeId}
		$.ajax({
			url:"${contextPath}/school/calssList",
			dataType:'json',
			data:param,
			success:function(data){
				var classList = data.rows;
				for(i=0;i<classList.length;i++){
					var classInfo = classList[i];
					if(classId == classInfo.id){
						$("#classId").append('<option selected=\'selected\' value="'+classInfo.id+'">'+classInfo.name+'</option>');
					}else{
						$("#classId").append('<option value="'+classInfo.id+'">'+classInfo.name+'</option>');
					}
				}
			}
		});
		return false;
	}
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
				for(i=0;i<classList.length;i++){
					var classInfo = classList[i];
					$("#classId").append('<option value="'+classInfo.id+'">'+classInfo.name+'</option>');
				}
			}
		});
		return false;
	});
	$('#save').live('click',function(){
		var url = "${contextPath}/studentClassInfo/updateStudentClassInfo";
		var data = {
			studentId: $('#studentId').val(),
			classId: $('#classId').val()
		}
		$.ajax({
			url: url,
			cache: false,
			dataType: 'json',
			data: JSON.stringify(data),
			contentType : 'application/json;charset=utf-8',
			type: "post",
			success: function (data) {
				if (data.success) {
					$.messager.alert('系统消息', '已完成', "info");
					$("#datagrid").datagrid("reload");
				} else {
					$.messager.alert('系统消息', data.msg,'error');
				}
				$("#editClassInfoDialog").dialog("close");
			},
			error: function (data) {
				$("#editClassInfoDialog").dialog("close");
				$.messager.alert('系统消息', data.msg,'error');
			}

		});
	});
</script>
</body>
</html>
