<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户选择</title>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">

	var hashMap = new HashMap();//存放选中人员信息
	$(function(){
		//填充已选择的人员
		if('${userIds}'){
			var paramUserNames = '${userNames}';
			var paramUserIds = '${userIds}';
			var userNames = paramUserNames.split(',');
			var userIds = paramUserIds.split(',');
			for(var i = 0; i < userNames.length; i++){
				if(userNames[i].length  > 0){
					hashMap.put(userIds[i], userNames[i]);
				}
			};
			loadData();
		}
	});
	
	function onSelectFn(data){
		hashMap.remove(data.employeeId);
		hashMap.put(data.employeeId, data.actualName);
		if('0'=='${isMultiple}'){//如果是单选
			var ids = hashMap.keySet();
			if(ids.length>1){
				hashMap.remove(data.employeeId);
				alert("only choose one");
				return;
			}
		}
		loadData();
	}
	function loadData(){
		var json = getJsonFromMap(hashMap);
		var obj = eval('(' + json + ')');
		$('#dg').datagrid("loadData",obj);
	}
	
	function getJsonFromMap(hashMap){
		var ids = hashMap.keySet();
		var json = '{"rows":[';
		for(var i=0;i<ids.length;i++){
			json += '{';
			json += '"employeeId":"' + ids[i] + '","actualName":"' + hashMap.get(ids[i]);
			if(i==ids.length-1){
				json += '"}';
			}else{
				json += '"},';
			}
		}
		json += ']}';
		return json;
	}
	
	var userIds;//最终保存的用户IDS
	var userNames;//最终显示的用户名称
	function setSubmitData(){
		userIds ='';
		userNames ='';
		var ids = hashMap.keySet();
		for(var i=0;i<ids.length;i++){
			if(i==ids.length-1){
				userIds += ids[i];
				userNames += hashMap.get(ids[i]);
			}else{
				userIds += ids[i] +',';
				userNames += hashMap.get(ids[i])+',';
			}
		};
	}
	
	function loadDataBySearchFn(newValue, oldValue){
		if(newValue){
			if((newValue.length==32||newValue.length==36)){//如果不加这句选择人员后会根据ID作为查询条件检索
			}else{
				$('#respUser').combobox('reload','${path}/app/userTaskPlatfrom/getComboListBySearch.action?search=' + newValue);  
			}
		}
	}
	
	function formatterCZ(value, row, index){
		var s = '<a href="#" onclick="javascript:deleteRow(\''+ row.employeeId +'\' )">Delete</a>';
		return s;
	}
	
	function deleteRow(employeeId){
		hashMap.remove(employeeId);
		loadData();
	}
	function submitForm(){
		setSubmitData();//为提交数据赋值
		var showObj = parent.opener.document.getElementById("USER_NAME");
		var valueObj = parent.opener.document.getElementById("USER_ID");
		valueObj.value = userIds;		
		showObj.value = userNames;
		parent.opener.DeptPersion_Choose_Callback(showObj, valueObj);
		parent.window.close(); 
	}
	
</script>
</head>
<body>
	<div style="margin:2px 0;"></div>
	<div class="easyui-layout" style="width:500px;height:350px;">
		<div data-options="region:'north'" style="height:50px">
			<input id="respUser" class="easyui-combobox" style="width:280px;" name="respUser" 
				data-options="valueField:'employeeId',textField:'actualName',onSelect:onSelectFn,onChange:loadDataBySearchFn"></input>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >Save</a>
		</div>
		<div data-options="region:'center',title:'已选人员'">
			<table id="dg" class="easyui-datagrid" data-options="fit:true">
				<thead>
					<tr>
						<th data-options="field:'employeeId',hidden:true"></th>   
						<th data-options="field:'actualName'" width="400">人员列表</th>
						<th data-options="field:'1',align:'center',formatter:formatterCZ" width="50px">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>