<meta http-equiv="X-UA-Compatible"  content="IT=edge,chrome=IE8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta2.jsp"%>
<html>
<head>
<title>全部任务页面</title>
<style type="text/css">
	#float_img.fixed {
		position: absolute;
		top: 0;
		left: 0;
		z-index: 1;
		width: 100%;
		border-bottom: 5px solid #ffffff;
		margin-left: 400px;
		margin-top: 2px;
	}
</style>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">
	var selectedRow;//全局变量,选中行任务
	$(function(){
		//高级检索区默认隐藏
		serarchDivHideFn();
		
		$('#dd1').datebox({
		    formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();},
		    parser: function(date){ return new Date(Date.parse(date.replace(/-/g,"/")));}
		});
	});
	
	function convert(rows){
		function exists(rows, parentId){
			for(var i=0; i<rows.length; i++){
				if (rows[i].id == parentId) return true;
			}
			return false;
		}
		
		var nodes = [];
		// get the top level nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (!exists(rows, row.parentId)){
				nodes.push({
					id:row.id,
					text:row.name
				});
			}
		}
		
		var toDo = [];
		for(var i=0; i<nodes.length; i++){
			toDo.push(nodes[i]);
		}
		while(toDo.length){
			var node = toDo.shift();	// the parent node
			// get the children nodes
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if (row.parentId == node.id){
					var child = {id:row.id,text:row.name};
					if (node.children){
						node.children.push(child);
					} else {
						node.children = [child];
					}
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
	
	//高级检索区域隐藏
	function serarchDivHideFn(){
		$('#search_div_detail').hide();
	}
	//高级检索区显示
	function serarchDivShowFn(){
		$('#search_div_detail').show();
	}
	//普通检索区域隐藏
	function serarchGeneralDivHideFn(){
		$('#search_div_general').hide();
	}
	//点击高级查询按钮
	function showDetailSearch(){
		//当点击高级检索设置为110
		$("#search_div_all").height(170);
		serarchGeneralDivHideFn();
		serarchDivShowFn();
		
		//为了提高页面响应速度,当点击高级查询按钮时加载部门树结构
		if('1'=='${leader}'){
			var outsideDiv = document.getElementById("deptSearch");
			outsideDiv.innerHTML = '';
			outsideDiv.innerHTML = '<spring:message code="DEPTHEAD"/>：<select id="cc" class="easyui-combotree" style="width:200px;" multiple style="width:200px;"></select>';
			$('#cc').combotree({    
			    url: '${path}/app/userTaskPlatfrom/deptComboxTree?userId=${userId}',    
			    loadFilter : function(rows){
			    	return convert(rows);
			    }
			}); 
		}
	}
	
	//普通检索查询方法
	function generalSearchFn(){
		var searchName = $('#searchName').val();
		if(searchName){
			searchName = encodeURI(searchName, "UTF-8");//中文乱码
			$.post('${path}/app/userTaskPlatfrom/getAllBySearchName.action?userId=${userId}&searchName='+ searchName, function(data){
				$('#tab1_dg').datagrid('loadData',data);
			},'json');
		}else{
			$.messager.alert('Message','Please enter');
		}
	}
	
	//普通的重置查询方法
	function generalResetFn(){
		tableReload();
	}
	
	function detailSearchFn(){
		var arrayType = $('#arrayType').combobox('getValues');//下拉
		var assignUserName = $('#assignUserName').val();
		var respUserName = $('#respUserName').val();
		var baseStartDate = $('#baseStartDate').val();
		var baseStartDate1 = $('#baseStartDate1').val();
		var baseEndDate = $('#baseEndDate').val();
		var baseEndDate1 = $('#baseEndDate1').val();
		var arraySerious =$('#arraySerious').combobox('getValues');//下拉
		var arrayPriority = $('#arrayPriority').combobox('getValues');//下拉
		var arrayProjSize = $('#arrayProjSize').combobox('getValues');//项目大小下拉
		//项目属性建索
		var sop = $('#sop').val();
		var sop1 = $('#sop1').val();
		var ph1 = $('#ph1').val();
		var ph2 = $('#ph2').val();
		var projO = $('#projO').val();
		var projB = $('#projB').val();
		var customerNumber = $('#customerNumber').val();
		var vehicle = $('#vehicle').val();
		var engine = $('#engine').val();
		var depts = '';
		if('1'=='${leader}'){
			depts = $('#cc').combotree('getText');
		}
		var projCode = $('#projCode').val();
		var paramsUrl = '';
		
		if(arrayType.length>0){
			paramsUrl+='&arrayType=' + arrayType;
		}
		if(arraySerious.length>0){
			paramsUrl+='&arraySerious=' + arraySerious;
		}
		if(arrayPriority.length>0){
			paramsUrl+='&arrayPriority=' + arrayPriority;
		}
		if(arrayProjSize.length>0){
			paramsUrl+='&arrayProjSize=' + arrayProjSize;
		}
		if(ph1){
			paramsUrl+='&appProjInfo.ph1=' + ph1;//ph1
		}
		if(ph2){
			paramsUrl+='&appProjInfo.ph2=' + ph2;//ph2
		}
		if(projO){
			paramsUrl+='&appProjInfo.projO=' + projO;//0 Number
		}
		if(projB){
			paramsUrl+='&appProjInfo.projB=' + projB;//B Number
		}
		if(projCode){
			paramsUrl+='&appProjInfo.projCode=' + projCode;//项目编号
		}
		if(customerNumber){
			paramsUrl+='&appProjInfo.customerNumber=' + customerNumber;//客户零件号
		}
		if(vehicle){
			paramsUrl+='&appProjInfo.vehicle=' + vehicle;//车型
		}
		if(engine){
			paramsUrl+='&appProjInfo.engine=' + engine;//发动机
		}
		if(depts){
			paramsUrl+='&depts=' + depts;
		}
		if(assignUserName){
			paramsUrl+='&assignUserName=' + assignUserName;
		}
		if(respUserName){
			paramsUrl+='&respUserName=' + respUserName;
		}
		if(baseStartDate){
			paramsUrl+='&baseStartDate=' + baseStartDate;
		}
		if(baseEndDate){
			paramsUrl+='&baseEndDate=' + baseEndDate;
		}
		if(baseStartDate1){
			paramsUrl+='&baseStartDate1=' + baseStartDate1;
		}
		if(baseEndDate1){
			paramsUrl+='&baseEndDate1=' + baseEndDate1;
		}
		if(projCode){
			paramsUrl+='&projCode=' + projCode;
		}
		if(sop){
			paramsUrl+='&appProjInfo.sop=' + sop;
		}
		if(sop1){
			paramsUrl+='&sop1=' + sop1;
		}
		var ranNumber=Math.random(); //IE缓存
		paramsUrl+='&ranNumber=' + ranNumber;
		/* var dgUrl = $('#tab1_dg').datagrid('options').url+paramsUrl; */
		var dgUrl = '';
		var f = '${type}';
		if(f == '1'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			if(arraystatus.length>0){
				paramsUrl+='&arraystatus=' + arraystatus;
			}
			dgUrl = '${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}'+paramsUrl;
		}else if(f == '2'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			if(arraystatus.length>0){
				paramsUrl+='&arraystatus=' + arraystatus;
			}
			if(arraystatus.length==0){
				arraystatus[0] = '1';
				arraystatus[1] = '2';
				arraystatus[2] = '3';
				paramsUrl+='&arraystatus=' + arraystatus;
			}
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}'+paramsUrl;
		}else if(f == '3'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1'+paramsUrl;
		}else if(f == '4'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			if(arraystatus.length>0){
				paramsUrl+='&arraystatus=' + arraystatus;
			}
			if(arraystatus.length==0){
				arraystatus[0] = '2';
				arraystatus[1] = '3';
				paramsUrl+='&arraystatus=' + arraystatus;
			}
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}'+paramsUrl;
		}else{
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4'+paramsUrl;
		}
		$('#tab1_dg').datagrid({
			url:dgUrl,
			pageSize:10,
			pageList :[10,20,30,40,50]
			
		});
	}
	
	function detailResetFn(){
		$('#search_div_detail_from').form("clear");
		var f = '${type}';
		var dgUrl = '';
		if(f == '1'){
			dgUrl = '${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}';
		}else if(f == '2'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}&arraystatus=1,2,3';
		}else if(f == '3'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1';
		}else if(f == '4'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&arraystatus=2,3';
		}else{
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4';
		}
		$('#tab1_dg').datagrid({
			url:dgUrl,
			pageSize:10,
			pageList :[10,20,30,40,50]
			
		});
		/* $.post(dgUrl,function(data){
			$('#tab1_dg').datagrid('loadData',data); 
		},'json'); */
	}
	
	//点击任务列表
	function tab1_dg_selectFn(index, rowData){
		selectedRow = rowData;
		getTaskMainPage(selectedRow.ID);
	}
	
	//新跳转到任务详情页面
	function getTaskMainPage(taskId){
		$("#tab1_right").html("");//先将div内部清空,否则会不停追加
		if(!taskId){
			return;
		}
		var mainHTML = "<iframe name=\"tab1_right_iframe\" id=\"tab1_right_iframe\" scrolling=\"no\" frameborder=\"0\"  src=\"${path}/v3/app/menu/getTaskMainPage.action?randomNum=${randomNum}&type=${type}&userId=${userId}&taskId=" + taskId + "\" style=\"width:100%;height:100%;\"></iframe>";
		$("#tab1_right").append(mainHTML);
	}
	
	//在TableReload的时候,会执行这个方法赋值
	var selectTaskId = '';
	function selectSomeRowID(taskId){
		selectTaskId = taskId;
	}
	
	function onLoadSuccessFn(){
		if(selectTaskId){
			var rows = $('#tab1_dg').datagrid('getRows');
			var flag = true;//如果存在则选中,如果不存在则设置为空
			for(var i = 0; i<rows.length; i++){
				if(selectTaskId==rows[i].ID){
					$('#tab1_dg').datagrid('selectRow',i);
					getTaskMainPage(selectTaskId);
					flag = false;
					break;
				}
			}
			if(flag){
				getTaskMainPage(null);
			}
		}
		
	}
	
	function tableReload(){
		//$('#tab1_dg').datagrid('reload');
		var ranNumber=Math.random(); //IE缓存
		$('#tab1_dg').datagrid('load', {    
			ranNumber: ranNumber  
		});
	}
	
	function exportExcel1(){
		$("#export").submit();
	}
	function formatterDateFn1(value, rowData, index){//日期格式函数，5天内黄灯，延期红灯
		 if(value){
			 var today = new Date();//取今天的日期
			 var planStartDate = new Date(Date.parse(value.replace('-','/').replace('-','/'))); 
			 var cnt =parseInt((today - planStartDate ) / 1000 / 60 / 60 /24) ;
			 
			 if(cnt > 0){
				 return "<font color='red'>"+value+"</font>";
			 }else if(cnt >-5){
				 return "<font color='orange'>"+value+"</font>";
			 }else{
				 return value;
			 } 
		 }else{
			 return '';
		 }
		 
	}
	function formatterDateFn2(value, rowData, index){//日期格式函数，5天内黄灯，延期红灯
		 if(value){
			 var today = new Date();//取今天的日期
			 var planStartDate = new Date(Date.parse(value.replace('-','/').replace('-','/'))); 
			 var cnt =parseInt((today - planStartDate ) / 1000 / 60 / 60 /24) ;
			 
			 if(cnt > 0){
				 return "<font color='red'>"+value+"</font>";;
			 }else if(cnt >-5){
				 return "<font color='orange'>"+value+"</font>";;
			 }else{
				 return value;
			 } 
		 }else{
			 return '';
		 }
		 
	}
	
	function formatterTypeFn(value, rowData, index){
		var ret = '';
		if(value=='1'){
			ret = '<spring:message code="PLANTASK"/>';
		}else if(value=='2'){
			ret = '<spring:message code = "RISK"/>';
		}else if(value=='3'){
			ret = 'OPL';
		}else if(value=='4'){
			ret = '<spring:message code = "DECOMPOSE"/>';
		}
		 return ret;
	}
	
	function formatterStatusFn(value, rowData, index){
		var ret = "";
		if(value=='1'){
			ret = '<spring:message code="UNBEGUN"/>';
		}else if('2'==value){
			ret = '<spring:message code="ONGOING"/>';
		}else if('3'==value){
			ret = '<spring:message code="APPROVAL"/>';
		}else if('4'==value){
			ret = '<spring:message code="COMPLETE"/>';
		}else if('5'==value){
			ret = '<spring:message code="CANCEL"/>';
		}
		return ret;
	}
	
	function formatterChildFn(value, rowData, index){
		
		var subTask = '';
		if(value){
			subTask += value;
		}
		if(rowData.CHILD_SUM){
			subTask += '/' + rowData.CHILD_SUM;
		}
		return subTask;
	}
	
	function formatterAgginFlag(value, rowData, index){
		if(value&&value == '1'){
			return "Yes";
		}else{
			return "No";
		}
	}
	
	
	//将DateBox格式化为yyyy-mm-dd
	function formatterYMDFn(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+m+'-'+d;//格式化为yyyy-mm-dd
	}
	
	function parserYMDFn(my){
		var s = null;
		if(my){
			s = my.replace(/-/g, "/");//转换为yyyy/mm/dd否则报错
		}
		var t = Date.parse(s);
		if (!isNaN(t)){
			return new Date(t);
		} else {
			return new Date();
		}

	}
	
	
	/*分别为每个参数值对应的文本框赋值  */
	function exportExcel(data){
		var arrayType = $('#arrayType').combobox('getValues');//下拉
		var assignUserName = $('#assignUserName').val();
		var respUserName = $('#respUserName').val();
		var baseStartDate = $('#baseStartDate').val();
		var baseStartDate1 = $('#baseStartDate1').val();
		var baseEndDate = $('#baseEndDate').val();
		var baseEndDate1 = $('#baseEndDate1').val();
		var arraySerious =$('#arraySerious').combobox('getValues');//下拉
		var arrayPriority = $('#arrayPriority').combobox('getValues');//下拉
		var arrayProjSize = $('#arrayProjSize').combobox('getValues');//项目大小下拉
		//项目属性建索
		var sop = $('#sop').val();
		var sop1 = $('#sop1').val();
		var ph1 = $('#ph1').val();
		var ph2 = $('#ph2').val();
		var projO = $('#projO').val();
		var projB = $('#projB').val();
		var projCode = $('#projCode').val();
		var customerNumber = $('#customerNumber').val();
		var vehicle = $('#vehicle').val();
		var engine = $('#engine').val();
		var depts = '';
		if(data){
			if('1'=='${leader}'){
				depts = $('#cc').combotree('getText');
			}
		}
		if(arrayType.length>0){
			$('#harrayType').val(arrayType);
		}
		if(arraySerious.length>0){
			$('#harraySerious').val(arraySerious);
		}
		if(arrayPriority.length>0){
			$('#harrayPriority').val(arrayPriority);
		}
		if(arrayProjSize.length>0){
			$('#harrayProjSize').val(arrayProjSize);
		}
		if(ph1){
			$('#hph1').val(ph1);//ph1
		}
		if(ph2){
			$('#hph2').val(ph2);//ph2
		}
		if(projO){
			$('#hprodOcode').val(projO);//0 Number
		}
		if(projB){
			$('#hprodBcode').val(projB);//B Number
		}
		if(projCode){
			$('#hprojCode').val(projCode);//项目编号
		}
		if(customerNumber){
			$('#hcustomerNumber').val(customerNumber);//客户零件号
		}
		if(vehicle){
			$('#hvehicle').val(vehicle);//车型
		}
		if(engine){
			$('#hengine').val(engine);//发动机
		}
		if(depts){
			$('#hdepts').val(depts);
		}
		if(assignUserName){
			$('#hassignUserName').val(assignUserName);
		}
		if(respUserName){
			$('#hrespUserName').val(respUserName);
		}
		if(baseStartDate){
			$('#hbaseStartDate').val(baseStartDate);
		}
		if(baseEndDate){
			$('#hbaseEndDate').val(baseEndDate);
		}
		if(baseStartDate1){
			$('#hbaseStartDate1').val(baseStartDate1);
		}
		if(baseEndDate1){
			$('#hbaseEndDate1').val(baseEndDate1);
		}
		if(sop){
			$('#hsop').val(sop);
		}
		if(sop1){
			$('#hsop1').val(sop1);
		}
		var type = '${type}';
		if(type == '1'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			if(arraystatus.length>0){
				$('#harraystatus').val(arraystatus);
			}
			$('#huserId').val('${userId}');
		}else if(type == '2'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			if(arraystatus.length==0){
				arraystatus[0] = '1';
				arraystatus[1] = '2';
				arraystatus[2] = '3';
				$('#harraystatus').val(arraystatus);
			}
			if(arraystatus.length>0){
				$('#harraystatus').val(arraystatus);
			}
			$('#hassignUser').val('${userId}');
		}else if(type == '3'){
			$('#hstatus').val('1');
			$('#hrespUser').val('${userId}');
		}else if(type == '4'){
			var arraystatus = $('#arraystatus').combobox('getValues');//下拉
			$('#hrespUser').val('${userId}');
			if(arraystatus.length==0){
				arraystatus[0] = '2';
				arraystatus[1] = '3';
				$('#harraystatus').val(arraystatus);
			}
			if(arraystatus.length>0){
				$('#harraystatus').val(arraystatus);
			}
		}else{
			$('#hstatus').val('4');
			$('#hrespUser').val('${userId}');
		}
		exportExcel1();
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true" style="width:610px;">
			<div class="easyui-layout" data-options="fit:true">
				<!-- 检索区域 -->
				<div data-options="region:'north'" style="height:200px;padding:13px;" id="search_div_all">
					<!-- 普通的检索区域： -->
					<div id="search_div_general">
						<spring:message code="TASKNAMEANDNAME" var="TASKNAMEANDNAME"/>
						<input class="easyui-textbox" data-options="prompt:'${TASKNAMEANDNAME }'" name="searchName" style="width:200px;" id="searchName">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="generalSearchFn()">Search</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-reload"  onclick="generalResetFn()">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-filter" onclick="showDetailSearch()">More Search</a>
						<a href="#"  class="easyui-linkbutton" iconCls="icon-redo" onclick="exportExcel();">Export</a>
					</div>
					<!-- 高级检索区域：  -->
					<div id="search_div_detail" style="display: none;">
							<div id="float_img" class="fixed">
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="detailSearchFn()">Search</a>
								<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="detailResetFn()">Reset</a>
								<a href="#"  class="easyui-linkbutton" iconCls="icon-redo" onclick="exportExcel(1);">Export</a><br>
							</div>
							<form id="search_div_detail_from">
							<div style="margin-top:20px">
							<input class="easyui-textbox" data-options="prompt:'PH1'"  id="ph1" style="width:130px;">
							<input class="easyui-textbox" data-options="prompt:'PH2'"  id="ph2" style="width:130px;">
							<input class="easyui-textbox" data-options="prompt:'0 Number'"  id="projO" style="width:130px;">
							<input class="easyui-textbox" data-options="prompt:'B Number'"  id="projB" style="width:130px;"><br>
							</div>
							&nbsp;&nbsp;&nbsp;
							<spring:message code="PROJSIZE" var="PROJSIZE"/>
							<spring:message code="PROJCUSTOMERNUMBER" var="PROJCUSTOMERNUMBER"/>
							<spring:message code="PROJCODE" var="PROJCODE"/>
							<spring:message code="PROJVEHICLE" var="PROJVEHICLE"/>
							<spring:message code="PROJENGINE" var="PROJENGINE"/>
							<div style="margin-bottom:8px">
								<input class="easyui-textbox" data-options="prompt:'${PROJCODE }'"  id="projCode" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${PROJCUSTOMERNUMBER}'"  id="customerNumber" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${PROJVEHICLE }'"  id="vehicle" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${PROJENGINE}'"  id="engine" style="width:130px;"><br>
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="PROJSIZE"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arrayProjSize" style="width:100px;">
									<option value="3840A36F466747A7BD9C9EE7D6D43A1D">Large Project</option>
									<option value="594C59A353994E71B1DAD8334A9830DD">Middle Project</option>
									<option value="F9092EC2E5154213B6BC60C5C9F918FA">Small Project</option>
								</select>
							</div>
							<div style="margin-bottom:8px">
								SOP from<input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="sop">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								to<input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="sop1">
							</div>
							<hr/>
							<div style="margin-bottom:8px">
							<spring:message code="ASSIGN_USER_NAME" var="ASSIGN_USER_NAME"/>
							<spring:message code="RESP_USER_NAME" var="RESP_USER_NAME"/>
								<input class="easyui-textbox" data-options="prompt:'${ASSIGN_USER_NAME }'"  id="assignUserName" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${RESP_USER_NAME }'"  id="respUserName" style="width:130px;">
								<spring:message code="TASKTYPE"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arrayType" style="width:80px;">
									<option value="1"><spring:message code="PLANTASK"/></option>
									<option value="2">OPL</option>
									<option value="3"><spring:message code="RISK"/></option>
									<option value="4"><spring:message code="DECOMPOSE"/></option>
								</select>
							</div>
							<div style="margin-bottom:8px">
								<c:if test="${type == '1' }">
									<spring:message code="STATUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraystatus" style="width:100px;">
									<option value="1"><spring:message code="UNBEGUN"/></option>
									<option value="2"><spring:message code="ONGOING"/></option>
									<option value="3"><spring:message code="APPROVAL"/></option>
									<option value="4"><spring:message code="COMPLETE"/></option>
									<option value="5"><spring:message code="CANCEL"/></option>
									</select>
									&nbsp;&nbsp;&nbsp;
								</c:if>
								<c:if test="${type == '2' }">
									<spring:message code="STATUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraystatus" style="width:100px;">
									<option value="1"><spring:message code="UNBEGUN"/></option>
									<option value="2"><spring:message code="ONGOING"/></option>
									<option value="3"><spring:message code="APPROVAL"/></option>
									</select>
									&nbsp;&nbsp;&nbsp;
								</c:if>
								<c:if test="${type == '4' }">
									<spring:message code="STATUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraystatus" style="width:100px;">
									<option value="2"><spring:message code="ONGOING"/></option>
									<option value="3"><spring:message code="APPROVAL"/></option>
									</select>
									&nbsp;&nbsp;&nbsp;
								</c:if>
								
								<spring:message code="LEVEL"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arrayPriority" style="width:100px;">
									<option value="3001">High</option>
									<option value="3002">Medium</option>
									<option value="3003">Low</option>
								</select>
								&nbsp;&nbsp;&nbsp;
								<spring:message code="SERIOUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraySerious" style="width:100px;">
									<option value="6434">Blocker</option>
									<option value="6435">Critical</option>
									<option value="6436">Major</option>
									<option value="877CA8CF7C714FCC828AE21B39C81AD0">Normal</option>
									<option value="178CA7C290BA4F77B5DE635824EE8906">Minor</option>
								</select>
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="BASE_START_DATE1"/><input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="baseStartDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<spring:message code="BASE_START_DATE2"/><input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="baseStartDate1">
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="BASE_END_DATE1"/><input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="baseEndDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<spring:message code="BASE_END_DATE2"/><input class="easyui-datebox" data-options="formatter:formatterYMDFn,parser:parserYMDFn" style="width:180px" id="baseEndDate1">
							</div>
							<div id="deptSearch"></div>
							</form>
					</div>
				</div>
						
				<!-- 数据展示区 -->
				<!-- 全部任务 -->
				<c:if test="${1 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}',
																				method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn,onLoadSuccess:onLoadSuccessFn">
							<thead>
								<tr>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
									<th data-options="field:'NAME'" width="240" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'AGGIN_FLAG',align:'center',formatter:formatterAgginFlag" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'PROJ_CODE'" width="160" sortable="true"><spring:message code="PROJCODE"/></th>
									<th data-options="field:'APP_PROJ_INFO_PH1'" width="160" sortable="true">PH1</th>
									<th data-options="field:'APP_PROJ_INFO_PH2'" width="160" sortable="true">PH2</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_OCODE'" width="160" sortable="true">0 Number</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_BCODE'" width="160" sortable="true">B number</th>
									<th data-options="field:'APP_PROJ_INFO_SOP'" width="160" sortable="true">SOP</th>
									<th data-options="field:'APP_PROJ_INFO_PROJ_SIZE_NAME'" width="160" sortable="true"><spring:message code="PROJSIZE"/></th>
									<th data-options="field:'APP_PROJ_INFO_VEHICLE'" width="160" sortable="true"><spring:message code="PROJVEHICLE"/></th>
									<th data-options="field:'APP_PROJ_INFO_ENGINE'" width="160" sortable="true"><spring:message code="PROJENGINE"/></th>
									<th data-options="field:'APP_PROJ_INFO_CUSTOMER_NUMBER'" width="160" sortable="true"><spring:message code="PROJCUSTOMERNUMBER"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<!-- 我的发包 -->
				<c:if test="${2 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}&arraystatus=1,2,3',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn,onLoadSuccess:onLoadSuccessFn">
							<thead>
								<tr>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
									<th data-options="field:'NAME'" width="240" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'AGGIN_FLAG',align:'center',formatter:formatterAgginFlag" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'PROJ_CODE'" width="160" sortable="true"><spring:message code="PROJCODE"/></th>
									<th data-options="field:'APP_PROJ_INFO_PH1'" width="160" sortable="true">PH1</th>
									<th data-options="field:'APP_PROJ_INFO_PH2'" width="160" sortable="true">PH2</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_OCODE'" width="160" sortable="true">0 Number</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_BCODE'" width="160" sortable="true">B number</th>
									<th data-options="field:'APP_PROJ_INFO_SOP'" width="160" sortable="true">SOP</th>
									<th data-options="field:'APP_PROJ_INFO_PROJ_SIZE_NAME'" width="160" sortable="true"><spring:message code="PROJSIZE"/></th>
									<th data-options="field:'APP_PROJ_INFO_VEHICLE'" width="160" sortable="true"><spring:message code="PROJVEHICLE"/></th>
									<th data-options="field:'APP_PROJ_INFO_ENGINE'" width="160" sortable="true"><spring:message code="PROJENGINE"/></th>
									<th data-options="field:'APP_PROJ_INFO_CUSTOMER_NUMBER'" width="160" sortable="true"><spring:message code="PROJCUSTOMERNUMBER"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<!-- 新任务 -->
				<c:if test="${3 eq type }">	
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn,onLoadSuccess:onLoadSuccessFn">
							<thead>
								<tr>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
									<th data-options="field:'NAME'" width="240" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'AGGIN_FLAG',align:'center',formatter:formatterAgginFlag" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'PROJ_CODE'" width="160" sortable="true"><spring:message code="PROJCODE"/></th>
									<th data-options="field:'APP_PROJ_INFO_PH1'" width="160" sortable="true">PH1</th>
									<th data-options="field:'APP_PROJ_INFO_PH2'" width="160" sortable="true">PH2</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_OCODE'" width="160" sortable="true">0 Number</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_BCODE'" width="160" sortable="true">B number</th>
									<th data-options="field:'APP_PROJ_INFO_SOP'" width="160" sortable="true">SOP</th>
									<th data-options="field:'APP_PROJ_INFO_PROJ_SIZE_NAME'" width="160" sortable="true"><spring:message code="PROJSIZE"/></th>
									<th data-options="field:'APP_PROJ_INFO_VEHICLE'" width="160" sortable="true"><spring:message code="PROJVEHICLE"/></th>
									<th data-options="field:'APP_PROJ_INFO_ENGINE'" width="160" sortable="true"><spring:message code="PROJENGINE"/></th>
									<th data-options="field:'APP_PROJ_INFO_CUSTOMER_NUMBER'" width="160" sortable="true"><spring:message code="PROJCUSTOMERNUMBER"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<!-- 进行中 -->
				<c:if test="${4 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&arraystatus=2,3',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn,onLoadSuccess:onLoadSuccessFn">
							<thead>
								<tr>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
									<th data-options="field:'NAME'" width="240" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'AGGIN_FLAG',align:'center',formatter:formatterAgginFlag" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'PROJ_CODE'" width="160" sortable="true"><spring:message code="PROJCODE"/></th>
									<th data-options="field:'APP_PROJ_INFO_PH1'" width="160" sortable="true">PH1</th>
									<th data-options="field:'APP_PROJ_INFO_PH2'" width="160" sortable="true">PH2</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_OCODE'" width="160" sortable="true">0 Number</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_BCODE'" width="160" sortable="true">B number</th>
									<th data-options="field:'APP_PROJ_INFO_SOP'" width="160" sortable="true">SOP</th>
									<th data-options="field:'APP_PROJ_INFO_PROJ_SIZE_NAME'" width="160" sortable="true"><spring:message code="PROJSIZE"/></th>
									<th data-options="field:'APP_PROJ_INFO_VEHICLE'" width="160" sortable="true"><spring:message code="PROJVEHICLE"/></th>
									<th data-options="field:'APP_PROJ_INFO_ENGINE'" width="160" sortable="true"><spring:message code="PROJENGINE"/></th>
									<th data-options="field:'APP_PROJ_INFO_CUSTOMER_NUMBER'" width="160" sortable="true"><spring:message code="PROJCUSTOMERNUMBER"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<!-- 已完成 -->
				<c:if test="${5 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn,onLoadSuccess:onLoadSuccessFn">
							<thead>
								<tr>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
									<th data-options="field:'NAME'" width="240" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'AGGIN_FLAG',align:'center',formatter:formatterAgginFlag" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'PROJ_CODE'" width="160" sortable="true"><spring:message code="PROJCODE"/></th>
									<th data-options="field:'APP_PROJ_INFO_PH1'" width="160" sortable="true">PH1</th>
									<th data-options="field:'APP_PROJ_INFO_PH2'" width="160" sortable="true">PH2</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_OCODE'" width="160" sortable="true">0 Number</th>
									<th data-options="field:'APP_PROJ_INFO_PROD_BCODE'" width="160" sortable="true">B number</th>
									<th data-options="field:'APP_PROJ_INFO_SOP'" width="160" sortable="true">SOP</th>
									<th data-options="field:'APP_PROJ_INFO_PROJ_SIZE_NAME'" width="160" sortable="true"><spring:message code="PROJSIZE"/></th>
									<th data-options="field:'APP_PROJ_INFO_VEHICLE'" width="160" sortable="true"><spring:message code="PROJVEHICLE"/></th>
									<th data-options="field:'APP_PROJ_INFO_ENGINE'" width="160" sortable="true"><spring:message code="PROJENGINE"/></th>
									<th data-options="field:'APP_PROJ_INFO_CUSTOMER_NUMBER'" width="160" sortable="true"><spring:message code="PROJCUSTOMERNUMBER"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
			</div>
		</div>
		<div id="tab1_right" data-options="region:'east',split:true" style="width:610px;overflow: hidden;"></div>
	</div>
	<form id="export" action="${path}/app/userTaskPlatfrom/export.action" method="POST">
	
		<input type="hidden" id="huserId" name="userId"/>
		<input type="hidden" id="hstatus" name="status"/>
		<input type="hidden" id="hassignUser" name="assginUser"/>
		<input type="hidden" id="hrespUser" name="respUser"/>
		<input type="hidden" id="hph1" name="appProjInfo.ph1"/>
		<input type="hidden" id="hph2" name="appProjInfo.ph2"/>
		<input type="hidden" id="hprodOcode" name="appProjInfo.prodOcode" />
		<input type="hidden" id="hprodBcode" name="appProjInfo.prodBcode"/>
		<input type="hidden" id="hprojSize" name="appProjInfo.projSize"/>
		<input type="hidden" id="hsop" name="appProjInfo.sop"/>
		<input type="hidden" id="hsop1" name="sop1"/>
		<input type="hidden" id="hvehicle" name="appProjInfo.vehicle"/>
		<input type="hidden" id="hcustomerNumber" name="appProjInfo.customerNumber"/>
		<input type="hidden" id="hengine" name="appProjInfo.engine" />
		<input type="hidden" id="hprojCode" name="projCode" />
		<input type="hidden" id="harrayProjSize" name="arrayProjSize" />
		
		<input type="hidden" id="harraystatus" name="arraystatus" />
		<input type="hidden" id="harraySerious" name="arraySerious" />
		<input type="hidden" id="harrayPriority" name="arrayPriority" />
		<input type="hidden" id="harrayType" name="arrayType" />
		<input type="hidden" id="hbaseEndDate" name="baseEndDate" />
		<input type="hidden" id="hbaseEndDate1" name="baseEndDate1" />
		<input type="hidden" id="hbaseStartDate" name="baseStartDate" />
		<input type="hidden" id="hbaseStartDate1" name="baseStartDate1" />
		<input type="hidden" id="hrespUserName" name="respUserName" />
		<input type="hidden" id="hassignUserName" name="assignUserName" />
		<input type="hidden" id="hdepts" name="depts" />
	</form>
</body>
</html>