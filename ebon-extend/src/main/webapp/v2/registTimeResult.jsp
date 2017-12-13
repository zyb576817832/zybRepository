<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>同步结果显示</title>
<script type="text/javascript">
	function aa() {
		 var id = ${command.id};
		 var type = ${command.type};
		 var sys = ${command.sys};
		 if(sys==1){
			 sys="LIMS";
		 }
		 else{
			 sys="UCS";
		 }
		 var status = ${command.status};
		 if(status==1){
			 status="成功";
		 }
		 else{
			 status="失败";
		 }
		 var startDate = '${command.startDate}';
		 var sendedTime = '${command.sendedTime}';
		 var comments = '${command.comments}';
		 //SyntaxError: unterminated string literal
		 //var description = 'command.description';
		 input = document.getElementsByName("command");
		 input[0].value = id;
		 input[1].value = startDate;
		 input[2].value = sendedTime;
		 input[3].value = '手动';
		 //0表示ucs 1表示Lims
		 input[4].value = sys;
		 input[5].value = status;
		 
		 textarea = document.getElementsByName("desc");
		 textarea[0].value = comments;
 		 
	}
</script>
<style type="text/css">
table input {
	
}

#table {
	width: 800px;
	margin: 0 auto;
}

#table table {
	width: 100%;
}
</style>
</head>
<body onload="aa()">
	<div id="table">
		<table border="2">
			<tr>
				<td>ID</td>
				<td width="200"><input type="text" name="command" /></td>
				<td>类型</td>
				<td width="200"><input type="text" name="command" /></td>
			</tr>
			<tr>
				<td>发送时间</td>
				<td><input type="text" name="command" /></td>
				<td>开始时间</td>
				<td><input type="text" name="command" /></td>
			</tr>
			<tr>
				<td>系统</td>
				<td><input type="text" name="command" /></td>
				<td>状态</td>
				<td><input type="text" name="command" /></td>
			</tr>
			<tr>
				<td>描述</td>
				<td colspan="3">
					<p>
						<textarea rows="3" cols="100" name="desc"></textarea>
					</p>
				</td>
			</tr>
			<tr>

				<td>详细描述</td>
				<td colspan="3">
					<p>
						<textarea rows="5" cols="100" name="desc"></textarea>
					</p>
				</td>

			</tr>
			<tr>
				<td colspan="2"><a href="../v2/registTime.jsp">返回同步页面继续同步</a></td>
				<td colspan="2"><a href="../v2/commandList.jsp">查看所有同步记录</a></td>
			</tr>

		</table>

	</div>
</body>
</html>