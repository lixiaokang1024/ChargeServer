<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/student/list" title="学生列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="id" sortable="true" align="center" width="50px">学号</th>
		<th field="name" sortable="true" align="center" width="80px">姓名</th>
		<th field="sexStr" sortable="true" align="center" width="50px">性别</th>
		<th field="graduateStr" align="center" sortable="true" width="70px">毕业状态</th>
		<th field="deposit" align="center" sortable="true" width="50px">押金</th>
		<th field="prepaymentAmount" align="center" sortable="true" width="70px">预交费金额</th>
		<th field="idCardType" sortable="true" align="center" width="50px">身份证类型</th>
		<th field="idCardNumber" sortable="true" align="center" width="150px">身份证号码</th>
		<th field="bornDate" sortable="true" align="center" width="80px">出生日期</th>
		<th field="country" sortable="true" align="center" width="80px">国籍</th>
		<th field="nation" sortable="true" align="center" width="80px">民族</th>
		<th field="oversea" sortable="true" align="center" width="80px">港澳台侨外</th>
		<th field="bornPlace" sortable="true" align="center" width="80px">出生所在地</th>
		<th field="nativePlace" sortable="true" align="center" width="80px">籍贯</th>
		<th field="accountCharacter" sortable="true" align="center" width="80px">户口性质</th>
		<th field="nonAgriculturalAccountType" sortable="true" align="center" width="80px">非农业户口类型</th>
		<th field="registeredResidence" sortable="true" align="center" width="80px">户口所在地</th>
		<th field="parentName" sortable="true" align="center" width="80px">监护人姓名</th>
		<th field="parentIdCardType" sortable="true" align="center" width="50px">监护人身份证件类型</th>
		<th field="parentIdCardNumber" sortable="true" align="center" width="150px">监护人身份证号码</th>
		<th field="relation" sortable="true" align="center" width="80px">监护人关系</th>
		<th field="mobile" align="center" width="100px" >联系方式</th>
		<th field="address" align="center" width="250px" >地址</th>
		<th field="operator" align="center" width="150px" formatter="settings">操作</th>
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
				<td style="text-align: right;">毕业状态：</td>
				<td style="width: 150px">
					<select id="graduateStatus">
						<option value="0">未毕业</option>
						<option value="1">已毕业</option>
						<option value="2">已退学</option>
						<option value="-1">全部</option>
					</select>
				</td>
				<td style="text-align: right;">手机号：</td>
				<td style="width: 150px">
					<input type="text" id="mobile" name="mobile" style="width: 110px;" value=""/>
				</td>
			</tr>
			<tr>&nbsp;</tr>
			<tr>
				<td colspan="4">
					<input type="button" class="button search" value="搜索" id="searchLink"/>
					<input type="reset" class="button clear" value="清空" id="clearLink"/>
					<input type="button" class="button add" value="批量导入" id="addExcel" />
					<input type="button" class="button export" value="批量导出" id="exportLink"/>
					<input type="button" class="button add" value="添加" onclick="addLink()" />
				</td>
			</tr>
		</table>
	</form>
</div>
<!--搜索条件结束-->

<!-- 学生信息批量导入 -->
<div id="dialogExcel" class="easyui-dialog" title="学生信息批量导入" closed="true"
	 style="width:500px; height:300px;overflow: auto;" iconCls="icon-edit">
	<form name="ExcelForm" action="" id="ExcelForm" method="post" enctype="multipart/form-data">
		<div style="margin:11px 11px 0px 25px">
        <span id="moban">
          <a href="javascript:;" onclick="location.href='${contextPath}/files/学生基础信息导入模板.xlsx'">导入模板下载</a>
        </span><br/><br/>
          <label>选择文件：</label>
          <input name="studentFileBuildInfo" id="studentFileBuildInfo" type="file" class="required"
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

