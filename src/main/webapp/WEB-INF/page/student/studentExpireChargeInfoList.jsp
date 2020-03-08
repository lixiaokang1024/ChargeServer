<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/expireList" title="学生欠费信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" sortable="true" align="center" width="150px">学号</th>
		<th field="studentName" sortable="true" align="center" width="150px">学生姓名</th>
		<th field="chargeAmount" sortable="true" align="center" width="150px">应缴费金额</th>
		<th field="overDueAmount" sortable="true" align="center" width="150px">欠费金额</th>
		<th field="actualChargeAmount" sortable="true" align="center" width="150px">实际缴费金额</th>
		<th field="prepaymentAmount" sortable="true" align="center" width="150px">预缴费剩余金额</th>
		<th field="deposit" align="center" width="130px" >押金</th>
		<th field="operator" width="300px" align="center" formatter="settings">操作</th>
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

				<td style="text-align: right;">年级：</td>
				<td>
					<select id="gradeId" style="width: 150px;">
					</select>
				</td>

				<td style="text-align: right;">班级：</td>
				<td>
					<select id="classId" style="width: 150px;">
					</select>
				</td>

			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 缴费弹框 -->
<div id="chargeDialog" class="easyui-dialog" title="缴费" closed="true"
	 style="width:400px; height:300px;overflow: auto;" iconCls="icon-edit">
	<form name="chargeForm" action="" id="chargeForm" method="post">
		<div style="margin:20px 5px 5px 35px">
			<input id="studentId" type="hidden" value=""/>
			<input id="studentChargeInfoId" type="hidden" value=""/>
			<span id="chargeProject"></span>
			<br/>
			<table style="border-collapse:separate; border-spacing:0px 5px;">
				<tr>
					<td style="float: right">使用预缴费金额：</td>
					<td>
						<input type="radio" name="useDeposit" checked="checked" value="0"/>不使用
						<input type="radio" name="useDeposit" value="1"/>使用
					</td>
				</tr>
				<tr>
					<td style="float: right">缴费方式：</td>
					<td>
						<select id="payType" style="width: 120px">
							<option value="0">现金</option>
							<option value="1">非现金</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="float: right">
						自定义优惠金额：
					</td>
					<td>
						<input type="text" style="width: 120px;" id="customOfferAmount" value="0.0" placeholder="请输入优惠金额"/>
					</td>
				</tr>
			</table>
			<br/>
			<p align="center">
				<input id="save" type="button" style="font-size: 15px" value="缴费"/>
				<input id="cancel" type="button" style="font-size: 15px" value="取消"/>
			</p>
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

		$(function () {
			$("#gradeId").empty();
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
		});

	});

	$('#gradeId').live('click',function(){
		$("#classId").empty();
		var gradeId = $("#gradeId").val();
		var param={page:1,rows:2000,gradeId:gradeId}
		$.ajax({
			url:"${contextPath}/school/calssList",
			dataType:'json',
			data:param,
			success:function(data){
				var classList = data.rows;
				$("#classId").append('<option value="-1">全部</option>');
				for(i=0;i<classList.length;i++){
					var classInfo = classList[i];
					$("#classId").append('<option value="'+classInfo.id+'">'+classInfo.name+'</option>');
				}
			}
		});
		return false;
	});

	function getFormData() {
		var data = {
			name: $('#studentName').val(),
			gradeId:$("#gradeId option:selected").val(),
			classId:$("#classId option:selected").val()
		};
		return data;
	}

	function settings(value, row) {
		var url = '${contextPath}/studentChargeInfo/expire/detail/'+row.studentId;
		var html = '<div style="text-align: center;">';
		html += '<span style="margin-right: 10px;"><a href="javascript:;" ';
		html += 'onclick="addTab(\'学生应缴费详情\',\''+url+'\',true)">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="查看详情" src="${contextPath}/images/m_view.gif">';
		html += '</a></span>';
		html += '<a style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openChargeDialog(\''+row.studentId+'\')">缴费</a>';
		html += '</div>';
		return html;
	}

	function openChargeDialog(studentId) {
		$("#chargeDialog").dialog("open");
		$("#chargeAmount").val("");
		$("#studentId").val(studentId);
		var discountList = {};
		$.ajax({
			url:"${contextPath}/discount/discountList",
			dataType:'json',
			data:{},
			async:false,
			success:function(data){
				discountList = data.rows;
			}
		});
		var select = '';
		for(k=0;k<discountList.length;k++){
			var row = discountList[k];
			select = select + '<option value="'+(row.discount/10).toFixed(2)+'">'+row.discount+'优惠</option>';
		}
		var chargeStatus = [3];
		$.ajax({
			url:"${contextPath}/studentChargeInfo/getByStudentId",
			dataType:'json',
			data:{"studentId":studentId, "chargeStatus":chargeStatus},
			success:function(data){
				$("#chargeProject").empty();
				var row = data.rows;
				$("#chargeProject").append('');
				for(i=0;i<row.length;i++){
					var rowData = row[i];
					$("#chargeProject").append('<input id="chargeAmount'+rowData.id+'" type="hidden" value="'+rowData.chargeAmount+'" />');
					$("#chargeProject").append('<input id="actualChargeAmount'+rowData.id+'" type="hidden" value="'+rowData.actualChargeAmount+'" />');
					$("#chargeProject").append('<input id="useDepositAmount'+rowData.id+'" type="hidden" value="'+rowData.useDepositAmount+'" />');
					$("#chargeProject").append(rowData.chargeProjectName + '：<input style="width: 80px;" id='+rowData.id+' type="text" value='+(rowData.chargeAmount-rowData.actualChargeAmount-rowData.useDepositAmount-rowData.customOfferAmount)+' />');

					$("#chargeProject").append('<select id="discount'+rowData.id+'"><option value="1">不优惠</option>'+select+'</select>');
					$("#chargeProject").append('应缴日期：'+rowData.chargeTimeStr+'<br/>');
					$("#discount"+rowData.id).bind('change',function () {
						var id = $(this).attr("id").substring(8);
						var discount = $(this).val();
						var chargeAmount = $("#chargeAmount"+id).val();
						var actualChargeAmount = $("#actualChargeAmount"+id).val();
						var useDepositAmount = $("#useDepositAmount"+id).val();
						var discountAmount = chargeAmount*discount - actualChargeAmount - useDepositAmount;
						$("#"+id).val(discountAmount.toFixed(2));
					});
				}
			},
			error:function () {
				alert("error");
			}
		});
		return false;
	}

	$('#save').live('click',function(){
		var url = "${contextPath}/studentChargeInfo/doProjectCharge";
		var projectChargeParamList = new Array();
		$('#chargeProject').find('input:text').each(function (index, element) {
			var discount = $('#discount'+element.id).val();
			projectChargeParamList.push({studentProjectId:element.id,projectAmount:element.value,discount:discount});
		});
		var data = {
			studentId: $('#studentId').val(),
			isUseDeposit: $('input:radio[name="useDeposit"]:checked').val(),
			projectChargeParamList:projectChargeParamList,
			payType:$("#payType").val(),
			customOfferAmount:$("#customOfferAmount").val()
		}
		$.messager.confirm('系统消息', "确认缴费！", function (r) {
			if (r) {
				$.ajax({
					url: url,
					cache: false,
					dataType: 'json',
					data: JSON.stringify(data),
					contentType : 'application/json;charset=utf-8',
					type: "post",
					success: function (data) {
						if (data.success) {
							$.messager.confirm('系统消息', "是否打印！", function (r) {
								if(r){
									doPrint(data.data);
								}
							});
							$.messager.alert('系统消息', '已完成', "info");
							$("#datagrid").datagrid("reload");
						} else {
							$.messager.alert('系统消息', data.msg,'error');
						}
						$("#chargeDialog").dialog("close");
					},
					error: function (data) {
						$("#chargeDialog").dialog("close");
						$.messager.alert('系统消息', data.msg,'error');
					}

				});
			}
		});
	});

	$('#cancel').live('click',function(){
		$("#chargeDialog").dialog("close");
		return false;
	});
	function doPrint(data) {
		var printHtml = '<div style="margin:11px 11px 0px 25px">';
		for(i = 0;i<data.length;i++){
			var row = data[i];
			var amount = row.actualChargeAmount+row.useDepositAmount;
			printHtml += '<span>缴费项目：'+row.chargeProjectName+'</span><br/><br/>';
			printHtml += '<span>缴费金额：'+amount+'</span><br/><br/>';
		}
		printHtml += '<span>学生姓名：'+data[0].studentName+'</span><br/><br/>';
		printHtml += '<span>缴费日期：'+data[0].actualChargeTimeStr+'</span><br/><br/>';
		printHtml += '</div>';
		window.document.body.innerHTML=printHtml;
		window.print();
	}

</script>
</body>
</html>
