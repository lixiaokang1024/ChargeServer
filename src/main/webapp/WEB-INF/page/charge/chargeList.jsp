<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/charge/list" title="收费项目列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="projectName" sortable="true" width="150px">收费项目</th>
		<th field="amount" sortable="true" width="150px">收费金额</th>
		<th field="gradeName" sortable="true" width="150px">所属年级</th>
		<th field="operator"  width="50px">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">收费项目：</td>
				<td style="width: 150px">
					<input type="text" id="projectName" name="projectName" style="width: 110px;" value=""/>
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

<!-- 添加费用项目 -->
<div id="addDialog" class="easyui-dialog" title="添加费用项目" closed="true"
	 style="width:350px; height:250px;overflow: auto;" iconCls="icon-edit">
	<form name="addForm" action="" id="addForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			收费项目：
			<input name="name" id="name" type="text" style="width: 150px;"/>
			<br/><br/>
			收费金额：
			<input name="amount" id="amount" type="text" style="width: 150px;"/>
			<br/><br/>
			所属年级：
			<select id="gradeId" style="width: 150px;">
				<option value="-1">全部</option>
			</select>
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
			projectName: $('#projectName').val()
		};
		return data;
	}

	$('#addLink').live('click',function(){
		$("#addDialog").dialog("open");
		return false;
	});
	$('#cancel').live('click',function(){
		$("#addDialog").dialog("close");
		return false;
	});

	$('#save').live('click',function(){
		var url = "${contextPath}/charge/saveProject";
		var data = {
			projectName: $('#name').val(),
			amount: $('#amount').val(),
			gradeId: $('#gradeId').val()
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
					$.messager.alert('系统消息', '导入成功', "info");
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
