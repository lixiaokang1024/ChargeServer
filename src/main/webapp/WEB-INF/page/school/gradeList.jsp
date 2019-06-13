<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/school/gradeList" title="年级列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="name" sortable="true" width="150px">年级名称</th>
		<th field="level" sortable="true" width="150px">年级等级</th>
		<th field="operator" width="50px" formatter="settings">操作</th>
	</tr>
	</thead>
</table>

<!--搜索条件开始-->
<div id="tb" style="padding: 5px; height: auto;">
	<form id="advanced_search" method="post">
		<table>
			<tr>
				<td style="text-align: right;">年级姓名：</td>
				<td style="width: 150px">
					<input type="text" id="gradeName" name="gradeName" style="width: 110px;" value=""/>
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
<!--搜索条件结束-->

<!-- 添加年级 -->
<div id="addGradeDialog" class="easyui-dialog" title="添加班级" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="addForm" action="" id="addForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="gradeId" type="hidden" value=""/>
			年纪名称：
			<input name="addGradeName" id="addGradeName" type="text" style="width: 150px;"/>
			<br/><br/>
			年纪排行：
			<select id="level">
				<option value="1">一年级</option>
				<option value="2">二年级</option>
				<option value="3">三年级</option>
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
			gradeName: $('#gradeName').val()
		};
		return data;
	}
    $('#addLink').live('click',function(){
		$("#addGradeName").val("");
        $("#addGradeDialog").dialog("open");
    	return false;
    });

    $('#cancel').live('click',function(){
        $("#addGradeDialog").dialog("close");
        return false;
    });

    $('#save').live('click',function(){
        var url = "${contextPath}/school/saveGradeInfo";
        var data = {
        	id: $('#gradeId').val(),
            name: $('#addGradeName').val(),
            level: $('#level').val()
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
                $("#addGradeDialog").dialog("close");
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
		var gradeId = row.id;
		var html = '<div style="text-align: center;">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="编辑" a src="${contextPath}/images/m_edit.gif" href="javascript:;" onclick="modifyGradeInfo(\''+row.name+'\',\''+gradeId+'\','+row.level+')" />';
		html += '</div>';
		return html;
	}

	function modifyGradeInfo(gradeName, gradeId, level){
		$("#addGradeDialog").dialog("open");
		$("#addGradeName").val(gradeName);
		$("#gradeId").val(gradeId);
		$("#save").val("更新");
		$("#level").find("option[value=\'"+level+"\']").attr("selected",true);
		return false;
	}
</script>
</body>
</html>
