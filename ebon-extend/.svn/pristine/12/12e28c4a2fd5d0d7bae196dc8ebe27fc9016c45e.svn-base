<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title>成本归集报表导出</title>
<script language="javascript" type="text/javascript" src="${path}/script/datepicker/WdatePicker.js"></script>
<script type="text/javascript">

	function submitForm1() {
		var date = $('#costStartDate').val();
		var date2 = $('#costEndDate').val();
		if(date && date2) {
			var startYear = Number(date.substr(0,4));
			var endYear = Number(date2.substr(0,4));
			if(startYear == endYear){
				var startMonth = Number(date.substr(5,2));
				var endMonth = Number(date2.substr(5,2));
				if(startMonth - endMonth <= 0){
					var flag = confirm("Export report may need a long time, please wait! Are you sure export?");
					if(flag){
						$('#exportForm1').form('submit');
					}
	    		} else {
	    			alert("StartDate is not greater than endDate! ");
	    		}
	    	} else{
	    		alert("Please choose same year!");
	    	}
		} else {
			alert("Please choose month!");
		}
	}
	
	function submitForm2() {
		var date = $('#deptStartDate').val();
		var date2 = $('#deptEndDate').val();
		if(date && date2) {
			var startYear = Number(date.substr(0,4));
			var endYear = Number(date2.substr(0,4));
			if(startYear == endYear){
				var startMonth = Number(date.substr(5,2));
				var endMonth = Number(date2.substr(5,2));
				if(startMonth - endMonth <= 0){
					var flag = confirm("Export report may need a long time, please wait! Are you sure export?");
					if(flag){
						$('#exportForm2').form('submit');
					}
	    		} else {
	    			alert("StartDate is not greater than endDate! ");
	    		}
	    	} else{
	    		alert("Please choose same year!");
	    	}
		} else {
			alert("Please choose month!");
		}
	}
	
</script>
</head>
<body>
	<form id="exportForm1" action="${path}/xlsexport/cost" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>
					StartMonth：
					<input type="text" id="costStartDate" name="costStartDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="Wdate"/>
					EndMonth：
					<input type="text" id="costEndDate" name="costEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="Wdate"/>
					<a href="#" onclick="submitForm1();" class="easyui-linkbutton" iconCls="icon-search">Export Cost Report</a>
				</td>
			</tr>
		</table>
	</form>
	<form id="exportForm2" action="${path}/xlsexport/dept" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>
					StartMonth：
					<input type="text" id="deptStartDate" name="deptStartDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="Wdate"/>
					EndMonth：
					<input type="text" id="deptEndDate" name="deptEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="Wdate"/>
					<a href="#" onclick="submitForm2();" class="easyui-linkbutton" iconCls="icon-search">Export Dept Report</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
