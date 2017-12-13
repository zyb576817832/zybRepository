<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人统一待办主页面</title>
<script type="text/javascript">
	$(function(){
		var type ='${type}';
		if(type){
			$('#tabs').tabs('select',Number(type.substring(0,1)));//如果有多个只取第一个
		}
	});
	
	function selectFn(title,index){
		/* window.frames[index].tableReload(); */
	}
</script>
</head>
<body>
	<div id="tabs" class="easyui-tabs" data-options="fit:true,onSelect:selectFn">
	   	<div id="tab3" title="<spring:message code='NEWTASK'/>" data-options="closable:true">
			<iframe name="0" src="${path}/v3/app/menu/task/getListPage.action?type=3&userId=${userId}" style="width:98%;height:98%;"></iframe>
	    </div>
	    <div id="tab4" title="<spring:message code='ONGOINGTASK'/>" data-options="closable:true">
			<iframe name="1" src="${path}/v3/app/menu/task/getListPage.action?type=4&userId=${userId}" style="width:98%;height:98%;"></iframe>
	    </div>
	    <div id="tab5" title="<spring:message code='TABCOMPLETE'/>" data-options="closable:true">
			<iframe name="2" src="${path}/v3/app/menu/task/getListPage.action?type=5&userId=${userId}" style="width:98%;height:98%;"></iframe>
	    </div>
	    <div id="tab6" title="<spring:message code='HOURS'/>" data-options="closable:true">
	    	<iframe name="3" src="${path}/v3/app/menu/getMain6Page.action?userId=${userId}" style="width:98%;height:98%;"></iframe>
	    </div>
	    <div id="tab2" title="<spring:message code='MYASSIGN'/>" data-options="closable:false">
			<iframe name="4" src="${path}/v3/app/menu/task/getListPage.action?type=2&userId=${userId}" style="width:98%;height:98%;"></iframe>
	   	</div>
	   	<div id="tab1" title="<spring:message code='ALL'/>">
			<iframe name="5" src="${path}/v3/app/menu/task/getListPage.action?type=1&userId=${userId}" style="width:98%;height:98%;"></iframe>
		</div>
	</div>
</body>
</html>