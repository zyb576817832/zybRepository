<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/meta1.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主项目里程碑列表</title>
<script type="text/javascript">
	/*js方式动态生成下拉框*/
	$(function(){
		$('#cc').combobox({
		    url:'${path }/app/mileStone/initCombox?projId=${projId }&comboxCache='+new Date().getTime(),
		    valueField:'id',
		    textField:'text',
		    onSelect: function(){
		    	queryData();
		    }
		});
		
		var pFlag = '${permissionFlag}';
		if(pFlag){
			$(".perssionClass").css("visibility","hidden");
		}
	});
	
	/* 添加里程碑 */
	function submitForm(){
		$('#ff').form('submit',{
			url: '${path}/app/mileStone/add.action?projId=${projId}&createUser=${userId}',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(data){
				var result = eval('('+data+')');
				if (result.code=='0'){
					$.messager.alert('Message','failed');
				} else {
					//$.messager.alert('Message','success');
					$('#dlg').dialog('close');// 关闭选择对话框
					$('#dg').datagrid({    
					    url:'${path}/app/mileStone/getListData?projId=${projId}&version=0&_t='+new Date().getTime()  
					});  
				}
			}
		});
	}
	function clearForm(){
		$('#ff').form('clear');
	}
	/* 删除里程碑 */
	function mileStoneDelete(){
		var row = $('#dg').datagrid('getSelected');//获取选中行
		if(row){
			$.messager.confirm('Delete','Confirm to delete?',function(r){
				if (r){
					$.post('${path}/app/mileStone/delete?',{id:row.ID},function(result){
						$.messager.alert('Message','success');
						$('#dg').datagrid({    
						    url:'${path}/app/mileStone/getListData?projId=${projId}&version=0&_t='+new Date().getTime()  
						}); 
					},'json');
				}
			});
		}
	}
	/* 刷新表格 */
	function tableReload(){	
		//解决由于url没有改变的缓存问题
		var url = $('#dg').datagrid('options').url;  
		if (url.indexOf("_t=") > 0) {  
		    url = url.replace(/_t=\d+/, "_t=" + new Date().getTime());  
		} else {  
		    url = url.indexOf("?") > 0  
		        ? url + "&_t=" + new Date().getTime()  
		        : url + "?_t=" + new Date().getTime();  
		}
		$('#dg').datagrid({    
		    url:url  
		});  
	}
	
	/* 保存基线 */
	function saveBaseLine(){
			$.messager.confirm('Save','Build new baseline?',function(r){
				if (r){
					$.post('${path}/app/mileStone/save?',{'projId':'${projId}'},function(result){
						$.messager.alert('Message','success');
						$('#cc').combobox('reload');//动态刷新该下拉框
						location.reload();
					},'json');
				}
			});
	}
	
	
	function queryData(){ 
		
		var v = $("#cc").combobox('getValue');
		if(v > 0){
		   $.post('${path}/app/mileStone/getListData?',{projId:'${projId}',version:v},function(data){
		        if (data) { 
		       		$("#dg").datagrid('loadData', data); 
		        }; 
	       },'json'); 
	       $(".perssionClass").css("visibility","hidden");
		}else{
	       $.post('${path}/app/mileStone/getListData?',{projId:'${projId}',version:'0'},function(data){
		        if (data) { 
		       		$("#dg").datagrid('loadData', data); 
		        }; 
	       },'json');  
	       $(".perssionClass").css("visibility","visible");
		}
	}
	
	function openDialog(){
		$('#dlg').dialog('open');
		$('#addName').val('');
		$('#addPlanTime').datebox('setValue', '');
		$('#addActTime').datebox('setValue', '');
	}
	
	function edit(){
		var row = $('#dg').datagrid('getSelected');//获取选中行
		
		if(!row){
			$.messager.alert('Message','uncheck');
			return;
		}
		$('#dlg1').dialog('open');
		$('#name').val(row.NAME);
		$('#id').val(row.ID);
		$('#planTime').datebox('setValue', row.PLAN_TIME);
		$('#actTime').datebox('setValue', row.ACT_TIME);
	}
	
	function submitForm1(){
		$('#fff').form('submit',{
			url: '${path}/app/mileStone/edit?',
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(data){
				var result = eval('('+data+')');
				if (result.code=='0'){
					$.messager.alert('Message','failed');
				} else {
					$.messager.alert('Message','success');
					$('#dlg1').dialog('close');// 关闭选择对话框
					$('#dg').datagrid({    
					    url:'${path}/app/mileStone/getListData?projId=${projId}&version=0&_t='+new Date().getTime()  
					}); 
				}
			}
		});
	}
	/*导出*/
	function exportExcel(){
		
		var v = $("#cc").combobox('getValue');
	    var maxm = "";
	    $.ajax({
	    	url:'${path}/app/mileStone/initCombox?projId=${projId}',
	    	dataType:'json',
	    	async : false,
	    	type:'post',
	    	success: function(data){
	    		
	    		maxm = data.length;
	    	}
	    });
	   
		if(v== maxm){
			$("#exportVersion").val('');
		}else{
			$("#exportVersion").val(v);
		}
		$("#export").submit();
	}
