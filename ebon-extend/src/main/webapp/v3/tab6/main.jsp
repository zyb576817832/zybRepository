<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta2.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${path}/v3/calendar.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">

	var retMonth = '';//即将跳转到的月份
	$(function(){
		$('#tab6_cl div.calendar-nav').click(function (b) {
			var curData = $('#tab6_cl').calendar('options');//切换前的日期
	        var o = $(this);
	        if (o.hasClass('calendar-prevmonth')){
	        	retMonth = getSelectMonthHours(1,curData);//点击上一月按钮
	        }else if (o.hasClass('calendar-nextmonth')){
	        	retMonth = getSelectMonthHours(2,curData);//点击下一月按钮
	        }else if (o.hasClass('calendar-prevyear')){
	        	retMonth = getSelectMonthHours(3,curData);//点击上一年按钮
	        }else{
	        	retMonth = getSelectMonthHours(4,curData);//点击下一年按钮
	        }
			setSumHours();
		});
		//默认点击当天日期
		if('${registDate}'){
			onSelectDateFn(null, '${registDate}');
		}else{
			onSelectDateFn(new Date(),null);
		}
	});

	function formatDay(date){
		var month = date.getMonth()+1;
		if(month<10){
			month = '0'+ month;
		}
		
		var searchMonth =date.getFullYear() +'-' + month;
		if(!monthHaveSearch.keySet().contains(searchMonth)){
			getNeedHours(searchMonth);
		}
		
		var newDate = date.getDate();
		if(newDate<10){
			newDate = '0'+ newDate;
		}
		var dayHours = searchMonth + '-'+ newDate;//当前月份加日期6*7=42个日期方块
		hashMap = monthHaveSearch.get(searchMonth);
		var dateHours = hashMap.get(dayHours);
		//格式化报工的小数
		var d = date.getDate();
		if(dateHours){//如果这一天有工时
			if(8==dateHours){//工时为8则样式1
				return '<div class="md">'  + 8  + '</div>' + d;
			}else{//其他则样式2
				
				if(dateHours.indexOf('.')>-1){
					//小数的话,向上取整数
					var myFloat = parseFloat(dateHours);
					dateHours = Math.ceil(myFloat);
				}
				return '<div class="md1">' + dateHours + '</div>' + d;
			}
		}else{
			return d;
		};
	}
	
	//根据月份获取该月所有工时信息
	var hashMap = new HashMap();
	var monthHaveSearch = new HashMap();//记录该月份所有工时信息
	var monthSumHoursMap = new HashMap();//记录该月份所有工时信息
	function getNeedHours(searchMonth){
		var monthSumHours =1;
		if(!searchMonth){
			var myDate = new Date();
			var myMonth = myDate.getMonth()+1;
			if(myMonth<10){
				myMonth = '0'+ myMonth;
			}
			searchMonth = myDate.getFullYear()+'-'+myMonth;
		}
		var ranNumber=Math.random(); //IE缓存
		var url ="${path}/app/userHours/getHoursByMonth.action?userId=${userId}&registMonth="+searchMonth+"&ranNumber="+ranNumber;
		$.ajax({
			type:'get',
			url:url,
			async:false,//不异步
			success:function(data){
				if(data != null){
					//hashMap.removeAll();切换月份不用情况：因为日期对应工期始终唯一
					for(var mapKey in data){
						var myFloat = parseFloat(data[mapKey]);
						monthSumHours =FloatAdd(monthSumHours,myFloat);
						hashMap.put(mapKey, data[mapKey]);
					};
					monthHaveSearch.put(searchMonth, hashMap);
					monthSumHoursMap.put(searchMonth, monthSumHours);
				}
			},
			dataType:'json'
		});
	}
	$(function(){
		setSumHours();
	});
	
	function setSumHours(){
		if(!retMonth){
			var myDate = new Date();
			var myMonth = myDate.getMonth()+1;
			if(myMonth<10){
				myMonth = '0'+ myMonth;
			}
			retMonth = myDate.getFullYear()+'-'+myMonth;
		}
		var sumHours = monthSumHoursMap.get(retMonth);
		sumHours = parseFloat(sumHours);
		sumHours = sumHours.toFixed(2);
		$('#effort').html(sumHours-1);
	}
	
	function onSelectDateFn(date, rightDate1){
		var compareRegistDate = "";
		if(rightDate1){
			compareRegistDate = new   Date(rightDate1.replace(/-/g,   "/"));
		}else{
			compareRegistDate = date;
		}
		var time1 = new Date();
		var time2 = new Date(compareRegistDate);
		if(time2.getTime()>time1.getTime()){
			$("#tab6_center").html("");
			return;
		}
		var rightDate = rightDate1;
		if(date){
			var newDate = date.getDate();
			if(newDate<10){
				newDate= '0'+ newDate;
			}
			var newMonth = date.getMonth()+1;
			if(newMonth<10){
				newMonth= '0'+ newMonth;
			}
			 rightDate = date.getFullYear()+"-"+ newMonth +"-"+newDate;
		}
		var moveToDate = new   Date(rightDate.replace(/-/g,   "/"));
		$('#tab6_cl').calendar('moveTo', moveToDate);
		var sumTaskHours = hashMap.get(rightDate);
		if(sumTaskHours && sumTaskHours.indexOf('.')>-1){
			//小数的话,四舍五入
			var myFloat = parseFloat(sumTaskHours);
			sumTaskHours = myFloat.toFixed(2);
		}
		$("#tab6_center").html("");//先将div内部清空,否则会不停追加
		var mainHTML = "<iframe name=\"tab6_center_add_iframe\" id=\"tab6_center_add_iframe\" scrolling=\"no\" frameborder=\"0\"  src=\"${path}/app/userHours/getListOrAddPage.action?userId=${userId}&registDate=" + rightDate + "&sumTaskHours="+ sumTaskHours + "\" style=\"width:100%;height:100%;\"></iframe>";
		$("#tab6_center").append(mainHTML);
	}
	
	function tableReload(){
		
	}
	
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<!-- 左侧：日历区域 -->
	<div data-options="region:'west',split:true" style="width: 460px;margin-left: 2px;margin-top: 2px;">
		<div class="easyui-calendar" style="width:450px;height:450px;" data-options="formatter:formatDay,onSelect:onSelectDateFn" id="tab6_cl"></div>
		<div style="text-align: center;"><h2><spring:message code='CURRENT_MONTH_HOURS'/>:<label id="effort"></label><spring:message code='HOURSH'/></h2></div>
	</div>
	<!-- 右侧：报工详情页面  -->
	<div data-options="region:'center',split:true" style="width: 400px;" id="tab6_center"></div>
	</div>
</body>
</html>