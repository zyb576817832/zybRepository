<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<%@ taglib  uri ="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首次推送的结果</title>
<script type="text/javascript">
$(function(){
	var status = ${projectInfoCommand.status};
	if(status == 1){
		status = "成功";
	}else if(status == 0){
		status = "失败";
	}else{
		status = "本次操作没有要同步的数据";
	}
	document.getElementById("status").value = status;
	document.getElementById("desc").value = "${fn:escapeXml(projectInfoCommand.description)}";
});
</script>
</head>
<body>
	<table border="1">
		<tr>
			<td colspan="2">状态：<input type="text" id="status" /></td>
		</tr>
		<tr>
			<td  valign="middle">详细描述：</td>
			<td><textarea rows="20" cols="60" id="desc"></textarea></td>
		</tr>
	</table>
</body>
</html>