</script>
</head>
<body style="padding-top: 0px">
	
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false" style="height:30px;">
			<a href="#" class="easyui-linkbutton perssionClass" onclick="openDialog();"><spring:message code="MBUTTONADD"/></a>
			<a href="#" class="easyui-linkbutton perssionClass" onclick="edit()"><spring:message code="MBUTTONUPDATE"/></a>
			<a href="#" class="easyui-linkbutton perssionClass" onclick="mileStoneDelete()"><spring:message code="MBUTTONDELETE"/></a>
			<a href="#" class="easyui-linkbutton perssionClass" onclick="saveBaseLine()"><spring:message code="MBUTTONSAVE"/></a>
	   		<input id="cc" class="easyui-combobox" />
	   		<!--  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="queryData()"><spring:message code="MBUTTONSEARCH"/></a>-->
	   		<a href="#" class="easyui-linkbutton" onclick="exportExcel('${projId}')">Export</a>
		</div>
		
		<div data-options="region:'center',border:false">
			<table id="dg"  class="easyui-datagrid" 
							style="height: 200px"
				            data-options="fit:true,singleSelect:true,collapsible:true,method:'get',url:'${path }/app/mileStone/getListData.action?projId=${projId }&randomNum=${randomNum }'"
				            rownumbers="true"
							pagination="true"
							fitColumns="true">
		        <thead>
		            <tr>
			            <th data-options="field:'M_LEVEL',width: 10" sortable="true"><spring:message code="MLEVEL"/></th>
						<th data-options="field:'NAME',width:40 " sortable="true"><spring:message code="MNAME"/></th>
						<th data-options="field:'PLAN_TIME',width:20" sortable="true"><spring:message code="MPLANTIME"/></th>
						<th data-options="field:'ACT_TIME',width:20 " sortable="true"><spring:message code="MACTTIME"/></th>
						<th data-options="field:'VERSION',width:10 " sortable="true"><spring:message code="MVERSION"/></th>
		            </tr>
		        </thead> 
		    </table>
   		 </div>
	</div>
					
    <div id="dlg" class="easyui-dialog" title="<spring:message code="MADD"/>" data-options="closed:true" style="width:450px;height:260px;padding:10px;left:20px;top:30px">
		<div class="easyui-panel" style="width:400px">
			<div style="margin:0 auto">
			    <form id="ff" method="post">
			    	<table cellpadding="5">
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MNAME"/>：</font><input id="addName"  class="easyui-validatebox" style="width:280px" data-options="required:true"  name="name" ></input></td>
			    		</tr>
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MPLANTIME"/>：</font><input id="addPlanTime"  class="easyui-datebox" style="width:280px"  data-options="required:true,panelWidth:270" name="planTime"></input></td>
			    		</tr>
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MACTTIME"/>：</font><input id="addActTime" class="easyui-datebox" style="width:280px" data-options="panelWidth:270"  name="actTime"></input></td>
			    		</tr>
			    	</table>
			    </form>
			    <div style="text-align:center;padding:5px">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()"><spring:message code="MBUTTONSUBMIT"/></a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="MBUTTONREST"/></a>
			    </div>
		    </div>
		</div>
	</div>
	
	<div id="dlg1" class="easyui-dialog" title="<spring:message code="MUPDATE"/>" data-options="closed:true" style="width:450px;height:260px;padding:10px;left:20px;top:30px">
		<div class="easyui-panel" style="width:400px">
			<div style="margin:0 auto">
			    <form id="fff" method="post">
			    	<table cellpadding="5">
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MNAME"/>：</font><input id="name"  class="easyui-validatebox" style="width:280px" data-options="required:true"  name="name" ></input></td>
			    		</tr>
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MPLANTIME"/>：</font><input id="planTime" class="easyui-datebox"  data-options="required:true" name="planTime"></input></td>
			    		</tr>
			    		<tr>
			    			<td><font style="display:inline-block;width:100px"><spring:message code="MACTTIME"/>：</font><input id="actTime" class="easyui-datebox"  name="actTime"></input></td>
			    			<input type="hidden" name="id" id="id">
			    			<input type="hidden" name="updateUser" value="${userId }">
			    		</tr>
			    	</table>
			    </form>
			    <div style="text-align:center;padding:5px">
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm1()"><spring:message code="MBUTTONSUBMIT"/></a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()"><spring:message code="MBUTTONREST"/></a>
			    </div>
		    </div>
		</div>
	</div>
	
	<form id="export" action="${path }/app/mileStone/export.action">
		<input type="hidden" name="projId" value="${projId }">
		<input id="exportVersion" type="hidden" name="version">
	</form>
</body>
</html>