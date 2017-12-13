//自定义一个map:格式化日期的时候更方便
function HashMap(){
    this.map = {};
}
HashMap.prototype = {
	    put : function(key , value){
	        this.map[key] = value;
	    },
	    get : function(key){
	        if(this.map.hasOwnProperty(key)){
	            return this.map[key];
	        }
	        return null;
	    },
	    remove : function(key){
	        if(this.map.hasOwnProperty(key)){
	            return delete this.map[key];
	        }
	        return false;
	    },
	    removeAll : function(){
	        this.map = {};
	    },
	    keySet : function(){
	        var _keys = [];
	        for(var i in this.map){
	            _keys.push(i);
	        }
	        return _keys;
	    }
	};

//根据当前的日期获取即将跳转到的月份
var retMonth = '';//即将跳转到的月份
function getSelectMonthHours(type, curData){
	var year = curData.year;
	var month = curData.month;
	if(1==type){//上一月
		if(1==month){
			year = year-1;
			retMonth=year+'-12';
		}else{
			month = month-1;
			if(month<10){
				retMonth=year+'-0'+ month;
			}else{
				retMonth=year+'-'+ month;
			}
		}
	}else if(2==type){//下一月
		if(12==month){
			year = year+1;
			retMonth=year+'-01';
		}else{
			month = month+1;
			if(month<10){
				retMonth=year+'-0'+ month;
			}else{
				retMonth=year+'-'+ month;
			}
		}
	}else if(3==type){//上一年
		year = year-1;
		if(month<10){
			retMonth=year+'-0'+ month;
		}else{
			retMonth=year+'-'+ month;
		}
	}else{//下一年
		year = year+1;
		if(month<10){
			retMonth=year+'-0'+ month;
		}else{
			retMonth=year+'-'+ month;
		}
	}
	return retMonth;
}

//清除掉其他月份的报工
function clearMonthHours(){
	$(".calendar-other-month div:first-child").each(function(){
	    $(this).hide();
	});
}

//格式化时间：转化为10/11-11/02
function formatterBaseDateFn(value, rowData, index){
	var ret = "";
	if(value&&value.length==10){//2017-10-11
		ret += value.substr(5,2);
		ret += '/';
		//ret += value.substr(-2);//IE8不支持
		ret += value.substr(8,2);
		var endDate = rowData.BASE_END_DATE;
		if(endDate){
			ret += '-';
			ret += endDate.substr(5,2);
			ret += '/';
			ret += endDate.substr(8, 2);
		}else{
			ret += '-NA';
		}
	}else{
		ret += 'NA-NA';
	}
	return ret;
}
//格式化时间：转化为10/11-11/02
function getActDateFn(startDate,endDate){
	var ret = "";
	if(startDate&&startDate.length==10){//2017-10-11
		ret += startDate.substr(5,2);
		ret += '/';
		ret += startDate.substr(8,2);
		if(endDate){
			ret += '-';
			ret += endDate.substr(5,2);
			ret += '/';
			ret += endDate.substr(8,2);
		}else{
			ret += '-NA';
		}
	}else{
		ret += 'NA-NA';
	}
	return ret;
}
//格式化状态
/*function formatterStatusFn(value, rowData, index){
	var ret = "";
	if(value=='1'){
		ret = '未开始';
	}else if('2'==value){
		ret = '已开始';
	}else if('3'==value){
		ret = '审批中';
	}else if('4'==value){
		ret = '已完成';
	}else if('5'==value){
		ret = '已取消';
	}
	return ret;
}*/