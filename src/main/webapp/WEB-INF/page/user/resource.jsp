<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/user/menuList" title="资源列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="menuKey" sortable="true" align="center" width="150px">资源路径</th>
		<th field="menuName" sortable="true" align="center" width="150px">资源名称</th>
		<th field="operator" align="center" width="150px" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">资源名称：</td>
				<td style="width: 150px">
					<input type="text" id="name" name="name" style="width: 110px;" value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button add" value="添加" id="addLink" />
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 新增资源 -->
<div id="addDialog" class="easyui-dialog" title="新增资源" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addResourceForm" action="" id="addResourceForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input type="hidden" id="menuId"/>
			资源路径
			<input id="menuKey" type="text" value="" />
			<br/><br/>
			资源名称：
			<input id="menuName" type="text" style="width: 150px;"/>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="保存"/>
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
    $('#cancel').live('click',function(){
        $("#addDialog").dialog("close");
        return false;
    });

	function getFormData() {
		var data = {
			name: $('#name').val()
		};
		return data;
	}

	function settings(value,row){
		var html = '<div style="text-align: center;">';
		html += "<img style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' title='编辑' a src='${contextPath}/images/m_edit.gif' href='javascript:;' onclick='editAddDialog("+JSON.stringify(row)+")' />";
		html += '</div>';
		return html;
	}

	function editAddDialog(row) {
		$("#menuId").val(row.id);
		$("#menuKey").val(row.menuKey);
		$("#menuName").val(row.menuName);
		$("#addDialog").dialog("open");
	}

	$('#addLink').live('click',function(){
		$("#menuId").val("");
		$("#menuKey").val("");
		$("#menuName").val("");
		$("#addDialog").dialog("open");
		return false;
	});

	$('#save').live('click',function(){
		var url = "${contextPath}/user/saveResource";
		var data = {
			menuId: $('#menuId').val(),
			menuName: $('#menuName').val(),
			menuKey: $('#menuKey').val()
		}
		$('#addResourceForm').ajaxSubmit({
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
					$.messager.alert('系统消息', data.msg,'error');
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
