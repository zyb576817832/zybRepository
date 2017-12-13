<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>里程碑基本信息页面</title>
</head>
<body>
<div class="easyui-accordion" data-options="fit:true">
	<c:choose>
		<c:when test="${primProjId ne null && primProjId ne '' }">
		<spring:message code="SYSPROJECT" var="SYSPROJECT"/>
		<div title="${SYSPROJECT }" data-options="" style="overflow:auto;padding:1px;height:100px;">
			<table id="dg"  class="easyui-datagrid" 
			            data-options="fit:true,singleSelect:true,collapsible:true,method:'get',url:'${path }/app/mileStone/getListData.action?projId=${primProjId }&platformFlag=1'"
			            rownumbers="true"
						pagination="true"
						fitColumns="true">
	        <thead>
	            <tr>
		            <th data-options="field:'M_LEVEL',align:'cener', halign: 'center',width:160 " sortable="true"><spring:message code="MLEVEL"/></th>
					<th data-options="field:'NAME',align:'cener', halign: 'center',width:640 " sortable="true"><spring:message code="MNAME"/></th>
					<th data-options="field:'PLAN_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MPLANTIME"/></th>
					<th data-options="field:'ACT_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MACTTIME"/></th>
					<th data-options="field:'VERSION',align:'cener', halign: 'center',width:125 " sortable="true"><spring:message code="MVERSION"/></th>
	            </tr>
	        </thead> 
	    </table>
		</div>
		<spring:message code="CHILDPROJECT" var="CHILDPROJECT"/>
		<div title="${CHILDPROJECT }" data-options="selected:true" style="padding:1px;height:100px;">
			<table id="dg"  class="easyui-datagrid" 
			            data-options="fit:true,singleSelect:true,collapsible:true,method:'get',url:'${path }/app/mileStone/getChildListData.action?projId=${projId }&platformFlag=1'"
			            rownumbers="true"
						pagination="true"
						fitColumns="true">
	        <thead>
	            <tr>
		            <th data-options="field:'M_LEVEL',align:'cener', halign: 'center',width:160 " sortable="true"><spring:message code="MLEVEL"/></th>
					<th data-options="field:'NAME',align:'cener', halign: 'center',width:640 " sortable="true"><spring:message code="MNAME"/></th>
					<th data-options="field:'PLAN_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MPLANTIME"/></th>
					<th data-options="field:'ACT_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MACTTIME"/></th>
					<th data-options="field:'VERSION',align:'cener', halign: 'center',width:125 " sortable="true"><spring:message code="MVERSION"/></th>
	            </tr>
	        </thead> 
	    </table>			
		</div>
		</c:when>
		<c:otherwise>
			<spring:message code="SYSPROJECT" var="SYSPROJECT"/>
			<div title="${SYSPROJECT }" data-options="" style="overflow:auto;padding:1px;height:100px;">
				<table id="dg"  class="easyui-datagrid" 
			            data-options="fit:true,singleSelect:true,collapsible:true,method:'get',url:'${path }/app/mileStone/getListData.action?projId=${projId }&platformFlag=1'"
			            rownumbers="true"
						pagination="true"
						fitColumns="true">
		        <thead>
		            <tr>
			            <th data-options="field:'M_LEVEL',align:'cener', halign: 'center',width:160 " sortable="true"><spring:message code="MLEVEL"/></th>
						<th data-options="field:'NAME',align:'cener', halign: 'center',width:640 " sortable="true"><spring:message code="MNAME"/></th>
						<th data-options="field:'PLAN_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MPLANTIME"/></th>
						<th data-options="field:'ACT_TIME',align:'cener', halign: 'center',width:320 " sortable="true"><spring:message code="MACTTIME"/></th>
						<th data-options="field:'VERSION',align:'cener', halign: 'center',width:125 " sortable="true"><spring:message code="MVERSION"/></th>
		            </tr>
		        </thead> 
		    </table>
			</div>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>