<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/discount/discountList" title="折扣列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="discount" sortable="true" align="center" width="150px">折扣</th>
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

<!-- 新增折扣 -->
<div id="addDialog" class="easyui-dialog" title="新增折扣" closed="true"
	 style="width:300px; height:170px;overflow: auto;" iconCls="icon-edit">
	<form name="addDiscountForm" action="" id="addDiscountForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			折扣：
			<input id="discount" type="text" placeholder="输入1-10之间数字，保留两位小数" style="width: 200px;"/>
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

	$('#addLink').live('click',function(){
		$("#discount").val("");
		$("#addDialog").dialog("open");
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
		html += "<img style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' title='删除' a src='${contextPath}/images/m_delete.gif' href='javascript:;' onclick='deleteDiscount("+row.id+")' />";
		html += '</div>';
		return html;
	}

	//删除
	function deleteDiscount(discountId){
		$.messager.confirm('系统消息', "确认删除！", function (r) {
			if (r) {
				$.ajax({
					url:  '${contextPath}/discount/deleteDiscount',
					cache: false,
					dataType: 'json',
					data: {discountId:discountId},
					type: "post",
					success: function (json) {
						$.messager.alert('系统消息', '已完成', "info");
						if (json.success) {
							$("#datagrid").datagrid("reload");
						}
					},
				});
			}
		});
	}

	$('#save').live('click',function(){
		var url = "${contextPath}/discount/saveDiscount";
		var data = {
			discount: $('#discount').val()
		}
		$('#addDiscountForm').ajaxSubmit({
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
