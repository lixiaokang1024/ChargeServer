<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/school/calssList" title="班级列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="name" sortable="true" width="150px">班级名称</th>
		<th field="gradeName" sortable="true" width="150px">所属年级</th>
		<th field="settings" width="50px" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">班级名称：</td>
				<td style="width: 150px">
					<input type="text" id="className" name="className" style="width: 110px;" value=""/>
				</td>
			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="2">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button add" value="添加" id="addLink" />
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 添加班级 -->
<div id="addClassDialog" class="easyui-dialog" title="添加班级" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addForm" action="" id="addForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="classId" type="hidden" value=""/>
			班级名称：
			<input name="addClassName" id="addClassName" type="text" style="width: 150px;"/>
			<br/><br/>
			所属年级：
			<select id="gradeId" style="width: 150px;">
			</select>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="添加"/>
				<input id="cancel" type="button" value="取消"/>
			</p><br>
		</div>
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
	});
	function getFormData() {
		var data = {
			className: $('#className').val(),
		};
		return data;
	}
    $('#addLink').live('click',function(){
        $("#addClassDialog").dialog("open");
		$("#addClassName").val("");
        $("#gradeId").empty();
		$("#save").val("添加");
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
        return false;
    });

    $('#cancel').live('click',function(){
        $("#addClassDialog").dialog("close");
        return false;
    });

    $('#save').live('click',function(){
        var url = "${contextPath}/school/saveClassInfo";
        var data = {
        	id: $('#classId').val(),
            name: $('#addClassName').val(),
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
                $("#addClassDialog").dialog("close");
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

	function settings(value,row){
		var classId = row.id;
		var html = '<div style="text-align: center;">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="编辑" a src="${contextPath}/images/m_edit.gif" href="javascript:;" onclick="modifyClassInfo(\''+row.name+'\',\''+classId+'\',\''+row.gradeId+'\')" />';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="删除" a src="${contextPath}/images/m_delete.gif" href="javascript:;" onclick="deleteClassInfo(\''+classId+'\')" />';
		html += '</div>';
		return html;
	}
	function modifyClassInfo(className, classId, gradeId){
		$("#addClassDialog").dialog("open");
		$("#gradeId").empty();
		$("#addClassName").val(className);
		$("#classId").val(classId);
		$("#save").val("更新");
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
					if(gradeId == grade.id){
						$("#gradeId").append('<option selected=\'selected\' value="'+grade.id+'">'+grade.name+'</option>');
					}else{
						$("#gradeId").append('<option value="'+grade.id+'">'+grade.name+'</option>');
					}
				}
			}
		});
		return false;
	}

	//删除
	function deleteClassInfo(classId){
		$.messager.confirm('系统消息', "确认删除！", function (r) {
			if (r) {
				$.ajax({
					url:  '${contextPath}/school/deleteClassInfo',
					cache: false,
					dataType: 'json',
					data: {classId:classId},
					type: "post",
					success: function (json) {
						$.messager.alert('系统消息', json.msg,'error');
						if (json.success) {
							$("#datagrid").datagrid("reload");
						}
					},
				});
			}
		});
	}
</script>
</body>
</html>
