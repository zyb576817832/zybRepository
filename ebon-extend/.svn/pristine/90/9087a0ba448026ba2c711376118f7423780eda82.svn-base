<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的工时-工时详情</title>
<script type="text/javascript">

	
	function updateTaskHours(){
		
		var hours = $("#registHours").val();
		if(hours > 24){
			$.messager.alert('message','Failed，Do not exceed 24 hours'');
			return;
		}
		if('${registTaskIds}'){//如果有任务则保存到任务的工时上
			saveHours();
		}else{
			var registHours = $('#registHours').val();
			var url = '${path}/app/userHours/updateAgain.action?registTaskIds=${registTaskIds}&noTaskUserhourId=${noTaskUserhour.id}&registDate=${registDate}&userId=${userId}&registHours=' + registHours; 
			$.post(url, function(data){
				$.messager.alert('Message','success');
				var topUrl = window.document.referrer +'&registDate=${registDate}';
				parent.location.href = topUrl;
			},'json');
		}
	}
	
	function tableReload(){
		var ranNumber=Math.random(); //IE缓存
		$('#dg').datagrid('load', {    
			ranNumber: ranNumber  
		});
	}
	
	var currentIndex;
	function editHours(rowIndex, field, value){
		if(currentIndex||0==currentIndex){
			endEdit(currentIndex);
		}
		$('#dg').datagrid('beginEdit', rowIndex);
		currentIndex=rowIndex;
	}
	
	//结束编辑的时候为总工时赋值
	function endEdit(rowIndex){
		$('#dg').datagrid('endEdit', rowIndex);
		var rows = $('#dg').datagrid('getRows'); 
		var userHours=Number(0);
		for(var i=0; i<rows.length; i++){
			userHours += Number(rows[i].REGIST_HOURS);
		}
		 $('#registHours').val(userHours);
	}
	
	function saveHours(){
		var rows = $('#dg').datagrid('getRows'); 
		var userHoursDatas='';
		for(var i=0; i<rows.length; i++){
			var userHoursData = rows[i].ID + ':'+rows[i].REGIST_HOURS + ',';
			userHoursDatas+=userHoursData;
			
		}
		var url = '${path}/app/userHours/updateByTaskIds.action?userHoursDatas=' + userHoursDatas; 
		$.post(url, function(data){
			$.messager.alert('提示','操作成功');
			var topUrl = window.document.referrer +'&registDate=${registDate}';
			parent.location.href = topUrl;
		},'json');
	}
	
	function seleceRowFn(rowIndex, rowData){
		endEdit(rowIndex);
		if(currentIndex||0==currentIndex){
			endEdit(currentIndex);
		}
	}
	
	function averageTaskHours(){
		var registHours = $('#registHours').val();
		var reg = /^\d+(\.\d{1,2})?$/;
		if (!reg.test(registHours)) {
			$.messager.alert('提示','请输入合法数字');
			return false;
		}else{
			if('${registTaskIds}'){//如果有任务则自定平均分配到相应任务上
				var rows = $('#dg').datagrid('getRows');
				var averageHours = Math.round(registHours/rows.length);
				for(var i=0; i<rows.length; i++){
					$('#dg').datagrid('updateRow',{
						index: i,
						row: {
							REGIST_HOURS: averageHours
						}
					});
				}
			}
		}
		
	}
	function keyOnClick(e){
		var theEvent = window.event || e;
	    var code = theEvent.keyCode || theEvent.which;
	    if (code==13) {  //回车键的键值为13
		    if(currentIndex||0==currentIndex){
		    	endEdit(currentIndex);
		    	currentIndex = null;
		    }else{
		    	averageTaskHours();
		    }
	    }
	}
</script>
</head>
<body onkeydown="keyOnClick(event);">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'"  style="margin: 2px 5px; height: 20px;">
			<table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;table-layout:fixed">
				<tr>
					<td colspan="2" width="350px;"><h2>${registDate }</h3></td>
				</tr>
				<tr>
					<td><spring:message code='EFFT'/>:</td>
					<c:if test="${sumTaskHours ne null }">
						<td><input class="easyui-validatebox"  data-options="required:true" onchange="averageTaskHours()" id="registHours" value="${sumTaskHours }">h</td>
					</c:if>
					<c:if test="${sumTaskHours eq null  }">
						<td><input class="easyui-validatebox"  data-options="required:true" id="registHours" value="${noTaskUserhour.registHours }"></td>
					</c:if>
					<td colspan="2"><input type="button" value="Submit" onclick="updateTaskHours()" style="width:80px"></td>
				</tr>
			</table>
		</div>
		<c:if test="${registTaskIds ne null}">
			<div data-options="region:'south',split:true" style="height:310px;">
				<div class="easyui-layout" data-options="split:true" style="width:100%;height:100%;">
					<div data-options="region:'center',split:true">
						<table id="dg" class="easyui-datagrid" title="<spring:message code='EFFDETAIL'/>" data-options="fit:true,singleSelect:true,collapsible:true,url:'${path}/app/userHours/getVList.action?registDate=${registDate}&userId=${userId}&randomNum=${randomNum}',method:'get',onDblClickCell:editHours,onSelect:seleceRowFn">
							<thead>
								<tr>
									<th data-options="field:'TASK_NAME'" width="200px;"><spring:message code='NAME'/></th>
									<th data-options="field:'REGIST_HOURS',editor:{type:'numberbox',options:{required:true}}" width="300px;"><spring:message code='EFFT'/></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>