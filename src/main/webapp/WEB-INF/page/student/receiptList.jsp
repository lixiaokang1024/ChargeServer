<%@page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/page/header.jsp"%>
<html>
<body>
<table id="datagrid" class="easyui-datagrid"
	   url="${ contextPath }/studentChargeInfo/receiptList" title="补打小票列表" toolbar="#tb"
	   rownumbers="true" pagination="true" pageSize="20" showFooter="true">
	<thead>
	<tr style="valign: middle">
		<th field="studentId" sortable="true" align="center" width="150px">学号</th>
		<th field="studentName" sortable="true" align="center" width="150px">学生姓名</th>
		<th field="receiptId" sortable="true" align="center" width="150px">小票编号</th>
		<th field="gradeName" sortable="true" align="center" width="150px">年级</th>
		<th field="className" sortable="true" align="center" width="150px">班级</th>
		<th field="chargeTime" sortable="true" align="center" width="150px">最近支付时间</th>
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
					<input type="text" id="name" name="name" style="width: 110px;" value=""/>
				</td>
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
<script type="text/javascript">
	/*搜索*/
	$(function(){
		$('#searchLink').click(function(){
			var data = getFormData();
			$('#datagrid').datagrid('load', data);
		});
	});
	function getFormData() {
		var data = {
			name: $('#name').val()
		};
		return data;
	}
	function settings(value, row) {
		var html = '<div style="text-align: center;">';
		html += '<a style="font-size: 15px;margin:0 2px 0 1px; line-height:1.5em;cursor:pointer;" onclick="printReceipt(\''+row.receiptId+'\')">补打小票</a>';
		html += '</div>';
		return html;
	}

	function printReceipt(receiptId) {
		var url = "${contextPath}/studentChargeInfo/count";
		var data = {
			receiptId: receiptId
		}
		$.ajax({
			url: url,
			cache: false,
			dataType: 'json',
			data: data,
			type: "post",
			success: function (data) {
				$.messager.confirm('系统消息', "是否确认打印！", function (r) {
					if(r){
						doPrint(data.rows);
					}
				});
				$("#datagrid").datagrid("reload");
			},
			error: function (data) {
				$.messager.alert('系统消息', data.msg,'error');
			}

		});
		return false;
	}
    function doPrint(data) {
    	var printHtml = '<html><head>';
		printHtml += '<title>小票打印</title><style type="text/css">*{padding:0;margin: 0;}span{font-size: 5px;} td{font-size: 10px;}</style></head><body style="background-color:#fff;">';
		printHtml += '<div style="padding: 0px;margin: 0px;width: 300px;">';
        printHtml += '<span style="text-align: center;">汇通婴幼儿智力开发园</span><br/>';
		printHtml += '<span style="text-align: center">收费票据</span><br/>';
		printHtml += '<span>学生姓名：'+data[0].studentName+'</span><br/>';
		printHtml += '<span>学号：'+data[0].studentId+'</span><br/>';
		printHtml += '<span>班级：'+data[0].gradeName+data[0].className+'</span><br/>';
		printHtml += '<span>缴费属期：'+data[0].chargeTimeStr+'</span><br/>';
		printHtml += '<span><table><tr><td align="center">序号</td><td align="center">项目</td><td align="center">基数</td><td align="center">系数</td><td align="center">小计</td></tr>';
		printHtml += '<tr><td colspan="5">----------------------------------------</td></tr>';
		var shouldCharge = 0;
		var useDepositAmount = 0;
		var cash = 0;
		var dz = 0;
        for(i = 0;i<data.length;i++){
            var row = data[i];
			shouldCharge += row.chargeAmount;
			useDepositAmount += row.useDepositAmount;
			if(row.payType == 0){
				cash += row.actualChargeAmount;
			}else{
				dz = row.actualChargeAmount;
			}
            var base = row.chargeAmount / row.chargeCoefficient;
        	printHtml += '<tr><td align="center">'+(i+1)+'</td><td align="center">'+row.chargeProjectName+'</td><td align="center">'+base+'</td><td align="center">'+row.chargeCoefficient+'</td><td align="center">'+row.chargeAmount+'</td></tr>';
		}
		printHtml += '<tr><td colspan="5">----------------------------------------</td></tr></table></span>';
		printHtml += '<span>应缴费金额：'+shouldCharge+'</span><br/>';
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
