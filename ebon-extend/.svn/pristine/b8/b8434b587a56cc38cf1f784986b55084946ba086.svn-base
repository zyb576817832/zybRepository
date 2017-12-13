<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<title>任务下项目概览页面</title>
</head>
<body>
	<div class="easyui-panel" style="padding:2px 10px;" data-options="fit:true">
		<table>
			<tr>
				<td ><spring:message code="PROJCODE"/>：</td>
				<td><input style="width:230px" class="easyui-textbox" value="${proj.code }" readonly="readonly"></td>
				<td><spring:message code="PROJSTATUS"/>：</td>
				<td><input class="easyui-textbox" value="${proj.status }" readonly="readonly"></td>
			</tr>
			<tr>
				<td ><spring:message code="PROJCUSTOMER"/>：</td>
				<td><input style="width:230px" class="easyui-textbox" value="${proj.customer }" readonly="readonly"></td>
				<td><spring:message code="PROJSIZE"/>：</td>
				<td><input class="easyui-textbox" value="${proj.projSize }" readonly="readonly"></td>
			</tr>
			<tr>
				<td width="100px"><spring:message code="PROJNAME"/>：</td>
				<td width="100px"><input style="width:230px" class="easyui-textbox" value="${proj.name }" readonly="readonly"></td>
				<td width="100px"><spring:message code="PROJSTAGE"/>：</td>
				<td width="100px"><input class="easyui-textbox" value="${proj.projStage }" readonly="readonly"></td>
			</tr>
			<tr>
				<td><spring:message code="PROJVEHICLE"/>：</td>
				<td><input class="easyui-textbox" value="${proj.vehicle }"    readonly="readonly"></td>
				<td><spring:message code="PROJENGINE"/>：</td>
				<td><input class="easyui-textbox" value="${proj.engine }" readonly="readonly"></td>
				
			</tr>
			<tr>
				<td><spring:message code="PROJPM"/>：</td>
				<td><input class="easyui-textbox" value="${proj.projPM }" readonly="readonly"></td>
				<td><spring:message code="PROJSALES"/>：</td>
				<td><input class="easyui-textbox" value="${proj.sales }" readonly="readonly"></td>
			</tr>
			<tr>
				
				<td>PH1：</td>
				<td><input class="easyui-textbox" value="${proj.ph1 }" readonly="readonly"></td>
				<td>PH2：</td>
				<td><input class="easyui-textbox" value="${proj.ph2 }" readonly="readonly"></td>
			</tr>
			<tr>
				<td>CSOP：</td>
				<td><input class="easyui-textbox" value="${proj.csop }" readonly="readonly"></td>
				<td>CSOD：</td>
				<td><input class="easyui-textbox" value="${proj.csod }"    readonly="readonly"></td>
			</tr>
			<tr>
				<td>CEOP：</td>
				<td><input class="easyui-textbox" value="${proj.ceop }"    readonly="readonly"></td>
				<td>EOP：</td>
				<td><input class="easyui-textbox" value="${proj.eop }"    readonly="readonly"></td>
			</tr>  
			<tr>
				<td>SOP：</td>
				<td><input class="easyui-textbox" value="${proj.sop }"    readonly="readonly"></td>
				<td>SOD：</td>
				<td><input class="easyui-textbox" value="${proj.sod }"    readonly="readonly"></td>
			</tr> 
			<tr>
				<td><spring:message code="PROJTIMELIGHT"/>：</td>
				<td colspan="3">
					<c:if test="${1==proj.timelight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_green.gif"/>
					</c:if>
					<c:if test="${2==proj.timelight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_yellow.gif"/>
					</c:if>
					<c:if test="${3==proj.timelight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_red.gif"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><spring:message code="PROJBUDGETLIGHT"/>：</td>
				<td colspan="3">
					<c:if test="${1==proj.budgetlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_green.gif"/>
					</c:if>
					<c:if test="${2==proj.budgetlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_yellow.gif"/>
					</c:if>
					<c:if test="${3==proj.budgetlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_red.gif"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><spring:message code="PROJCOSTLIGHT"/>：</td>
				<td colspan="3">
					<c:if test="${1==proj.costlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_green.gif"/>
					</c:if>
					<c:if test="${2==proj.costlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_yellow.gif"/>
					</c:if>
					<c:if test="${3==proj.costlight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_red.gif"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td><spring:message code="PROJQUALITYLIGHT"/>：</td>
				<td colspan="3">
					<c:if test="${1==proj.qualitylight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_green.gif"/>
					</c:if>
					<c:if test="${2==proj.qualitylight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_yellow.gif"/>
					</c:if>
					<c:if test="${3==proj.qualitylight }">
						<img src="/ebon/web/applications/Platform/OVERVIEW/icon_lamp_red.gif"/>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>