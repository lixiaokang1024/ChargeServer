<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/list" title="学生应缴费信息列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" sortable="true" align="center" width="150px">学号</th>
		<th field="studentName" sortable="true" align="center" width="150px">学生姓名</th>
		<th field="chargeAmount" sortable="true" align="center" width="150px">应缴费金额</th>
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
					<input type="button" class="button add" value="批量导入" id="addExcel" />
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 学生收费信息批量导入 -->
<div id="dialogExcel" class="easyui-dialog" title="学生收费信息批量导入" closed="true"
	 style="width:500px; height:300px;overflow: auto;" iconCls="icon-edit">
	<form name="ExcelForm" action="" id="ExcelForm" method="post" enctype="multipart/form-data">
		<div style="margin:11px 11px 0px 25px">
        <span id="moban">
          <a href="javascript:;" onclick="location.href='${contextPath}/files/学生收费信息导入模板.xlsx'">导入模板下载</a>
        </span><br/><br/>
          <label>选择文件：</label>
          <input name="studentChargeFileBuildInfo" id="studentChargeFileBuildInfo" type="file" class="required"
				 style="width: 200px;"/>
        </span><br/><br/>
			<p align="center">
				<input id="saveExcel" type="button" value="导入Excel"/>
				<input id="cancelExcel" type="button" value="取消"/>
			</p><br>
			<span style="display: none;" id="spanHidden">文件正在上传中.......</span>
		</div>
	</form>
