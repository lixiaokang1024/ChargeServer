<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/user/userList" title="用户列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="name" sortable="true" align="center" width="150px">用户名</th>
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
			菜单配置：
			<div id=menuSet></div>
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
	 style="width:550px; height:350px;overflow: auto;" iconCls="icon-edit">
	<form name="addUserForm" action="" id="addUserForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			角色名称：
			<input id="roleName" type="text" style="width: 150px;"/>
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
		html += "<img style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' title='编辑' a src='${contextPath}/images/m_edit.gif' href='javascript:;' onclick='openMenuDialog("+JSON.stringify(row)+")' />";
        // html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openMenuDialog(\''+row.id+'\')">资源配置</a>';
		html += '</div>';
		return html;
	}

    function openMenuDialog(roleId) {
        $("#menuDialog").dialog("open");
		var param={roleId:roleId}
		$.ajax({
			url:"${contextPath}/user/menuList",
			dataType:'json',
			data:param,
			success:function(data){
				var menuList = data.rows;
				for(i=0;i<menuList.length;i++){
					var menu = menuList[i];
					$("#menuSet").append('<ul id="tree">');
					$("#menuSet").append('<li><input type="checkbox" id="'+menu.id+'" name="chkbox"><label for="'+menu.id+'"><span class="folder">'+menu.name+'</span></label></li>');
				}
				$("#menuSet").append('</ul>');
			}
		});
        return false;
    }

	$('#save').live('click',function(){
		var url = "${contextPath}/user/saveUser";
		var data = {
			name: $('#roleName').val()
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
