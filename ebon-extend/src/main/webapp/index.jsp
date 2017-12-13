<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PMS</title>
</head>
<script type="text/javascript">
	function test() {
		alert($('form'));
	}
</script>
<body>
<input type="button" value="test" onclick="test();"/>
<form action="login" method="post">
<input type="text" id="username" name="username" value="用户名" onfocus="getvalue(this)" onblur="setvalue(this)" />
<input type="password" id="password" name="password" value="" onfocus="getvalue(this)" onblur="setvalue(this)"/>
<input type="submit" value="登录"/>
</form>
</body>
</html>