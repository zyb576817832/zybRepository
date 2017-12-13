<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<style type="text/css">
table td{
	border: 1px solid black;
	width:150px;
	height: 20px;
	font-family: 楷体_GB2312;
	font-size: 13;
}

.mystyle{
	background-color:gray;
}
td input{
border:none;
width:100%;
height: 100%;
}
</style>
<script type="text/javascript">
	var check = true; 
	function putBudegt(){
		if(check){
			$.ajax({
				type:'post',
				url:'/uPMS/budgetController/putBudget',
				data:$("#putBudget").serialize(),
				success:function(data){
					alert("提交成功");
					//提交成功后刷新
					location.reload();
					onLo();
				},
				error:function(data){
					alert(data);
				}
			});
		}else{
			alert("请输入数字，最高保存两位小数");
		}
	}
	function onLo(){
		
		//如果是系统项目则无保存按钮input只读
		var isSYS = <%=request.getAttribute("isSYS") %>;
		if(isSYS){
			
			//弃用该机制，这样会导致系统项目没有持久化，这样出CPC图的时候没有数据
			$("#show").css("display","none");
			//因此增加系统项目的Budget保存
			$(":text").attr("readonly",true);
		}
		
		//在初始化数据的时候，先增加千分符，再为页面赋值
		addQianfen('_employee','${budget.PEO }');
		addQianfen('_mad','${budget.MAD }');
		addQianfen('_ser','${budget.SER }');
		
		var _employee = Number('${budget.PEO }');
		var _mad = Number('${budget.MAD }');
		var _ser = Number('${budget.SER }');
		var employee = Number('${employee}');
		var mad = Number('${mad}');
		var ser = Number('${ser}');
		var _sum = _employee+_mad+_ser;
		var sum = employee+mad+ser;
		//这种方式是直接定义id是sum1的td节点
		//document.getElementById("sum1").innerHTML=_sum.toFixed(2);
		addQianfen('sum1',_sum.toFixed(2));
		
		
		//document.getElementById("sum2").value=sum;
		addQianfen('sum2',sum.toFixed(2));
		
	}
	
	function addQianfen(id,value){
		//首先该值是否含有小数
		if(value.indexOf('.') > -1){
			//先获取整数部分
			var n = value.substr(0,value.indexOf("."));
			//千分位处理
			var re=/\d{1,3}(?=(\d{3})+$)/g;
			var n1=n.replace(/^(\d+)((\.\d+)?)$/,function(s,s1,s2){return s1.replace(re,"$&,")+s2;});
			//返回的时候加上小数部分
			$("#" + id).val(n1 + '.' + value.substr(value.indexOf(".")+1));
			
		}else{
			var n = value;
			//千分位处理
			var re=/\d{1,3}(?=(\d{3})+$)/g;
			var n1=n.replace(/^(\d+)((\.\d+)?)$/,function(s,s1,s2){return s1.replace(re,"$&,")+s2;});
			$("#"+id).val(n1);
		}
	}
	
	
	/* 
	失去焦点输入框的校验
	*/
	//定义失焦事件
	function validate(obj){
		var str = obj.value;
		var id = obj.id;
		str = rep(str);
		
		var pattern =/^[0-9]+([.]\d{1,2})?$/;
		//判断为数字并且最多两位小数
		if(!pattern.test(str)){
			alert("请输入数字，最高保存两位小数");
			$("#"+id).val('');
		}else{
			addQianfen(id,str);
		}
	}
	
	function rep(str){
		if(str.indexOf(',') > -1){
			str =  str.replace(eval("/"+','+"/gi"),'');
		}
		return str;
	}
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目预算</title>
</head>
<body onload="onLo()">
<div>
	<form action="${path}/budgetController/putBudget.do" id="putBudget">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>Project Number</td>
				<td>Budget Plan(RMB)</td>
				<td>Actual Cost(RMB)</td>
			</tr>
			<tr>
				<td>R&D Person sum</td>
				<td><input type="text" name="_employee" id="_employee" value="" onblur="validate(this)"></td>
				<td class="mystyle" id="employee">${employee}</td>
			</tr>
			<tr>
				<td>R&D MAE</td>
				<td><input type="text" name="_mad" id="_mad" value="" onblur="validate(this)"></td>
				<td class="mystyle" id="mad">${mad}</td>
			</tr>
			<tr>
				<td>R&D Service</td>
				<td><input type="text" name="_ser" id="_ser" value="" onblur="validate(this)"></td>
				<td class="mystyle" id="ser">${ser}</td>
			</tr>
			<tr style="background-color:gray;">
				<td>Budget sum</td>
				<td><input id = 'sum1'  style="background-color:gray;"></td>
				<td><input id = 'sum2'  style="background-color:gray;"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td><input  id="show"type="button" value="Save Project Budget" onclick="putBudegt()" ></td>
			</tr>
		</table>
		<input value="<%=request.getParameter("projId")%>" name="projId" style="visibility:hidden">
		<%-- <input value="${projName}" style="visibility:hidden">
		<a href="/uPMS/budgetCPC/getBudgetCPC.do?proj_id=${projName}" target="_blank">查看项目开发费用跟踪表</a>
		<a href="/uPMS/milestoneCPC/getMilestoneCPC.do?proj=${projName}" target="_blank">查看项目里程碑点跟踪表</a> --%>
	</form>
</div>
</body>
</html>