<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.ebon.platform.util.DateHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
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
<link rel="stylesheet" type="text/css" href="${theme}/easyui1.3.2/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/easyui1.3.2/icon.css"/>
<link rel="stylesheet" type="text/css" href="${theme}/easyui1.3.2/demo.css"/>
<script type="text/javascript" src = "${path}/script/easyui1.3.2/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src = "${path}/script/easyui1.3.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path}/script/easyui1.3.2/locale/easyui-lang-zh_CN.js"></script>
