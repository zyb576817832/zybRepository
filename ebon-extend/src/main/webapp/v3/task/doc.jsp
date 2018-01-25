<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/common/meta1.jsp"%>
<title>文档列表</title>
<script type="text/javascript">

  $(function(){
	  var descr = '${descr}';
	  document.getElementById("descr").value=descr;
	  var type = '${type}';
	  if(type != '4' ){
		  $(".perssionClass").css("visibility","hidden");
	  }
	  if(type =='1'|| type == '3' || type=='5'){
		  $(".perssionClassRemark").css("visibility","hidden");  
	  }
  });
	function uploadFile(){
		$('#ff').submit();
		tableReload();//刷新
	}
	function formatterCZ(value, row, index){
		if('${type}' == '4'){
			var s = "<a href='#' onclick=downloadFile('"+ row.ID +"','" + row.NAME +"')><font color='6699CC'>download</a>";
			s+='<a href=\'#\' onclick=deleteFile("' + row.ID	+ '")><font color=\'6699CC\'>|delete</a>';
		}
		if('${type}' != '4'){
			var s = "<a href='#' onclick=downloadFile('"+ row.ID +"','" + row.NAME +"')><font color='6699CC'>download</a>";
		}
		return s;
	}
	
	function downloadFile(pathId, fileName){
		$('#pathId').val(pathId);
		$('#fileName').val(fileName);
		$('#fileForm').submit();
	}
	
	function deleteFile(id){
		$.messager.confirm('Delete','Confirm to delete?',function(r){
			if (r){
				$.post('${path}/app/taskDoc/delete.action?updateUser=${userId}&taskId=${taskId }&ids='+ id,function(data){
					//$.messager.alert('Message','success');
					tableReload();
				},'json');
			}
		});
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
	
	function clearForm(){
		$('#ff1').form('clear');
	}
	function submitForm(){
		var reg = /\\/g;//不能输入\
		var str = document.getElementById("descr").value;
		if(reg.test(str)){
			$.messager.alert('Message','please input correct text');
			return;
		}
		str = str.replace(/\n|\r\n/g,"\\n");
		if(str){
			$.post('${path}/app/userTaskPlatfrom/update.action?id=${taskId}&descr='+encodeURIComponent(str) ,function(data){
				$('#w1').window('close');  
			},'json');
		}else{
			$.messager.alert('Message','please input context');
		}
	}
	function openWindow(data){
		if(data == '1'){
			$('#w').window('open');
		}else{
			$('#w1').window('open');
		}
	}
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north'" style="height:35px;">
		<a href="javascript:void(0)" class="easyui-linkbutton perssionClass" onclick="openWindow(1);"><spring:message code='DOCADD'/></a>
		<a href="javascript:void(0)" class="easyui-linkbutton perssionClassRemark" onclick="openWindow(2);"><spring:message code='ADDTASKREMARK'/></a>
		<form action="${path}/app/taskDoc/downloadFile.action" id="fileForm" name="fileForm">
			<input name="pathId" id="pathId" type="hidden">
			<input name="fileName" id="fileName" type="hidden">
		</form>
		<div id="w1" class="easyui-window" data-options="closed:true" title="<spring:message code="TASKREMARK"/>" style="width:400px;height:250px;top: 10px;">
			<form id=ff1 method="post" style="padding:5px 0">
				<textarea id="descr" style="width:380px;height: 100px;" ></textarea>
			</form>
			 <div style="text-align:center;padding:5px 0">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >Submit</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" >Clear</a>
			</div>
		</div>
	</div>
	<spring:message code='DOCCHOOSE' var="DOCCHOOSE"/>
	<div id="w" class="easyui-window" data-options="closed:true" title="${DOCCHOOSE }：" style="width:300px;height:200px;top: 10px;">
			<form id=ff enctype="multipart/form-data" action="${path}/app/taskDoc/uploadFile?" method="post">
				<input name="type" value="${type }" type="hidden">
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
					<th data-options="field:'NAME'" width="40px;" sortable="true"><spring:message code='DOCNAME'/></th>
					<th data-options="field:'CREATE_TIME'" width="28px;" sortable="true"><spring:message code='DOCUPTIME'/></th>
					<th data-options="field:'FILE_SIZE'" width="15px; sortable="true"><spring:message code='DOCFILESIZE'/></th>
					<th data-options="field:'1',align:'center',formatter:formatterCZ" width="30px;" sortable="true"><spring:message code='DOCOPERATION'/></th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>