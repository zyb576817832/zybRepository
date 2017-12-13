<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ebon.platform.util.DateHelper" %>
<%
	String path = request.getContextPath();
	String title ="项目管理平台";
	
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path ;
	String currentTime = DateHelper.toString(DateHelper.currentTime(),"yyyyMMddHHmm");
	String currentDate = DateHelper.toString(DateHelper.currentTime(),"yyyy-MM-dd HH:mm:ss");
	
	request.setAttribute("path", path);
	request.setAttribute("jspPath", path + "/modules");
	request.setAttribute("basePath", basePath);
	request.setAttribute("theme", path + "/themes");
	request.setAttribute("title", title);
	request.setAttribute("currentTime", currentTime);
	request.setAttribute("currentDate", currentDate);
%>
<script type="text/javascript" src = "${path}/script/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="${theme}/easyui/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/easyui/icon.css"/>
<script type="text/javascript" src="${path}/script/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/script/easyui/locale/easyui-lang-zh_CN.js"></script>