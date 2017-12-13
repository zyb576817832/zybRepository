<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<title>我的工时-添加工时</title>
<script type="text/javascript">
	function submitForm(){
		var hours = $("#registHours").val();
		if(hours > 24){
			$.messager.alert('message','failed，Do not exceed 24 hours');
			return;
		}
		$('#ff').form('submit', {    
		    url:'${path}/app/userHours/add.action?userId=${userId}',    
		    success:function(data){    
		    	$.messager.alert('Message','success');
		    	var topUrl = window.document.referrer +'&registDate=${registDate}';
				parent.location.href = topUrl;
		    	parent.onSelectDateFn(null, '${registDate}');
		    }    
		}); 
	}
	
	function aotoRegist(){
		var url = '${path}/app/userHours/aotoRegist.action?taskIds=${taskIds}&userId=${userId}&registDate=${registDate}';
		$.post(url, function(data){
			$.messager.alert('Message','success');
			var topUrl = window.document.referrer +'&registDate=${registDate}';
			parent.location.href = topUrl;
	    	parent.onSelectDateFn(null, '${registDate}');
		},'json');
	}
</script>
</head>
<body>
	<h2><spring:message code="EFF"/></h2>
	<p><spring:message code="EFFDESC"/></p>
	<form id="ff" method="post">
		<input name="taskIds" type="hidden" value="${taskIds}">
		<div style="margin-bottom:20px">
			<spring:message code="EFFDATE"/>：<input class="easyui-datebox" name="registDate" style="width:200px;" data-options="required:true" value="${registDate}">
		</div>
		<div style="margin-bottom:20px">
			<spring:message code="EFFEFFORT"/>：<input id="registHours" class="easyui-numberbox" name="registHours" style="width:200px;" data-options="required:true">
		</div>
	</form>
	<div style="text-align:center;padding:5px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="aotoRegist()">模拟定时</a>
	</div>
</body>
</html>