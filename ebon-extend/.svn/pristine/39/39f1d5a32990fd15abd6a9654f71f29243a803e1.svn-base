<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<script type="text/javascript" src="${path}/script/fusioncharts-suite-xt/js/fusioncharts.js"></script>
<script type="text/javascript" src="${path}/script/fusioncharts-suite-xt/js/themes/fusioncharts.theme.fint.js"></script>
<script type="text/javascript" src="${path}/script/datepicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目CPC里程碑</title>
<script type="text/javascript">

	function submitForm(){
		var startMonth = document.getElementById("startMonth").value;
		var endMonth = $("#endMonth").val();
		var proj_id = $("#proj_id").val();
		if(startMonth&&endMonth){
			var myurl = "/uPMS/milestoneCPC/getMilestoneCPC.do?proj_id=" + proj_id + "&startMonth=" + startMonth + "&endMonth=" + endMonth;
			$.ajax({
				type : 'post',
				url : myurl,
				success : callbackFun
			});
		}else{
			alert("请输入起始或结束日期");
		}
	}
	//定义数据来源
	function callbackFun(msg){
		var dataSource = eval("(" + msg + ")");
		var projName = '${proj_id}';
		var mydata = dataSource.data;
		//构造类型
		var month = new Array();
		var onTimeMonth = new Array();
		var QA0 = new Array();
		var QA1 = new Array();
		var QA2 = new Array();
		var QA3 = new Array();
		var QA4 = new Array();
		if(mydata){
			for (var int = 0; int < mydata.length; int++) {
				month.push({"label":mydata[int]["month"]});
				onTimeMonth.push({"value":mydata[int]["onTimeMonth"]});
				QA0.push({"value":mydata[int]["QA0"]});
				QA1.push({"value":mydata[int]["QA1"]});
				QA2.push({"value":mydata[int]["QA2"]});
				QA3.push({"value":mydata[int]["QA3"]});
				QA4.push({"value":mydata[int]["QA4"]});
				
			}
			new FusionCharts({
				"type": "msline",
				"renderAt": "chartContainer",
				"width": "570",
				"height": "300",
				"dataFormat": "json",
				"dataSource": {
					"chart": {
				        "caption": "里程碑点跟踪表",
				        "subCaption": "Current Project : " + projName,
				        "xaxisname": "Month",//X轴name
				        "yaxisname": "Month",//X轴name
				        "bgcolor": "FFFFFF",//除XY轴以外的区域
				        "showalternatehgridcolor": "1",//Y轴之间区域是否隔行变色
				        "divlinecolor": "CCCCCC",//分割线的颜色
				        "showvalues": "0",//是否显示对应值0不显示
				        "showcanvasborder": "0",//帆布边框1为不显示
				        "canvasbordercolor": "#DEB887",//画布边框颜色
				        "canvasborderthickness": "1",//画布边框厚度
				        "yaxismaxvalue": "41990400000",
				        "captionpadding": "15",
				        "linethickness": "3",//折线厚度
				        "yaxisvaluespadding": "15",
				        "legendshadow": "0",
				        "legendborderalpha": "0",
				        "palettecolors": "#f8bd19,#008ee4,#33bdda,#e44a00,#6baa01,#583e78",
				        "showborder": "0",
				        "defaultNumberScale": "ms",
				        "numberScaleValue": "1000,60,60,24,30",
				        "numberScaleUnit": "s,min,hr,day,month"
				    },
				    "categories": [
				        {
				            "category": month
				        }
				    ],
				    "dataset": [
				        {
				            "seriesname": "QA0",
				            "renderas": "Line",
				            "data": QA0
				        },
				        {
				            "seriesname": "QA1/项目启动会议",
				            "renderas": "Line",
				            "data": QA1
				        },
				        {
				            "seriesname": "QA2/正式批量供货",
				            "renderas": "Line",
				            "data": QA2
				        },
				        {
				            "seriesname": "QA3",
				            "renderas": "Line",
				            "data": QA3
				        },
				        {
				            "seriesname": "QA4",
				            "renderas": "Line",
				            "data": QA4
				        },
				        {
				            "seriesname": "onTime",
				            "renderas": "Line",
				            "data": onTimeMonth
				        }
				        
				        
				    ]
				}
			}).render();
		}
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
	<div id="chartContainer" align="center"></div>
</body>
</html>