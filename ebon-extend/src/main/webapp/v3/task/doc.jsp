<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<title>文档列表</title>
<script type="text/javascript">

	function uploadFile(){
		$('#ff').submit();
		tableReload();//刷新
	}
	function formatterCZ(value, row, index){
		var s = "<a href='#' onclick=downloadFile('"+ row.ID +"','" + row.NAME +"')><font color='6699CC'>download</a>";
		s+='<a href=\'#\' onclick=deleteFile("' + row.ID	+ '")><font color=\'6699CC\'>|delete</a>';
		return s;
	}
	
	function downloadFile(pathId, fileName){
		$('#pathId').val(pathId);
		$('#fileName').val(fileName);
		$('#fileForm').submit();
	}
	
	function deleteFile(id){
		$.post('${path}/app/taskDoc/delete.action?updateUser=${userId}&ids='+ id,function(data){
			$.messager.alert('Message','success');
			tableReload();
		},'json');
	}
	
	function tableReload(){
		var ranNumber=Math.random(); //IE缓存
		$.post('${path }/app/taskDoc/getVlist.action?taskId=${taskId }&ranNumber='+ ranNumber, function(data){
			$('#dg').datagrid('loadData',data);
		},'json');
	}
	
	$(function(){
		var ranNumber=Math.random(); //IE缓存
		$('#dg').datagrid({    
		    url:'${path }/app/taskDoc/getVlist.action?taskId=${taskId }&ranNumber='+ ranNumber  
		}); 
	})
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" style="height:35px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')"><spring:message code='DOCADD'/></a>
		<form action="${path}/app/taskDoc/downloadFile.action" id="fileForm" name="fileForm">
			<input name="pathId" id="pathId" type="hidden">
			<input name="fileName" id="fileName" type="hidden">
		</form>
	</div>
	<spring:message code='DOCCHOOSE' var="DOCCHOOSE"/>
	<div id="w" class="easyui-window" data-options="closed:true" title="${DOCCHOOSE }：" style="width:300px;height:200px;top: 10px;">
			<form id=ff enctype="multipart/form-data" action="${path}/app/taskDoc/uploadFile" method="post">
				<input name="taskId" value="${taskId }" type="hidden">
				<input name="userId" value="${userId }" type="hidden">
				<div style="margin-bottom:20px">
					<input type="file" name="file">
				</div>
				<div>
					<a href="#" class="easyui-linkbutton" onclick="uploadFile()">Upload</a>
				</div>
			</form>
	</div>
	<div data-options="region:'center'">
		<table id="dg" class="easyui-datagrid"
			data-options="url:'',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true,rownumbers:true,pagination:true">
			<thead>
				<tr>
					<th data-options="field:'NAME'" width="35%" sortable="true"><spring:message code='DOCNAME'/></th>
					<th data-options="field:'CREATE_TIME'" width="35%" sortable="true"><spring:message code='DOCUPTIME'/></th>
					<th data-options="field:'FILE_SIZE'" width="15%" sortable="true"><spring:message code='DOCFILESIZE'/></th>
					<th data-options="field:'1',align:'center',formatter:formatterCZ" width="15%" sortable="true"><spring:message code='DOCOPERATION'/></th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>