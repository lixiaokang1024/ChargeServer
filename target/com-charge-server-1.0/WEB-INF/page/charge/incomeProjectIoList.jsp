<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/charge/incomeProjectIolist" title="收入项目列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="payProjectName" sortable="true" width="150px">收入项目</th>
		<th field="payAmount" sortable="true" width="150px">收入金额</th>
		<th field="payTimeStr" sortable="true" width="150px">收入时间</th>
		<th field="remark" sortable="true" width="350px">备注</th>
		<th field="operator" width="50px">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">支出项目：</td>
				<td style="width: 150px">
					<input type="text" id="projectName" name="projectName" style="width: 110px;" value=""/>
				</td>
				<td style="text-align: right;">支出时间：</td>
				<td colspan="3">
					<input id="payTimeBegin" style="width: 125px" name="payTimeBegin" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'payTimeEnd\')}'})"
						   value=""/>&nbsp;-&nbsp;
					<input id="payTimeEnd" style="width: 125px" name="payTimeEnd" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'payTimeBegin\')}'})"
						   value=""/>
				</td>
			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="3">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button add" value="添加" id="addLink" />
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 添加收入项目 -->
<div id="addDialog" class="easyui-dialog" title="添加收入项目" closed="true"
	 style="width:350px; height:250px;overflow: auto;" iconCls="icon-edit">
	<form name="addForm" action="" id="addForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			收入项目：
			<select id="payProjectId" style="width: 150px;">
			</select>
			<br/><br/>
			收入金额：
			<input name="payAmount" id="payAmount" type="text" style="width: 150px;"/>
			<br/><br/>
			特别备注：
			<input name="addRemark" id="addRemark" type="text" style="width: 150px;"/>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="添加"/>
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

	function getFormData() {
		var data = {
			projectName: $('#projectName').val(),
			payTimeBegin: $('#payTimeBegin').val(),
			payTimeEnd: $('#payTimeEnd').val()
		};
		return data;
	}

	$('#addLink').live('click',function(){
		$("#addDialog").dialog("open");
		$('#payAmount').val("");
		$('#addRemark').val("");
		$("#payProjectId").empty();
		var param={page:1,rows:2000}
		$.ajax({
			url:"${contextPath}/charge/incomeProjectlist",
			dataType:'json',
			data:param,
			success:function(data){
				var payProjectList = data.rows;
				$("#payProjectId").append('<option value="-1">全部</option>');
				for(i=0;i<payProjectList.length;i++){
					var payProject = payProjectList[i];
					$("#payProjectId").append('<option value="'+payProject.id+'">'+payProject.projectName+'</option>');
				}
			}
		});
		return false;
	});
	$('#cancel').live('click',function(){
		$("#addDialog").dialog("close");
		return false;
	});

	$('#save').live('click',function(){
		var url = "${contextPath}/charge/savePayProjectIo";
		var data = {
			payProjectId: $('#payProjectId').val(),
			payAmount: $('#payAmount').val(),
			remark: $('#addRemark').val()
		}
		$('#addForm').ajaxSubmit({
			url: url,
			cache:false,
			dataType:'json',
			data:data,
			beforeSend: function() {
				$("#save").attr("disabled",true);
				$("#cancel").attr("disabled", true);
			} ,
			success: function (data) {
				$("#addDialog").dialog("close");
				if (data.success) {
					$.messager.alert('系统消息', '已完成', "info");
					$("#datagrid").datagrid("reload");
				} else {
					layer.alert(data.msg);
				}
			} ,
			complete: function(){
				$("#save").attr("disabled",false);
				$("#cancel").attr("disabled", false);
			},
			error:function(data){
				$.messager.alert('系统消息', data.msg,'error');
			}
		});
	});

</script>
</body>
</html>
