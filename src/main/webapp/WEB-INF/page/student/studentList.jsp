<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/student/list" title="学生列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="name" sortable="true" width="150px">姓名</th>
		<th field="sex" sortable="true" width="150px">性别</th>
		<th field="parentName" sortable="true" width="130px">父母姓名</th>
		<th field="relation" width="80px" >与学生关系</th>
		<th field="operator"  width="50px">操作</th>
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
				<td style="text-align: right;">入学时间：</td>
				<td colspan="3">
					<input id="createTimeBegin" style="width: 125px" name="createTimeBegin" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})"
						   value=""/>&nbsp;-&nbsp;
					<input id="createTimeEnd" style="width: 125px" name="createTimeEnd" class="Wdate"
						   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'createTimeBegin\')}'})"
						   value=""/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">分公司：</td>
				<td><select id="branchId" name="branchId" style="width:110px;"></select></td>
				<td style="text-align: right;">门店名称：</td>
				<td>
					<input type="text" id="storeName" name="storeName" style="width: 110px;" value=""/>
					<input type="hidden" id="storeCode" name="storeCode" style="width: 110px;" value=""/>
				</td>
				<td style="text-align: right;">门店类型：</td>
				<td>
					<select style="width:130px" name="storeType" id="storeType">
						<option value="-1">请选择</option>
						<option value="1">自营店</option>
						<option value="4">委托店</option>
					</select>
				</td>
				<td style="text-align: right;">是否赠品：</td>
				<td>
					<select style="width:90px" name="isGift" id="isGift">
						<option value="-1">全部</option>
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button export" value="批量导出" id="exportLink"/>
				</td>
			</tr>
		</table>
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
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/purchasedmanagement/replenishmentOrderController/export';
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
		var data = {
			replenishmentSn: $('#replenishmentSn').val(),
			purchaseCode: $('#purchaseCode').val(),
			storeCode: $('#storeCode').val(),
			storeType: $('#storeType').val(),
			branchId: $("#branchId").val(),
			createTimeBegin: $('#createTimeBegin').val(),
			createTimeEnd: $('#createTimeEnd').val(),
			isGift: $('#isGift').val()
		};
		return data;
	}

</script>
</body>
</html>
