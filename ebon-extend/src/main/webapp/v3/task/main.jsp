<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>任务详情主页面</title>
<script type="text/javascript" src="${path}/v3/util.js"></script>
<script type="text/javascript">

	//优化效率,点击生成iframe
	var hashMap = new HashMap();//定义一个全局Map记录每一个Frame
	hashMap.put(0, '');
	hashMap.put(1,'<iframe src="${path}/v3/app/menu/task/getProjInfoPage.action?randomNum=${randomNum}&projId=${userTask.projId}" style="width:98%;height:95%;padding:0px;"></iframe>');
	hashMap.put(2,'<iframe src="${path}/v3/app/menu/task/getMileStonePage.action?randomNum=${randomNum}&projId=${userTask.projId}" style="width:98%;height:95%;padding:0px;"></iframe>');
	hashMap.put(3,'<iframe src="${path}/v3/app/menu/task/getDocPage.action?randomNum=${randomNum}&userId=${userId}&taskId=${userTask.id}&type=${type}" style="width:98%;height:98%;"></iframe>');
	hashMap.put(4,'<iframe src="${path}/v3/app/menu/task/getChildPage.action?randomNum=${randomNum}&userId=${userId}&taskId=${userTask.id}&assginUser=${userTask.assginUser}&type=${type}" style="width:98%;height:98%;padding:0px;"></iframe>');
	hashMap.put(5,'<iframe src="${path}/v3/app/menu/task/getCalendarPage.action?randomNum=${randomNum}&userId=${userId}&taskId=${userTask.id}" style="width:98%;height:98%;"></iframe>');

	
	function selectFn(title,index){
		if('任务工时'==title||'Task effort'==title){//如果没有子任务的Tabs:将index后移一位
			if(index==4){
				index = 5;
			}
		}
		if(index==0){//任务基本信息无Iframe
			return;
		}
		var tabId = 'tab' + index;
		var outsideDiv = document.getElementById(tabId);
		outsideDiv.innerHTML = '';
		outsideDiv.innerHTML = hashMap.get(index);
	}
	
	//格式化一些值
	$(function(){
		var type = '${type}';
		if(type!='2'|| '${userTask.status}' == '4' ){
			$("#updatePlanHours").attr("disabled", true);
		}
		 //类型
		if('${userTask.type}'=='1'){
			$('#type').val('<spring:message code="PLANTASK"/>');
		}else if('${userTask.type}'=='2'){
			$('#type').val('<spring:message code = "RISK"/>');
		}else if('${userTask.type}'=='3'){
			$('#type').val('OPL');
		}else if('${userTask.type}'=='4'){
			$('#type').val('<spring:message code = "DECOMPOSE"/>');
		}
		 
		 if('1'=='${userTask.agginFlag}'){
			 $('#agginFlag').val('Yes');
		 }else{
			 $('#agginFlag').val('No');
		 }
		 
		//分解任务
		var child = '';
		if('${userTask.childUndo}'){
			child += '${userTask.childUndo}';
		}
		if('${userTask.childSum}'){
			child += '/';
			child += '${userTask.childSum}';
		}
		$('#child').val(child);
		
		  var againDescr = '${userTask.againDescr }';
		  var descr = '${userTask.descr }';
		  document.getElementById('descr').value = descr;//描述换行显示
		  document.getElementById('againDescr').value = againDescr;//驳回原因换行显示
		  document.getElementById('againDescr1').value = againDescr;//驳回原因窗口换行显示
	});
	
	

	function startTask(){
		changeStatus('2');
	}
	function cancleTask(){
		changeStatus('5');
	}
	function approveTask(){
		changeStatus('3');
	}
	function agreeTask(){
		changeStatus('4');
	}
	function notAgreeTask(){//如果驳回修改为已开始,同时标记驳回
		$('#w').window('open');
	}
	
	function changeStatus(status){
		$.post('${path}/app/userTaskPlatfrom/changeStatus.action?userId=${userId}&id=${userTask.id}&status='+ status,function(data){
			parentTableReload();
		},'json');
	}
	
	//为空则发包人
	function remindTask(){
		$.post('${path}/app/userTaskPlatfrom/remindTask.action?userId=${userId}&id=${userTask.id}',function(data){
			$.messager.alert('Message','Send Mail Success');
		},'json');
	}
	
	//type是1则提醒任务责任人
	function remindTask1(){
		$.post('${path}/app/userTaskPlatfrom/remindTask.action?userId=${userId}&id=${userTask.id}&type=1',function(data){
			$.messager.alert('Message','Send Mail Success');
		},'json');
	}
	
	function parentTableReload(){
		parent.selectSomeRowID('${userTask.id}');
		parent.tableReload();//重新加载父页面状态
	}
	
	//更新任务的计划工时
	function updatePlanHours(){
		var reg = /^\+?[1-9][0-9]*$/;
		if (!reg.test($("#planHours").val())) {
			$.messager.alert('Message','illegal effort');
			return;
		}else{
			$.post('${path}/app/userTaskPlatfrom/update.action?id=${userTask.id}&planHours='+ $("#planHours").val(),function(data){
				$.messager.alert('Message','success');
			},'json');
		}
	}
	
	function clearForm(){
		$('#ff').form('clear');
	}
	function submitForm(){
		var reg = /\\/g;//不能输入\
		var againDescr = document.getElementById("againDescr1").value;
		if(reg.test(againDescr)){
			$.messager.alert('Message','please input correct text');
			return;
		}
		againDescr = againDescr.replace(/\n|\r\n/g,"\\n");
		if(againDescr){
			$.post('${path}/app/userTaskPlatfrom/update.action?id=${userTask.id}&againDescr='+encodeURIComponent(againDescr) ,function(data){
				$('#w').window('close'); 
				changeStatus('2');
			},'json');
		}else{
			$.messager.alert('Message','please input context');
		}
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'"  style="height:71px;width:100%; margin: 5px 5px;overflow: hidden;">
			<div id="w" class="easyui-window" data-options="closed:true" title="<spring:message code="TASKREJECTDESCR"/>" style="width:400px;height:250px;top: 10px;">
			<form id=ff method="post" style="padding:5px 0">
				<textarea id="againDescr1" style="width:380px;height: 100px;" >${userTask.againDescr }</textarea>
			</form>
			 <div style="text-align:center;padding:5px 0">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" >Submit</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()" >Clear</a>
			</div>
			</div>
			<table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse; width:100%;table-layout:auto">
				<tr style="width:100%">
					<td colspan="2" style="width:50%" ><h3 id="tab1_info_taskName">${userTask.name }</h3></td>
					<!-- 全部任务不显示任何按钮 -->
					<c:if test="${1 ne type }">
						<!--我的发包  -->
						<c:if test="${2 eq type }">
							<!-- new的时候需要提醒责任人接受任务 -->
							<c:if test="${1 eq userTask.status and userTask.assginUser eq userId}">
								<td><input type="button" value="Remind" onclick="remindTask1()" style="width:80px"></td>
							</c:if>
							<!-- 任务已接受发包人可以提醒责任人抓紧完成任务 -->
							<c:if test="${2 eq userTask.status and userTask.assginUser eq userId}">
								<td><input type="button" value="Remind" onclick="remindTask1()" style="width:80px"></td>
							</c:if>
							<!-- 且只有发包人可以看到 -->
							<c:if test="${4 ne userTask.status and userTask.assginUser eq userId}">
								<td><input type="button" value="Cancel" onclick="cancleTask()" style="width:80px"></td>
							</c:if>
							<c:if test="${3 eq userTask.status and userTask.assginUser eq userId}">
								<td><input type="button" value="Approve" onclick="agreeTask()" style="width:80px">
								<input type="button" value="Reject" onclick="notAgreeTask()" style="width:80px">
								</td>
							</c:if>
							
						</c:if>
						<!-- 新任务 -->
						<c:if test="${3 eq type }">
							<c:if test="${userTask.respUser eq userId}">
								<td><input type="button" value="Accept" onclick="startTask()" style="width:80px"></td>
							</c:if>
							<!-- 任务责任人收到任务后如果不接受则需要提醒发包人取消邮件提醒 -->
							<c:if test="${1 eq userTask.status and userTask.respUser eq userId}">
								<td><input type="button" value="Remind" onclick="remindTask()" style="width:80px"></td>
							</c:if>
						</c:if>
						<!--进行中  -->
						<c:if test="${4 eq type }">
							<c:if test="${2 eq userTask.status and userTask.respUser eq userId}">
								<td><input type="button" value="Finish" onclick="approveTask()" style="width:80px"></td>
							</c:if>
							<c:if test="${3 eq userTask.status and userTask.respUser eq userId}">
								<td><input type="button" value="Remind" onclick="remindTask()" style="width:80px"></td>
							</c:if>
						</c:if>
					</c:if>
				</tr>
				<tr style="height:29px">
					<td style="width:15%"><spring:message code="BASERESP"/>:</td>
					<td style="width:35%">${userTask.respUserName}(${userTask.respDept})</td>
					<td style="width:15%"><spring:message code="BASEPLANHOURES"/>:</td>
					<!--
					
					这里是计划工时修改
					
					  -->
					<td style="width:35%"><input value="${userTask.planHours}" id="planHours" name="planHours" style="width:60px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="updatePlanHours" type="button" value="save" onclick="updatePlanHours()" style="width:60px"></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',split:true" style="height:430px;overflow: hidden;">
			<div class="easyui-tabs" data-options="fit:true,onSelect:selectFn">
			<spring:message code="BASE" var="BASE"/>
				<div id="tab0" title="${BASE }" data-options="closable:false" style="overflow: hidden;">
					<div class="easyui-panel" style="width:auto; height:auto; padding:2px 3px;">
						
						
						<table>
						<tr>
							<td ><spring:message code="BASETYPE"/>：</td>
							<td colspan="3"><input class="easyui-textbox" id="type" readonly="readonly"></td>
						</tr>
						<c:if test="${parentUserTask.name ne null }">
							<tr>
								<td ><spring:message code="PARENTUSERTASK"/>：</td>
								<td colspan="3"><input class="easyui-textbox" value="${parentUserTask.name }" style="width:500px" readonly="readonly"></td>
							</tr>
						</c:if>
						<tr>
							<td><spring:message code="AGGINFLAG"/>：</td>
							<td colspan="3"><input class="easyui-textbox" id="agginFlag" readonly="readonly"></td>
						</tr>
						<tr>
							<td><spring:message code="BASESERIOUS"/>：</td>
							<td><input class="easyui-textbox" value="${userTask.seriousName }"    readonly="readonly"></td>
							<td><spring:message code="BASELEVEL"/>：</td>
							<td><input class="easyui-textbox" value="${userTask.priorityName }" readonly="readonly"></td>
							
						</tr>
						<tr>
							<td><spring:message code="BASESOURCE"/>：</td>
							<td colspan="3"><textarea style="width:500px;height: 70px;" readonly="readonly">${userTask.source }</textarea></td>
						</tr>
						<tr>
							
							<td><spring:message code="ACTSTART"/>：</td>
							<td><input class="easyui-textbox" value="${userTask.actStartDate }" readonly="readonly"></td>
							<td><spring:message code="ACTEND"/>：</td>
							<td><input class="easyui-textbox" value="${userTask.actEndDate }" readonly="readonly"></td>
						</tr>
						<tr>
							<td><spring:message code="ACTHOURS"/>：</td>
							<td><input class="easyui-textbox" value="${userTask.actHours }" readonly="readonly"></td>
							<td><spring:message code="CHILDTASK"/>：</td>
							<td><input class="easyui-textbox" value="${proj.prodOcode }" id="child"   readonly="readonly"></td>
						</tr>
						<tr>
							<td><spring:message code="TASKREJECTDESCR"/>：</td>
							<td colspan="3"><textarea style="width:500px;height: 70px;" id="againDescr" readonly="readonly"></textarea></td>
						</tr>
						<tr>
							<td><spring:message code="TASKDESCR"/>：</td>
							<td colspan="3"><textarea style="width:500px;height: 70px;" id="descr" readonly="readonly"></textarea></td>
						</tr>
					</table>
					</div>
				</div>
				<spring:message code="PROJINFO" var="PROJINFO"/>
				<spring:message code="MILESTONE" var="MILESTONE"/>
				<spring:message code="DOC" var="DOC"/>
				<spring:message code="CHILDPAGE" var="CHILDPAGE"/>
				<spring:message code="TASKHOURS" var="TASKHOURS"/>
				<div id="tab1" title="${PROJINFO }" data-options="closable:false"></div>
				<div id="tab2" title="${MILESTONE }" data-options="closable:false"></div>
				<div id="tab3" title="${DOC }" data-options="closable:false,selected:true"></div>
				<!-- 子任务不可以再分解 -->
				<c:if test="${userTask.parentId eq null }">
					<div id="tab4" title="${CHILDPAGE }" data-options="closable:false"></div>
				</c:if>
				<div id="tab5" title="${TASKHOURS }" id="tab1_tab15" data-options="closable:false"></div>
			</div>			
		</div>
	</div>
</body>
</html>