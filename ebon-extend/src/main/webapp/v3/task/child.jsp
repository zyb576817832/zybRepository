<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<title>子任务列表页面</title>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">

	function formatterCZ(value, row, index){
		if('${type}' == '4'){
			if('1' == row.STATUS){
				var s = '<a href="#" onclick="javascript:deleteTask(\''+ row.ID +'\' )">Delete</a>';
			}
		}
		/* var s = '<img src=\"${path }/themes/easyui1.3.2/icons/cancel.png\" onclick="javascript:deleteTask(\''+ row.ID +'\' )"/>'; */
		return s;
	}
	var appStatus  = '${appStatus}';
	function deleteTask(rowId){
		if(appStatus == '4'){
			$.messager.alert('Message','The task has been completed');
			return;
		}
		$.messager.confirm('Delete','Confirm to delete?',function(r){
			if (r){
				$.post('${path}/app/userTaskPlatfrom/delete.action?userId=${userId}&id=' + rowId, function(data){
					$.messager.alert('提示','success');
					 tableReload();
				},'json');
			}
		});
	}
	
	function submitForm(){
		var newValue = $('#respUser').combobox('getValue');
		if(newValue){
			if(newValue.length==36||newValue.length==32){
				
			}else{
				$.messager.alert('Message','Please Choose Person');
				return;
			}
		}else{
			$.messager.alert('Message','Please Choose Person');
			return;
		}
		$('#ff').form('submit',{
			url: '${path }/app/userTaskPlatfrom/insertChild.action?',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(data){
				var result = eval('('+data+')');
				if (result.code=='0'){
					$.messager.alert('Message','failed');
				} else {
					$('#w').window('close');  // 关闭选择对话框
					tableReload();
				}
			}
		});
	}
	
	function tableReload(){
		var ranNumber=Math.random(); //IE缓存
		$('#dg').datagrid('load', {    
			ranNumber: ranNumber  
		});
	}
	
	function clearForm(){
		$('#ff').form('clear');
	}
	
	$(function(){
		//选人注册change事件
		$('#respUser').combobox({
			onChange : loadDataBySearchFn,
			valueField:'employeeId',
			textField:'actualName',	
			onSelect : onSelectFn
		});
		
		var type = '${type}';
	    if(type != '4'){
		    $(".perssionClass").css("visibility","hidden");
		}
	});
	
	function onSelectFn(data){
		if('${assginUser}'==data.employeeId){
			$.messager.alert('警告','不能选择父任务发包人'); 
			$('#respUser').combobox('setText',null);
		}
	}
	
	function loadDataBySearchFn(newValue, oldValue){
		if(newValue){
			if((newValue.length==32||newValue.length==36)){
			}else{
				$('#respUser').combobox('reload','${path}/app/userTaskPlatfrom/getComboListBySearch.action?search=' + newValue);  
			}
		}
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
	
	//planstart默认设置为today
	function formatterDate(date){
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day;

	}
	
	window.onload = function(){ 
		$('#baseStartDate').datebox('setValue', formatterDate(new Date()));
	}
	
	function openWindow(data){
		$('#w').window('open');
		$('#name').val('');
		$('#respUser').combobox('setValue','');
		$('#hours').val('');
		$('#baseStartDate').datebox('setValue', '');
		$('#baseEndDate').datebox('setValue', '');
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north'" style="height:35px;">
			<a href="javascript:void(0)" class="easyui-linkbutton perssionClass" onclick="openWindow();"><spring:message code='DECOADD'/></a>
		</div>
		<div id="w" class="easyui-window" data-options="closed:true" title="<spring:message code='DECOADDTASK'/>" style="width:400px;height:250px;top: 10px;">
			<form id=ff method="post">
				<input name="parentId" value="${taskId}" type="hidden">
				<input name="userId" value="${userId}" type="hidden">
				<table>
					<tr>
						<td><spring:message code='DECONAME'/></td>
						<td><input id="name"  class="easyui-validatebox" style="width:280px" data-options="required:true"  name="name" ></input></td>
					</tr>
					<tr>
						<td><spring:message code='DECOHOURS'/></td>
						<td><input id="hours" class="easyui-validatebox" style="width:280px" name="planHours" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td><spring:message code='DECORESP'/></td>
						<td><input id="respUser" class="easyui-combobox" style="width:280px" name="respUser" data-options="required:true"></input></td>
					</tr>
					<tr>
						<td><spring:message code='DECOSTARTIME'/></td>
						<td><input id="baseStartDate" class="easyui-datebox" style="width:280px" name="baseStartDate" data-options="required:true,panelWidth:270"></input></td>
					</tr>
					<tr>
						<td><spring:message code='DECOSTOPTIME'/></td>
						<td><input id = "baseEndDate" class="easyui-datebox" name="baseEndDate" style="width:280px" data-options="required:true,panelWidth:270"></input></td>
					</tr>
				</table>
			</form>
			 <div style="text-align:center;padding:5px 0">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >Submit</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" >Clear</a>
				</div>
		</div>
		<div data-options="region:'center'">
			<table id="dg" class="easyui-datagrid"
				data-options="url:'${path}/app/userTaskPlatfrom/getVList.action?randomNum=${randomNum}&parentId=${taskId}',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true,pagination:true">
				<thead>
					<tr>
						<th data-options="field:'NAME'" width="20%"><spring:message code='DECONAME'/></th>
						<th data-options="field:'STATUS',formatter:formatterStatusFn" width="10%" sortable="true"><spring:message code='DECOSTATUS'/></th>
						<th data-options="field:'BASE_START_DATE',align:'center'" width="20%" sortable="true"><spring:message code='DECOSTART'/></th>
						<th data-options="field:'BASE_END_DATE',align:'center'" width="20%" sortable="true"><spring:message code='DECOFINISH'/></th>
						<th data-options="field:'RESP_USER_NAME',align:'center'" width="35%" sortable="true"><spring:message code='DECORESP'/></th>
						<th data-options="field:'ASSIGN_USER_NAME',align:'center'" width="35%" sortable="true"><spring:message code='DECOORIGINATOR'/></th>
						<th data-options="field:'1',align:'center',formatter:formatterCZ" width="10%" sortable="true"><spring:message code='DECOOPER'/></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>