<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人统一待办主页面</title>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">

	//优化效率,点击生成iframe
	var hashMap = new HashMap();//定义一个全局Map记录每一个Frame
	hashMap.put(0, '<iframe name="0" src="${path}/v3/app/menu/task/getListPage.action?type=4&userId=${userId}" style="width:100%;height:98%;"></iframe>');
	hashMap.put(1,'<iframe name="1" src="${path}/v3/app/menu/task/getListPage.action?type=5&userId=${userId}" style="width:100%;height:98%;"></iframe>');
	hashMap.put(2,'<iframe name="3" src="${path}/v3/app/menu/getMain6Page.action?userId=${userId}" style="width:100%;height:98%;"></iframe>');
	hashMap.put(3,'<iframe name="4" src="${path}/v3/app/menu/task/getListPage.action?type=2&userId=${userId}" style="width:100%;height:98%;"></iframe>');
	hashMap.put(4,'<iframe name="5" src="${path}/v3/app/menu/task/getListPage.action?type=1&userId=${userId}" style="width:100%;height:98%;"></iframe>');
	hashMap.put(5,'<iframe name="2" src="${path}/v3/app/menu/task/getListPage.action?type=3&userId=${userId}" style="width:100%;height:98%;"></iframe>');
	$(function(){
		var type ='${type}';
		if(type){
			$('#tabs').tabs('select',Number(type.substring(0,1)));//如果有多个只取第一个
		}
	});
	
	function selectFn(title,index){
		var tabId = 'tab' + index;
		var outsideDiv = document.getElementById(tabId);
		outsideDiv.innerHTML = '';
		outsideDiv.innerHTML = hashMap.get(index);
	}
</script>
</head>
<body>
	<div id="tabs" class="easyui-tabs" data-options="fit:true,onSelect:selectFn">
	   	<!-- 进行中 type4-->
	    <div id="tab0" title="<spring:message code='ONGOINGTASK'/>" data-options="closable:false" style="overflow: hidden;"></div>
	    <!-- 已完成type5 -->
	    <div id="tab1" title="<spring:message code='TABCOMPLETE'/>" data-options="closable:false" style="overflow: hidden;"></div>
	    <!-- 工时登记6 -->
	    <div id="tab2" title="<spring:message code='HOURS'/>" data-options="closable:false" style="overflow: hidden;"></div>
	    <!-- 我的发包type 2-->
	    <div id="tab3" title="<spring:message code='MYASSIGN'/>" data-options="closable:false" style="overflow: hidden;"></div>
	    <!-- 全部任务 type1-->
	   	<div id="tab4" title="<spring:message code='ALL'/>" style="overflow: hidden;"></div>
	   	<!-- 新任务 type3-->
	   	<div id="tab5" title="<spring:message code='NEWTASK'/>" data-options="closable:false" style="overflow: hidden;"></div>
	</div>
</body>
</html>