</div>

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
						<h6 id="leftDepositAmount" style="color: red"></h6>
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

	$('#addExcel').live('click',function(){
		$("#dialogExcel").dialog("open");
		$('#studentChargeFileBuildInfo').val("");
		return false;
	});
	$('#cancelExcel').live('click',function(){
		$("#dialogExcel").dialog("close");
		return false;
	});
	$('#cancel').live('click',function(){
		$("#chargeDialog").dialog("close");
		return false;
	});
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/studentChargeInfo/export';
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
			name: $('#studentName').val(),
            gradeId:$("#gradeId option:selected").val(),
            classId:$("#classId option:selected").val()
		};
		return data;
	}

	<!--批量导入-->
	$('#saveExcel').live('click',function(){
		var url = "${contextPath}/studentChargeInfo/importStudentChargeInfo";
		var selNum = $('#studentChargeFileBuildInfo').length;
		var file = $('#studentChargeFileBuildInfo').val();
		var index = file.lastIndexOf(".");
		var ext = file.substring(index + 1, file.length).toLowerCase();
		if(parseInt(selNum)==0 || file=="" || file == null){
			$.messager.alert('系统消息',"请选择Excel文件！！！！","info");
		}
		else if(ext != "xls" && ext != "xlsx"){
			$.messager.alert('系统消息',"请选择Excel文件上传！！！！","info");
		}else{
			$('#ExcelForm').ajaxSubmit({
				url: url,
				cache:false,
				dataType:'json',
				beforeSend: function() {
					$("#spanHidden").show();
					$("#saveExcel").attr("disabled",true);
					$("#cancelExcel").attr("disabled", true);
				} ,
				success: function (data) {
					if (data.success) {
						$.messager.alert('系统消息', '导入成功', "info");
						$('#studentChargeFileBuildInfo').val("");
						$("#datagrid").datagrid("reload");
					} else {
						$.messager.alert('系统消息', data.msg, "error");
					}
					$("#dialogExcel").dialog("close");
				} ,
				complete: function(){
					$("#spanHidden").hide();
					$("#saveExcel").attr("disabled",false);
					$("#cancelExcel").attr("disabled", false);
				},
				error:function(data){
					$.messager.alert('系统消息', data.msg,'error');
				}
			});
		}
	});

	function settings(value, row) {
		var url = '${contextPath}/studentChargeInfo/detail/'+row.studentId;
		var html = '<div style="text-align: center;">';
		html += '<span style="margin-right: 10px;"><a href="javascript:;" ';
		html += 'onclick="addTab(\'学生应缴费详情\',\''+url+'\',true)">';
		html += '<img style="margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" title="查看详情" src="${contextPath}/images/m_view.gif">';
		html += '</a></span>';
		html += '<a style="font-size: 15px;margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="openChargeDialog(\''+row.studentId+'\')">缴费</a>';
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
		var chargeStatus = [0,1];
        $.ajax({
            url:"${contextPath}/studentChargeInfo/getByStudentId",
            dataType:'json',
            data:{"studentId":studentId, "chargeStatus":chargeStatus},
            success:function(data){
                $("#chargeProject").empty();
                var row = data.rows;
                $("#chargeProject").append('');
                $("#leftDepositAmount").html('账户余额：'+row[0].leftDepositAmount);
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

    function doPrint(data) {
    	var printHtml = '<html><head>';
		printHtml += '<title>小票打印</title><style type="text/css">*{padding:0;margin: 0;}span{font-size: 5px;} td{font-size: 10px;}</style></head><body style="background-color:#fff;">';
		printHtml += '<div style="padding: 0px;margin: 0px;width: 300px;">';
        printHtml += '<span style="text-align: center;margin-left: 25px">汇通婴幼儿智力开发园</span><br/>';
		printHtml += '<span style="text-align: center;margin-left: 50px">收费票据</span><br/>';
		printHtml += '<span>学生姓名：'+data[0].studentName+'</span><br/>';
		printHtml += '<span>学号：'+data[0].studentId+'</span><br/>';
		printHtml += '<span>班级：'+data[0].gradeName+data[0].className+'</span><br/>';
		printHtml += '<span>缴费属期：'+data[0].chargeTimeStr+'</span><br/>';
		printHtml += '<span>个人账户余额：'+data[0].leftDepositAmount+'</span><br/>';
		printHtml += '<span><table><tr><td align="center">序号</td><td align="center">项目</td><td align="center">基数</td><td align="center">系数</td><td align="center">小计</td></tr>';
		printHtml += '<tr><td colspan="5">----------------------------------------</td></tr>';
		var shouldCharge = 0;
		var customOfferAmount = 0;
		var useDepositAmount = 0;
		var cash = 0;
		var dz = 0;
        for(i = 0;i<data.length;i++){
            var row = data[i];
			shouldCharge += row.chargeAmount;
			useDepositAmount += row.useDepositAmount;
			customOfferAmount += row.customOfferAmount;
			if(row.payType == 0){
				cash += row.actualChargeAmount;
			}else{
				dz += row.actualChargeAmount;
			}
            var base = row.chargeAmount / row.chargeCoefficient;
        	printHtml += '<tr><td align="center">'+(i+1)+'</td><td align="center">'+row.chargeProjectName+'</td><td align="center">'+base+'</td><td align="center">'+row.chargeCoefficient+'</td><td align="center">'+row.chargeAmount+'</td></tr>';
		}
		printHtml += '<tr><td colspan="5">----------------------------------------</td></tr></table></span>';
		printHtml += '<span>应缴费金额：'+shouldCharge+'</span><br/>';
		printHtml += '<span>优惠金额：'+customOfferAmount+'</span><br/>';
		printHtml += '<span>个人账号扣除金额：'+useDepositAmount+'</span><br/>';
		printHtml += '<span>现金付款：'+cash+'</span><br/>';
		printHtml += '<span>电子支付：'+dz+'</span><br/>';
		printHtml += '<span>========================</span><br/>';
		printHtml += '<span>收费员：${sessionScope.user.userName }</span><br/>';
		printHtml += '<span>缴费时间：'+data[0].actualChargeTimeStr+'</span><br/>';
		printHtml += '<span>收据编号：'+data[0].receiptId+'</span><br/><br/>';
		printHtml += '<span>一式两联  白联存根  红联收据联</span><br/><br/>';
		printHtml += '<span>----------------------------------------</span>';
        printHtml += '</div>';
        printHtml += '</body></html>';
        window.document.body.innerHTML=printHtml;
        window.print();
    }

</script>
</body>
</html>
