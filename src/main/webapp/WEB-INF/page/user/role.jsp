<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/user/roleList" title="角色列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="name" sortable="true" align="center" width="150px">角色名称</th>
		<th field="operator" align="center" width="150px" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">角色名称：</td>
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
	 style="width:350px; height:250px;overflow: auto;" iconCls="icon-edit">
	<form name="menuForm" action="" id="menuForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="menuRoleId" type="hidden" />
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

<!-- 新增角色 -->
<div id="addDialog" class="easyui-dialog" title="新增角色" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addRoleForm" action="" id="addRoleForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="roleId" type="hidden" value="" />
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
		html += "<img style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' title='编辑' a src='${contextPath}/images/m_edit.gif' href='javascript:;' onclick='editAddDialog("+JSON.stringify(row)+")' />";
        html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openMenuDialog(\''+row.id+'\')">资源配置</a>';
		html += '</div>';
		return html;
	}

	function editAddDialog(row) {
		$("#roleId").val(row.id);
		$("#roleName").val(row.name);
		$("#addDialog").dialog("open");
	}

	$('#addLink').live('click',function(){
		$("#roleId").val("");
		$("#roleName").val("");
		$("#addDialog").dialog("open");
		return false;
	});

    function openMenuDialog(roleId) {
        $("#menuDialog").dialog("open");
		$("#menuSet").empty();
		$("#menuRoleId").val(roleId);
		var param={roleId:roleId}
		$.ajax({
			url:"${contextPath}/user/roleMenuList",
			dataType:'json',
			data:param,
			success:function(data){
				var menuList = data.rows;
				var roleMenus = data.roleMenus;
				for(i=0;i<menuList.length;i++){
					var menu = menuList[i];
					var menuId = menu.id;
					$("#menuSet").append('<ul id="tree">');
					if($.inArray(menuId, roleMenus) == -1){
						$("#menuSet").append('<li><input type="checkbox" id="'+menu.id+'" value="'+menu.id+'" name="chkbox">'+menu.menuName+'</li>');
					}else{
						$("#menuSet").append('<li><input type="checkbox" checked="checked" id="'+menu.id+'" value="'+menu.id+'" name="chkbox">'+menu.menuName+'</li>');
					}
				}
				$("#menuSet").append('</ul>');
			}
		});
        return false;
    }

	$('#save').live('click',function(){
		var url = "${contextPath}/user/saveRole";
		var data = {
			name: $('#roleName').val()
		}
		$('#addRoleForm').ajaxSubmit({
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

	$('#menuSave').live('click',function(){
		var url = "${contextPath}/user/saveRoleResource";
		var resourceIds=new Array();
		$("input[name=chkbox]").each(function (i,d){
			if(d.checked) {
				resourceIds.push(d.value);
			}
		})
		var data = {
			roleId: $("#menuRoleId").val(),
			resourceIds: resourceIds
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
</script>
</body>
</html>
