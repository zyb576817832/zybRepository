<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta2.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全部任务页面</title>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">
	var selectedRow;//全局变量,选中行任务
	$(function(){
		//高级检索区默认隐藏
		serarchDivHideFn();
		
		$('#cc').combotree({    
		    url: '${path}/app/userTaskPlatfrom/deptComboxTree?userId=${userId}',    
		    loadFilter : function(rows){
		    	return convert(rows);
		    }
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
		var arraystatus = $('#arraystatus').combobox('getValues');//下拉
		var arraySerious =$('#arraySerious').combobox('getValues');//下拉
		var arrayPriority = $('#arrayPriority').combobox('getValues');//下拉
		var leader = '${leader}';
		var depts = '';
		if(leader != ''){
			depts = $('#cc').combotree('getText');
		}
		var projCode = $('#projCode').val();
		var paramsUrl = '';
		if(arrayType.length>0){
			paramsUrl+='&arrayType=' + arrayType;
		}if(arraystatus.length>0){
			paramsUrl+='&arraystatus=' + arraystatus;
		}
		if(arraySerious.length>0){
			paramsUrl+='&arraySerious=' + arraySerious;
		}
		if(arrayPriority.length>0){
			paramsUrl+='&arrayPriority=' + arrayPriority;
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
		var ranNumber=Math.random(); //IE缓存
		paramsUrl+='&ranNumber=' + ranNumber;
		/* var dgUrl = $('#tab1_dg').datagrid('options').url+paramsUrl; */
		var dgUrl = '';
		var f = '${type}';
		if(f == '1'){
			dgUrl = '${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}'+paramsUrl;
		}else if(f == '2'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}'+paramsUrl;
		}else if(f == '3'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1'+paramsUrl;
		}else if(f == '4'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=2,3'+paramsUrl;
		}else{
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4'+paramsUrl;
		}
		$.post(dgUrl,{titles:arrayPriority},function(data){
			$('#tab1_dg').datagrid('loadData',data);
		},'json');
		$('#tab1_dg').datagrid({url:dgUrl});//防止下一页无查询参数 QX 20170026
	}
	
	function detailResetFn(){
		$('#search_div_detail_from').form("clear");
		
		var f = '${type}';
		var dgUrl = '';
		if(f == '1'){
			dgUrl = '${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}';
		}else if(f == '2'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}';
		}else if(f == '3'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1';
		}else if(f == '4'){
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=2,3';
		}else{
			dgUrl = '${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4';
		}
		$.post(dgUrl,function(data){
			$('#tab1_dg').datagrid('loadData',data);
		},'json');
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
	
	function tableReload(){
		//$('#tab1_dg').datagrid('reload');
		var ranNumber=Math.random(); //IE缓存
		$('#tab1_dg').datagrid('load', {    
			ranNumber: ranNumber  
		});
	}
	
	function exportExcel(){
		$("#export").submit();
	}
	function formatterDateFn1(value, rowData, index){//日期格式函数，5天内黄灯，延期红灯
		
		 var today = new Date();//取今天的日期
		 var planStartDate = new Date(Date.parse(value.replace('-','/').replace('-','/'))); 
		 var cnt =parseInt((today - planStartDate ) / 1000 / 60 / 60 /24) ;
		 
		 if(cnt > 0){
			 return value;
		 }else if(cnt >-5){
			 return "<font color='orange'>"+value+"</font>";
		 }else{
			 return value;
		 }
	}
	function formatterDateFn2(value, rowData, index){//日期格式函数，5天内黄灯，延期红灯
		
		 var today = new Date();//取今天的日期
		 var planStartDate = new Date(Date.parse(value.replace('-','/').replace('-','/'))); 
		 var cnt =parseInt((today - planStartDate ) / 1000 / 60 / 60 /24) ;
		 
		 if(cnt > 0){
			 return "<font color='red'>"+value+"</font>";;
		 }else if(cnt >-5){
			 return value;
		 }else{
			 return value;
		 }
	}
	
	function formatterTypeFn(value, rowData, index){
		var ret = '';
		if(value=='1'){
			ret = '<spring:message code="PLANTASK"/>';
		}else if(value=='2'){
			ret = '<spring:message code = "RISK"/>';
		}else if(value=='3'){
			ret = 'OPL'
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true">
			<div class="easyui-layout" data-options="fit:true">
				<!-- 检索区域 -->
				<div data-options="region:'north'" style="height:170px;padding:13px;" id="search_div_all">
					<!-- 普通的检索区域： -->
					<div id="search_div_general">
						<spring:message code="TASKNAMEANDNAME" var="TASKNAMEANDNAME"/>
						<input class="easyui-textbox" data-options="prompt:'${TASKNAMEANDNAME }'" name="searchName" style="width:200px;" id="searchName">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="generalSearchFn()">Search</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-reload"  onclick="generalResetFn()">Reset</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-filter" onclick="showDetailSearch()">More Search</a>
						<a href="#"  class="easyui-linkbutton" iconCls="icon-redo" onclick="exportExcel('${userId}')">Export</a>
					</div>
					<!-- 高级检索区域：  -->
					<div id="search_div_detail">
							<form id="search_div_detail_from">
							<div style="margin-bottom:8px">
							<spring:message code="ASSIGN_USER_NAME" var="ASSIGN_USER_NAME"/>
							<spring:message code="RESP_USER_NAME" var="RESP_USER_NAME"/>
							<spring:message code="PROJECTNO" var="PROJECTNO"/>
								<input class="easyui-textbox" data-options="prompt:'${ASSIGN_USER_NAME }'"  id="assignUserName" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${RESP_USER_NAME }'"  id="respUserName" style="width:130px;">
								<input class="easyui-textbox" data-options="prompt:'${PROJECTNO }'"  id="projCode" style="width:130px;">&nbsp;
								<spring:message code="TASKTYPE"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arrayType" style="width:120px;">
									<option value="1"><spring:message code="PLANTASK"/></option>
									<option value="2">OPL</option>
									<option value="3"><spring:message code="RISK"/></option>
									<option value="4"><spring:message code="DECOMPOSE"/></option>
								</select>
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="STATUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraystatus" style="width:130px;">
									<option value="1"><spring:message code="UNBEGUN"/></option>
									<option value="2"><spring:message code="ONGOING"/></option>
									<option value="3"><spring:message code="APPROVAL"/></option>
									<option value="4"><spring:message code="COMPLETE"/></option>
									<option value="5"><spring:message code="CANCEL"/></option>
								</select>
								&nbsp;&nbsp;&nbsp;
								<spring:message code="LEVEL"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arrayPriority" style="width:130px;">
									<option value="3001">High</option>
									<option value="3002">Medium</option>
									<option value="3003">Low</option>
								</select>
								&nbsp;&nbsp;&nbsp;
								<spring:message code="SERIOUS"/>:<select class="easyui-combobox" data-options="multiple:true,value:''" id="arraySerious" style="width:130px;">
									<option value="6434">Blocker</option>
									<option value="6435">Critical</option>
									<option value="6436">Major</option>
									<option value="877CA8CF7C714FCC828AE21B39C81AD0">Normal</option>
									<option value="178CA7C290BA4F77B5DE635824EE8906">Minor</option>
								</select>
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="BASE_START_DATE1"/><input class="easyui-datebox" style="width:180px" id="baseStartDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<spring:message code="BASE_START_DATE2"/><input class="easyui-datebox" style="width:180px" id="baseStartDate1">
							</div>
							<div style="margin-bottom:8px">
								<spring:message code="BASE_END_DATE1"/><input class="easyui-datebox" style="width:180px" id="baseEndDate">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<spring:message code="BASE_END_DATE2"/><input class="easyui-datebox" style="width:180px" id="baseEndDate1">
							</div>
							<c:if test="${leader =='1' }">
							    <spring:message code="DEPTHEAD"/>：<select id="cc" class="easyui-combotree" style="width:200px;" multiple style="width:200px;"></select>
							</c:if>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="detailSearchFn()">Search</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="detailResetFn()">Reset</a>
						<a href="#"  class="easyui-linkbutton" iconCls="icon-redo" onclick="exportExcel('${userId}')">Export</a>
							</form>
					</div>
				</div>
						
				<!-- 数据展示区 -->
				<c:if test="${1 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getAllByUserId.action?randomNum=${randomNum}&userId=${userId}',
																				method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:50,onSelect:tab1_dg_selectFn">
							<thead>
								<tr>
									<th data-options="field:'NAME'" width="160" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" width="130" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" width="130" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'RESP_USER_NAME',align:'center'"width="160" sortable="true"><spring:message code="RESP_USER_NAME"/></th>
									<th data-options="field:'RESP_DEPT',align:'center'" sortable="true"><spring:message code="RESP_DEPT"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'TYPE',align:'center',formatter:formatterTypeFn" width="130" sortable="true"><spring:message code="BASETYPE"/></th>
									<th data-options="field:'PARENT_NAME',align:'center'" width="130" sortable="true"><spring:message code="PARENTUSERTASK"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="AGGINFLAG"/></th>
									<th data-options="field:'SERIOUS_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASESERIOUS"/></th>
									<th data-options="field:'PRIORITY_NAME',align:'center'" width="130" sortable="true"><spring:message code="BASELEVEL"/></th>
									<th data-options="field:'SOURCE',align:'center'" width="130" sortable="true"><spring:message code="BASESOURCE"/></th>
									<th data-options="field:'ACT_START_DATE',align:'center'" width="130" sortable="true"><spring:message code="ACTSTART"/></th>
									<th data-options="field:'ACT_END_DATE',align:'center'" width="130" sortable="true"><spring:message code="ACTEND"/></th>
									<th data-options="field:'ACT_HOURS',align:'center'" width="130" sortable="true"><spring:message code="ACTHOURS"/></th>
									<th data-options="field:'CHILD_UNDO',align:'center', formatter:formatterChildFn" width="130" sortable="true"><spring:message code="CHILDTASK"/></th>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn" sortable="true"><spring:message code="STATUS"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<c:if test="${2 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&assginUser=${userId}',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn">
							<thead>
								<tr>
									<th data-options="field:'NAME'" width="180" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn"width="60" sortable="true"><spring:message code="STATUS"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<c:if test="${3 eq type }">	
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=1',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:50,onSelect:tab1_dg_selectFn">
							<thead>
								<tr>
									<th data-options="field:'NAME'" width="180" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn"width="60" sortable="true"><spring:message code="STATUS"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<c:if test="${4 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&arraystatus=2,3',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:10,onSelect:tab1_dg_selectFn">
							<thead>
								<tr>
									<th data-options="field:'NAME'" width="180" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn"width="60" sortable="true"><spring:message code="STATUS"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
				<c:if test="${5 eq type }">		
					<div data-options="region:'center'">
						<table id="tab1_dg" class="easyui-datagrid" data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&respUser=${userId}&status=4',method:'get',border:false,singleSelect:true,fit:true,
						fitColumns:false,pagination:true,striped:true,rownumbers:true,pageSize:50,onSelect:tab1_dg_selectFn">
							<thead>
								<tr>
									<th data-options="field:'NAME'" width="180" sortable="true"><spring:message code="NAME"/></th>
									<th data-options="field:'BASE_START_DATE',align:'center',formatter:formatterDateFn1" sortable="true"><spring:message code="BASE_START_DATE"/></th>
									<th data-options="field:'BASE_END_DATE',align:'center',formatter:formatterDateFn2" sortable="true"><spring:message code="BASE_END_DATE"/></th>
									<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="130" sortable="true"><spring:message code="ASSIGN_USER_NAME"/></th>
									<th data-options="field:'STATUS',align:'center', formatter:formatterStatusFn"width="60" sortable="true"><spring:message code="STATUS"/></th>
								</tr>
							</thead>
						</table>
					</div>
				</c:if>
			</div>
		</div>
		<div id="tab1_right" data-options="region:'east'" style="width:710px;"></div>
	</div>
	<form id="export" action="${path}/app/userTaskPlatfrom/export.action">
		<input type="hidden" name="userId" value="${userId }">
	</form>
</body>
</html>