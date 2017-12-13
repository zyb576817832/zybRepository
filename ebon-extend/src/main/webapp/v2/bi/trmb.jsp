<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<script type="text/javascript" src="${path}/script/fusioncharts-suite-xt/js/fusioncharts.js"></script>
<script type="text/javascript" src="${path}/script/fusioncharts-suite-xt/js/themes/fusioncharts.theme.fint.js"></script>
<script type="text/javascript" src="${path}/script/datepicker/WdatePicker.js"></script>
<style type="text/css">
body{text-align:center} 
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>开发费用跟踪表</title>
<script type="text/javascript">
	
	function submitForm(){
		var startMonth = document.getElementById("startMonth").value;
		var endMonth = $("#endMonth").val();
		var proj_id = $("#proj_id").val();
		if(startMonth&&endMonth){
			var myurl = "/uPMS/budgetCPC/getBudgetCPC.do?proj_id=" + proj_id + "&startMonth=" + startMonth + "&endMonth=" + endMonth;
			$.ajax({
				type : 'post',
				url : myurl,
				success : callbackFun
			});
		}else{
			alert("请输入起始或结束日期");
		}
	}
	
	//生成图形
	function callbackFun(msg){
		var dataSource = eval("(" + msg + ")");
		var projName = '${proj_id}';
		//处理后台返回的数据
		var month = new Array();
		var budget = new Array();
		var monthCost = new Array();
		var actualTotal = new Array();
		
		for (var int = 0; int < dataSource.data.length; int++) {
			month.push({"name":dataSource.data[int].month});
			budget.push({"value":dataSource.data[int].budget});
			monthCost.push({"value":dataSource.data[int].monthCost});
			actualTotal.push({"value":dataSource.data[int].actualTotal});
		}
		
		 var revenueChart = new FusionCharts({
		      "type": "mscombidy2d",
		      "renderAt": "chartContainer",
		      "width": "570",
		      "height": "300",
		      "dataFormat": "json",
		      "dataSource": {
		    	  "chart": {
		    		  	"caption": "TRMB",
	                    "subCaption": "Current Project : " + projName,
		    	        "showvalues": "0",//是否显示每个图上的值
		    	        "setadaptiveymin": "1",
		    	        "setadaptivesymin": "1",//采取自适应Y的值，最小坐标为0
		    	        "linethickness": "3",//折线的厚度
		    	        "showborder": "0",//是否显示边框
		  		        "pYAxisName": "total value",
		  		        "showcanvasborder": "0",//帆布边框1为不显示
				        "canvasbordercolor": "#DEB887",//画布边框颜色
				        "canvasborderthickness": "1",//画布边框厚度
				        "sYAxisName": "month value",
				        "legendborderalpha": "0",//下部标题表框是否显示
				        "bgcolor": "FFFFFF"//除XY轴以外的区域
		    	    },
		    	    
		    	    //X轴的数据列表
		    	    "categories": [
		    	        {
		    	            "category": month
		    	        }
		    	    ],
		    	    "dataset": [
		    	        //项目当期预算 折线
		    	        {
		    	        	"parentYAxis": "P",
		    	            "seriesname": "Project Month Budget",//依照左侧Y轴
		    	            "renderas": "Line",
		    	            "data": budget
		    	        },
		    	        
		    	        //项目当期实际 柱形图
		    	        {	
		    	        	"parentYAxis": "S",
		    	            "seriesname": "Project Month Cost",//依照右侧Y轴
		    	            "renderas": "Column",
		    	            "data": monthCost
		    	        },
		    	        
		    	        //项目总实际费用 折线
		    	        {	
		    	        	"parentYAxis": "P",
		    	            "seriesname": "Project Total Cost",//依照左侧Y轴
		    	            "renderas": "Line",
		    	            "data": actualTotal
		    	        }
		    	    ]
		    	   
		      }
		  });
		
		  revenueChart.render();
	}
	
</script>
</head>
<body>
	<input type="hidden" id="proj_id" value="${proj_id }">
	<div style="position:fixed;bottom:0;left:0px;_position:absolute;_margin-top:expression(this.style.pixelHeight+document.documentElement.scrollTop);">
		<table width="100%" height="5%" align="top" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">Start Month</td>
				<td width="50" ><input type="text" name ="startMonth" id="startMonth"  value="${defalutStartMonth}" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy/MM'})"></td>
				<td align="center" >End Month</td>
				<td width="50" ><input type="text" name ="endMonth"  id="endMonth" value="${currentMonth}"  onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy/MM'})"></td>
				<td ><input type="button" name ="OK" value="确定" onclick="submitForm()"></td> 
			</tr>
		</table>
	</div>
	<div id="chartContainer"></div>
</body>
</html>