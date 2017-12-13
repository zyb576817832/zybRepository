<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手动获取数据</title>
<script type="text/javascript">
	function submitForm1(){
		try{
		//$('#frm').form('submit');
		//document.forms("frm").submit()
			frm1.submit();
		}catch(e){
			alert(e.message);
		}
	}
	
	function submitForm2(){
		try{
			frm2.submit();
		}catch(e){
			alert(e.message);
		}
	}
	
	function submitForm3(){
		try{
			frm3.submit();
		}catch(e){
			alert(e.message);
		}
	}
	
	function submitForm4(){
		try{
			frm4.submit();
		}catch(e){
			alert(e.message);
		}
	}
</script>
</head>
<body>
<div>
<form id="frm1" id="frm1" action="${path}/limsController/doCommand.do" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>LIMS手动取得设备报工数据</td>
			</tr>
			<tr>
				<td>
					Register StartDate：<input style="width: 200px" id="regStartDateL" name="regStartDateL" editable="false" class="easyui-datebox"/>
					<a href="#" onclick="submitForm1();" class="easyui-linkbutton" iconCls="icon-search">抽取</a>
				</td>
			</tr>
		</table>
</form>
</div>
<div>
<form id="frm2" id="frm2" action="${path}/ucsController/doCommand.do" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>UCS手动报工取得人工报工数据</td>
			</tr>
			<tr>
				<td>
				Register StartDate：<input style="width: 200px" id="regStartDateU" name="regStartDateU" editable="false" class="easyui-datebox"/>
					<a href="#" onclick="submitForm2();" class="easyui-linkbutton" iconCls="icon-search">抽取</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div>
<form id="frm3" id="frm3" action="${path}/limsController/pushAllProjectInfo.do" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>首次推送全部信息</td>
			</tr>
			<tr>
				<td>
					<a href="#" onclick="submitForm3();" class="easyui-linkbutton" iconCls="icon-search">确定</a>
				</td>
			</tr>
		</table>
</form>
</div>
<div>
<form id="frm4" id="frm4" action="${path}/limsController/pushProjectInfo.do" style="margin: 10; text-align: center;" validate>
		<table width="100%">
			<tr>
				<td>LIMS获取项目主信息</td>
			</tr>
			<tr>
				<td>
					<a href="#" onclick="submitForm4();" class="easyui-linkbutton" iconCls="icon-search">抽取</a>
				</td>
			</tr>
		</table>
</form>
</div>
提示：同步时数据量较大，请耐心等待。  
</body>
</html>