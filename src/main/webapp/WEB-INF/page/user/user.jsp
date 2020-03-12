<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/user/userList" title="用户列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="userName" sortable="true" align="center" width="150px">用户名</th>
		<th field="operator" align="center" width="150px" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">用户名：</td>
				<td style="width: 150px">
					<input type="text" id="name" name="name" style="width: 110px;" value=""/>
				</td>
			</tr>
			<tr>&nbsp;</tr>
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
<div id="menuDialog" class="easyui-dialog" title="菜单配置" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="menuForm" action="" id="menuForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input type="hidden" id="userId"/>
			角色分配：
			<div id=roleSet></div>
			<br/><br/>
			<p align="center">
				<input id="menuSave" type="button" value="提交"/>
				<input id="menuCancel" type="button" value="取消"/>
			</p><br>
		</div>
	</form>
</div>

<!-- 新增用户 -->
<div id="addDialog" class="easyui-dialog" title="新增用户" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addUserForm" action="" id="addUserForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input type="hidden" id="uid"/>
			用户名：
			<input id="userName" type="text" style="width: 150px;"/>
			<br/><br/>
			密码：
			<input id="password" type="password" style="width: 150px;"/>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="保存"/>
				<input id="cancel" type="button" value="取消"/>
			</p><br>
		</div>
	</form>
</div>


<script type="text/javascript">

	$('#addLink').live('click',function(){
		$("#userName").val("");
		$("#password").val("");
		$("#uid").val("");
		$("#addDialog").dialog("open");
		return false;
	});
	function editAddDialog(row) {
		$("#password").val(row.password);
		$("#userName").val(row.userName);
		$("#uid").val(row.id);
		$("#addDialog").dialog("open");
	}
	$(function() {
		/*搜索*/
		$(function(){
			$('#searchLink').click(function(){
				var data = getFormData();
				$('#datagrid').datagrid('load', data);
			});
		});

	});
	$('#menuCancel').live('click',function(){
		$("#menuDialog").dialog("close");
		return false;
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
        html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openMenuDialog(\''+row.id+'\')">角色分配</a>';
		html += '</div>';
		return html;
	}

    function openMenuDialog(userId) {
		$("#userId").val(userId);
        $("#menuDialog").dialog("open");
		$("#roleSet").empty();
		$.ajax({
			url:"${contextPath}/user/userRoleList",
			dataType:'json',
			data:{userId:userId},
			success:function(data){
				var roleList = data.rows;
				var userRoleIds = data.roleIds;
				for(i=0;i<roleList.length;i++){
					var role = roleList[i];
					if($.inArray(role.id, userRoleIds) == -1){
						$("#roleSet").append('<li><input type="checkbox" value="'+role.id+'" id="'+role.id+'" name="chkbox">'+role.name+'</li>');
					}else{
						$("#roleSet").append('<li><input type="checkbox" checked="checked" value="'+role.id+'" id="'+role.id+'" name="chkbox">'+role.name+'</li>');
					}
					$("#roleSet").append('<ul id="tree">');
				}
				$("#roleSet").append('</ul>');
			}
		});
        return false;
    }

	$('#menuSave').live('click',function(){
		var url = "${contextPath}/user/saveUserRole";
		var roleIds=new Array();
		$("input[name=chkbox]").each(function (i,d){
			if(d.checked) {
				roleIds.push(d.value);
			}
		})
		var data = {
			userId: $('#userId').val(),
			roleIds: roleIds
		}
		$.ajax({
			url: url,
			cache:false,
			dataType:'json',
			data:data,
			success: function (data) {
				$("#menuDialog").dialog("close");
				if (data.success) {
					$.messager.alert('系统消息', '已完成', "info");
					$("#datagrid").datagrid("reload");
				} else {
					$.messager.alert('系统消息', data.msg,'error');
				}
			} ,
			error:function(data){
				$.messager.alert('系统消息', data.msg,'error');
			}
		});
	});

	$('#save').live('click',function(){
		var url = "${contextPath}/user/saveUser";
		var data = {
			userId: $('#uid').val(),
			userName: $('#userName').val(),
			password: $('#password').val()
		}
		$('#addUserForm').ajaxSubmit({
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