<div id="chargeDialog" class="easyui-dialog" title="预缴费" closed="true"
	 style="width:350px; height:200px;overflow: auto;" iconCls="icon-edit">
	<form name="chargeForm" action="" id="chargeForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="chargeStudentId" type="hidden" value=""/>
			<input id="chargeStudentName" type="hidden" value=""/>
			<input id="deposit" type="hidden" value=""/>
			<input id="prepaymentAmount" type="hidden" value=""/>
			当前金额：
			<input name="" id="currAmount" type="text" disabled="disabled" style="width: 150px;"/>
			<br/>
			缴费金额：
			<input name="chargeAmount" id="chargeAmount" type="text" style="width: 150px;"/>
			<br/>
			预缴类型：
			<select style="width: 150px" id="chargeType">
				<option value="1">预交费</option>
				<option value="2">押金</option>
			</select>
			<br/>
			缴费方式：
			<select style="width: 150px" id="chargeMethod">
				<option value="1">现金</option>
				<option value="2">非现金</option>
			</select>
			<br/>
			<p align="center">
				<input id="chargeSave" type="button" value="预缴费"/>
				<input id="chargeCancel" type="button" value="取消"/>
			</p>
		</div>
	</form>
</div>

<!-- 修改学生信息 -->
<div id="addDialog" class="easyui-dialog" title="更新学生信息" closed="true"
	 style="width:550px; height:350px;overflow: auto;" iconCls="icon-edit">
	<form name="updateStudentInfoForm" action="" id="updateStudentInfoForm" method="post">
		<div style="margin:11px 11px 0px 25px">
			<input id="studentId" type="hidden" value=""/>
			<div style="width: 130px;float: left">
				学生姓名：
			</div>
			<input id="updateStudentName" type="text" style="width: 150px;"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				学生性别：
			</div>
			<select id="updateSex" style="width: 150px;float: left">
				<option value="0">男</option>
				<option value="1">女</option>
			</select>
			<br/><br/>
			<div style="width: 130px;float: left">
				身份证类型：
			</div>
			<select id="idCardType" style="width: 150px;float: left">
				<option value="身份证">身份证</option>
			</select>
			<br/><br/>
			<div style="width: 130px;float: left">
				身份证号码：
			</div>
			<input id="idCardNumber" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				国籍：
			</div>
			<input id="country" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				民族：
			</div>
			<input id="nation" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				港澳台侨外：
			</div>
			<input id="oversea" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				出生地：
			</div>
			<input id="bornPlace" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				籍贯：
			</div>
			<input id="nativePlace" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				户口性质：
			</div>
			<input id="accountCharacter" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				非农业户口类型：
			</div>
			<input id="nonAgriculturalAccountType" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				户口所在地：
			</div>
			<input id="registeredResidence" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				监护人姓名：
			</div>
			<input id="parentName" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				监护人身份证类型：
			</div>
			<input id="parentIdCardType" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				监护人身份证号：
			</div>
			<input id="parentIdCardNumber" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				监护人关系：
			</div>
			<input id="relation" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				出生日期：
			</div>
			<form name="form1" id="dateForm" style="float: left">
				<select id="year"></select>年
				<select id="month"></select>月
				<select id="day"></select>日
			</form>
			<br/><br/>
			<div style="width: 130px;float: left">
				联系方式：
			</div>
			<input id="updateMobile" type="text" style="width: 150px;float: left"/>
			<br/><br/>
			<div style="width: 130px;float: left">
				家庭地址：
			</div>
			<input id="updateAddress" type="text" style="width: 250px;float: left"/>
			<br/><br/>
			<p align="center">
				<input id="save" type="button" value="更新"/>
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
	$('#addExcel').live('click',function(){
		$("#dialogExcel").dialog("open");
		$('#studentFileBuildInfo').val("");
		return false;
	});
	$('#cancelExcel').live('click',function(){
		$("#dialogExcel").dialog("close");
		return false;
	});
    $('#chargeCancel').live('click',function(){
        $("#chargeDialog").dialog("close");
        return false;
    });
	//导出
	$('#exportLink').click(function () {
		var datas = getFormData();
		var url = '${contextPath}/student/export';
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
            mobile: $('#mobile').val(),
			graduate: $('#graduateStatus').val()
		};
		return data;
	}

	<!--批量导入-->
	$('#saveExcel').live('click',function(){
		var url = "${contextPath}/student/importStudentInfo";
		var selNum = $('#studentFileBuildInfo').length;
		var file = $('#studentFileBuildInfo').val();
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
						$('#studentFileBuildInfo').val("");
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

	function settings(value,row){
		var html = '<div style="text-align: center;">';
		html += "<img style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' title='编辑' a src='${contextPath}/images/m_edit.gif' href='javascript:;' onclick='modifyStudentInfo("+JSON.stringify(row)+")' />";
        html += "<a style='margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;' onclick='openChargeDialog("+JSON.stringify(row)+")'>预缴费</a>";
		html += '</div>';
		return html;
	}

    function openChargeDialog(row) {
		$("#chargeAmount").val(0);
        $("#chargeDialog").dialog("open");
        if($("#chargeType").val()==1){
			$("#currAmount").val(row.prepaymentAmount);
		}else{
			$("#currAmount").val(row.deposit);
		}
        $("#prepaymentAmount").val(row.prepaymentAmount);
		$("#deposit").val(row.deposit);
        $("#chargeStudentId").val(row.id);
		$("#chargeStudentName").val(row.name);
        return false;
    }

	$('#chargeType').change(function(){
		if($("#chargeType").val()==1){
			$("#currAmount").val($("#prepaymentAmount").val());
		}else{
			$("#currAmount").val($("#deposit").val());
		}
	});

    $('#chargeSave').live('click',function(){
		url = "${contextPath}/studentChargeInfo/doDepositCharge";
        var data = {
            studentId: $('#chargeStudentId').val(),
            chargeAmount: $('#chargeAmount').val(),
			chargeType: $('#chargeType').val()
        }
        $.messager.confirm('系统消息', "确认缴费！", function (r) {
            if (r) {
                $.ajax({
                    url: url,
                    cache: false,
                    dataType: 'json',
                    data: data,
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

	function modifyStudentInfo(row){
		$("#addDialog").dialog("open");
		$('#studentId').val(row.id);
		$('#updateStudentName').val(row.name);
		$('#updateMobile').val(row.mobile);
		$('#updateAddress').val(row.address);
		$('#idCardNumber').val(row.idCardNumber);
		$('#updateSex').find("option[value=\'"+row.sex+"\']").attr("selected",true);
		a(row.year, row.month, row.day);
		$('#parentName').val(row.parentName);
		$('#parentIdCardType').val(row.parentIdCardType);
		$('#parentIdCardNumber').val(row.parentIdCardNumber);
		$('#relation').val(row.relation);
		$('#idCardType').val(row.idCardType);
		$('#country').val(row.country);
		$('#nation').val(row.nation);
		$('#oversea').val(row.oversea);
		$('#bornPlace').val(row.bornPlace);
		$('#nativePlace').val(row.nativePlace);
		$('#accountCharacter').val(row.accountCharacter);
		$('#nonAgriculturalAccountType').val(row.nonAgriculturalAccountType);
		$('#registeredResidence').val(row.registeredResidence);
		return false;
	}
	function addLink() {
		$("#addDialog").dialog("open");
		$('#updateStudentName').val("");
		$('#updateMobile').val("");
		$('#updateAddress').val("");
		$('#idCardNumber').val("");
		a(0, 0, 0);
		$('#parentName').val("");
		$('#parentIdCardType').val("");
		$('#parentIdCardNumber').val("");
		$('#relation').val("");
		$('#idCardType').val("");
		$('#country').val("");
		$('#nation').val("");
		$('#oversea').val("");
		$('#bornPlace').val("");
		$('#nativePlace').val("");
		$('#accountCharacter').val("");
		$('#nonAgriculturalAccountType').val("");
		$('#registeredResidence').val("");
	}

	$('#cancel').live('click',function(){
		$("#addDialog").dialog("close");
		return false;
	});

	$('#save').live('click',function(){
		var url = "${contextPath}/student/saveStudentInfo";
		var data = {
			id: $("#studentId").val(),
			name: $('#updateStudentName').val(),
			sex: $('#updateSex').val(),
			year: $('#year').val(),
			month: $('#month').val(),
			day: $('#day').val(),
			mobile: $('#updateMobile').val(),
			address: $('#updateAddress').val(),
			idCardNumber:$('#idCardNumber').val(),
			parentName:$('#parentName').val(),
			parentIdCardType:$('#parentIdCardType').val(),
			parentIdCardNumber:$('#parentIdCardNumber').val(),
			relation:$('#relation').val(),
			idCardType:$('#idCardType').val(),
			country:$('#country').val(),
			nation:$('#nation').val(),
			oversea:$('#oversea').val(),
			bornPlace:$('#bornPlace').val(),
			nativePlace:$('#nativePlace').val(),
			accountCharacter:$('#accountCharacter').val(),
			nonAgriculturalAccountType:$('#nonAgriculturalAccountType').val(),
			registeredResidence:$('#registeredResidence').val(),
		}
		$('#updateStudentInfoForm').ajaxSubmit({
			url: url,
			cache:false,
			dataType:'json',
			data:data,
			beforeSend: function() {
				$("#save").attr("disabled",true);
				$("#cancel").attr("disabled", true);
			} ,
			success: function (data) {
				if (data.success) {
					$("#addDialog").dialog("close");
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





	function a(oldyear,oldmonth,oldday){
		var i=1980;
		var date=new Date();
		var year=date.getFullYear();//获取当前年份
		var dropList = "";
		for(var i;i<=year;i++){
			if(i==oldyear){
				dropList=dropList+"<option value='"+i+"' selected>"+i+"</option>";
			}else {
				dropList=dropList+"<option value='"+i+"'>"+i+"</option>";
			}
		}
		$('#year').html(dropList);//生成年份下拉列表
		var monthly = "";
		for(var j=1;j<13;j++){
			if(j == oldmonth){
				monthly=monthly+'<option selected value="'+j+'">'+j+'</option>'
			}else{
				monthly=monthly+'<option value="'+j+'">'+j+'</option>'
			}
		}
		$('#month').html(monthly);//生成月份下拉列表
		var daily = "";
		for(var day=1;day<=31;day++){
			if(day == oldday){
				daily=daily+'<option selected value="'+day+'">'+day+'</option>';
			}else{
				daily=daily+'<option value="'+day+'">'+day+'</option>';
			}
		}
		$('#day').html(daily);
		$('#month').change(function(){
			var currentDay = "";
			var total;
			var flag=$('#year:selected').val();
			var currentMonth=$('#month').val();
			switch (currentMonth){
				case "1":
				case "3":
				case "5":
				case "7":
				case "8":
				case "10":
				case "12":total=31;break;
				case "4":
				case "6":
				case "9":
				case "11":total=30;break;
				case "2":
					//闰年 整除4但是不整除100 或者整除400
					if( (flag%4==0 && flag%100!=0 ) || flag%400){
						total=29;
					}else {
						total=28;
					}
				default :break
			}
			for(day=1;day<=total;day++){
				currentDay=currentDay+'<option value="'+day+'">'+day+'</option>'
			}
			$('#day').html(currentDay);//生成日期下拉列表
		});
	};

	function doPrint(data) {
		var printHtml = '<html><head>';
		printHtml += '<title>小票打印</title><style type="text/css">*{padding:0;margin: 0;}span{font-size: 5px;} td{font-size: 10px;}</style></head><body style="background-color:#fff;">';
		printHtml += '<div style="padding: 0px;margin: 0px;width: 300px;">';
		printHtml += '<span style="text-align: center;margin-left: 25px">汇通婴幼儿智力开发园</span><br/>';
		printHtml += '<span style="text-align: center;margin-left: 50px">收费票据</span><br/>';
		printHtml += '<span>学生姓名：'+data[0].studentName+'</span><br/>';
		printHtml += '<span>学号：'+data[0].studentId+'</span><br/>';
		printHtml += '<span>班级：'+data[0].gradeName+data[0].className+'</span><br/>';
		printHtml += '<span><table><tr><td align="center">序号</td><td align="center">项目</td><td align="center">小计</td></tr>';
		printHtml += '<tr><td colspan="3">----------------------------------------</td></tr>';
		var cash = 0;
		var dz = 0;
		for(i = 0;i<data.length;i++){
			var row = data[i];
			if(row.payType == 0){
				cash += row.actualChargeAmount;
			}else{
				dz += row.actualChargeAmount;
			}
			printHtml += '<tr><td align="center">'+(i+1)+'</td><td align="center">'+row.chargeProjectName+'</td><td align="center">'+row.actualChargeAmount+'</td></tr>';
		}
		printHtml += '<tr><td colspan="5">----------------------------------------</td></tr></table></span>';
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
