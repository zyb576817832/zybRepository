<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta2.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务工时页面</title>
<link href="${path}/v3/calendar.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">
	var retMonth = '';//即将跳转到的月份
	$(function(){
		//为切换日历定义事件
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
	        //如果切换的月份不在变量中则去数据库查询
			var month = curData.month;
			if(month<10){
				month = '0'+ month;
			}
			var searchMonth =curData.year +'-' + month;
			if(monthHaveSearch.get(searchMonth)==null){
				getNeedHours();//切换月份时,数据库补充月份工时
			};
	    });
		
		//清除掉其他月份的报工
		clearMonthHours();
	});	
	
	function formatDay(date){
		var opts = $(this).calendar('options');
		var month = opts.month;
		if(month<10){
			month = '0'+ month;
		}
		
		var searchMonth =opts.year +'-' + month;
		if(monthHaveSearch.get(searchMonth)==null){
			getNeedHours();
		}
		
		var newDate = date.getDate();
		if(newDate<10){
			newDate = '0'+ newDate;
		}
		var dayHours = retMonth + '-'+ newDate;//当前月份加日期6*7=42个日期方块
		var dateHours = hashMap.get(dayHours);
		var d = date.getDate();
		if(dateHours){//如果这一天有工时
			if(8==dateHours){//工时为8则样式1
				return '<div class="md3">'  + 8  + '</div>' + d;
			}else{//其他则样式2
				return '<div class="md4">' + dateHours + '</div>' + d;
			}
		}else{
			return d;
		};
	}
	
	//根据月份获取该月所有工时信息
	var hashMap = new HashMap();
	var monthHaveSearch = new HashMap();//记录该月份所有工时信息
	function getNeedHours(){
		if(!retMonth){
			var myDate = new Date();
			var myMonth = myDate.getMonth()+1;
			retMonth = myDate.getFullYear()+'-'+myMonth;
		}
		var ranNumber=Math.random(); //IE缓存
		var url ="${path}/app/userHours/getHoursByMonth.action?taskId=${taskId}&registMonth="+retMonth+"&&ranNumber="+ranNumber;
		$.ajax({
			type:'get',
			url:url,
			async:false,//不异步
			success:function(data){
				if(data != null){
					//hashMap.removeAll();切换月份不用情况：因为日期对应工期始终唯一
					for(var mapKey in data){
						hashMap.put(mapKey, data[mapKey]);
					};
					monthHaveSearch.put(retMonth, hashMap);
				}
			},
			dataType:'json'
		});
	}
</script>
</head>
<body>

<div class="easyui-calendar" style="width:370px;height:230px;" data-options="formatter:formatDay" id="tab6_cl"></div>
<div style="position:absolute;left:400px;top:115px"><h2><spring:message code='HOURSALL'/>:${totalHours}<spring:message code='HOURSH'/></h2></div>
</body>
</html>