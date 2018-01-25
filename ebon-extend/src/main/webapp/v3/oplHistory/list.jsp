<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>opl历史纪录</title>
<script type="text/javascript">
function exportExcel(){
	$("#export").submit();
}
</script>
</head>
<body style="padding-top:0px;">

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" style="height:35px;">
		<a href="#" class="easyui-linkbutton" onclick="exportExcel('${oplId}')">Export</a>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dg"  class="easyui-datagrid"
			            data-options="fit:true,singleSelect:true,collapsible:true,method:'get',url:'${path }/app/oplHistory/getListData.action?oplId=${oplId }&randomNum=${randomNum }'"
			            rownumbers="true"
						pagination="true"
						fitColumns="true">
			<thead>
				<tr>
					<th data-options="field:'CREATE_TIME', width:15" sortable="true" ><spring:message code="OPLHUPDATETIME"/></th>
					<th data-options="field:'CREATE_USER',width:10 " sortable="true"><spring:message code="OPLHUPDATEUSER"/></th>
					<th data-options="field:'OPL_DESC',width:40" sortable="true"><spring:message code="OPLHDESC"/></th>
					<th data-options="field:'OPL_DEGREE',width:10 " sortable="true"><spring:message code="OPLHDEGREE"/></th>
					<th data-options="field:'OPL_LEVEL',width:10 " sortable="true"><spring:message code="OPLHLEVEL"/></th>
					<th data-options="field:'RESP_USER',width:10 " sortable="true"><spring:message code="OPLHRESPUSER"/></th>
					<th data-options="field:'OPL_DESCRIBE',width:10 " sortable="true"><spring:message code="OPLHDESCRIBE"/></th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<form id="export" action="${path }/app/oplHistory/export.action">
	<input type="hidden" name="oplId" value="${oplId }">
</form>
</body>
</html>