<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title>Tef报工</title>

<script type="text/javascript">

function showConfirm(title, msg, callback) {
	$.messager.confirm(title, msg, function(r) {
		if (r) {
			if (jQuery.isFunction(callback)) {
				callback.call();
			}
		}
	});
}

jQuery(function($){
    $('#tefTable').datagrid({
        title:'TEF_TimeSheet', //标题  
        method:'post',
        iconCls:'', //图标  
        singleSelect:true, //多选  
        height:getHeight(0.88), //高度  
        fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。  
        striped: true, //奇偶行颜色不同  
        collapsible:true,//可折叠  
        url:"${path}/tef/list", //数据来源  
        sortName: 'updateDate', //排序的列  
        sortOrder: 'desc', //倒序  
        remoteSort: true, //服务器端排序  
        idField:'id', //主键字段  
        queryParams:{}, //查询条件  
        pagination:true, //显示分页  
        rownumbers:true, //显示行号  
        columns:[[
            {field:'ck',checkbox:true,width:2}, //显示复选框  
            {title:'ID', field:'tefId', width:80, hidden : true},
            {title:'CostCenter', field:'costCenterName', width:80, sortable: true, align: "center",
            	formatter:function(value,row,index){return row.costCenterName;}},
            {title:'HNTE', field:'hnte', width:80, sortable: true, align: "center",
            	formatter:function(value,row,index){return row.hnte;}},
            {title:'RegDate', field:'regDate', width:80, sortable: true, align: "center",
            	formatter:function(value,row,index){return row.regDate;}},
            {title:'WorkHour', field:'workHour', width:80, sortable: true, align: "center",
            	formatter:function(value,row,index){return row.workHour;}},
            {title:'CreateDate', field:'createDate', width:80, sortable: true, align: "center",
            	formatter:function(value,row,index){return row.createDate;}},
            {title:'UpdateDate', field:'updateDate', width:80, sortable: true, align: "center",
            		formatter:function(value,row,index){return row.updateDate;}}
        ]],   //styler:function(value,row,index){return "${theme}/easyui/icons/undo.png";},
        toolbar:[{
            text:'Add',
            iconCls:'icon-add',
            handler:function(){
                addrow();
            }
        },'-',{
            text:'Update',
            iconCls:'icon-edit',
            handler:function(){
                updaterow();
            }
        },'-',{
            text:'Delete',
            iconCls:'icon-remove',
            handler:function(){
                deleterow();
            }
        },'-'],
        onLoadSuccess:function(){
            $('#tefTable').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题  
        }
    });
      
    $('body').append('<div id="myWindow" class="easyui-dialog" closed="true"></div>');

});  

function showMyWindow(title, href, width, rowData, height, modal, minimizable, maximizable) {
	$('#myWindow').window({
		title: title,
		width: width === undefined ? 600 : width,
		height: height === undefined ? 400 : height,
		content: '<iframe scrolling="yes" frameborder="0"  src="' + href + '" style="width:100%;height:98%;"></iframe>',
		modal: modal === undefined ? true : modal,
		minimizable: minimizable === undefined ? false : minimizable,
		maximizable: maximizable === undefined ? false : maximizable,
		shadow: false,
		cache: false,
		closed: false,
		collapsible: false,
		resizable: false,
		loadingMessage: 'Loading,please wait...'
	});
}

function closeMyWindow() {
	$('#myWindow').window('close');
}

//新增  
function addrow(){
	showMyWindow('TimeSheet', '${jspPath}/tef/add.jsp');
}  
//更新  
function updaterow(){  
    var row = $('#tefTable').datagrid('getSelected');
    //<span style="color: #ff0000;">这里有一个jquery easyui datagrid的一个小bug，必须把主键单独列出来，要不然不能多选</span>  
    if(row){
    	showMyWindow('Update TimeSheet', '${path}/tef/getTefById/' + row.tefId);
    } else {
    	$.messager.alert('Prompting',"Please choose which you want to update!",'info');
        return;
    }
}
  
//删除  
function deleterow(){
	var row = $('#tefTable').datagrid('getSelected');
	if(row) {
		$.messager.confirm('Prompting','Are you sure want to delete?',function(result){
	        if (result){
	            $.post('${path}/tef/delete/'+row.tefId,
	            	function(data){
	            		if(data == 1) {
	            			$('#tefTable').datagrid('reload');
	    	                $.messager.alert('Prompting', 'Delete success！', 'info');
	            		} else {
	            			 $.messager.alert('Prompting', data, 'info');
	            		}
	            });
	        }
	    });
	} else {
    	$.messager.alert('Prompting',"Please choose which you want to delete!",'info');
        return;
    }
}  
//表格查询  
function _search(){  
    var params = $('#tefTable').datagrid('options').queryParams; //先取得 datagrid 的查询参数  
    var fields =$('#queryForm').serializeArray(); //自动序列化表单元素为JSON对象  
    $.each( fields, function(i, field){  
        params[field.name] = field.value; //设置查询参数  
    });   
    $('#tefTable').datagrid('reload'); //设置好查询参数 reload 一下就可以了  
}  
//清空查询条件  
function clearForm(){  
    $('#queryForm').form('clear');  
    searchTef();  
}

function getWidth(percent){  
    return $(window).width() * percent;  
} 

function getHeight(percent){  
    return $(window).height() * percent;  
}

$(window).resize(function(){
	$('#tefTable').datagrid('resize', {
		width: getWidth(0.99),
		height: getHeight(0.88)
	});
});

</script>
</head>

<body>
	<form id="queryForm" style="margin: 10; text-align: center;">
		<table width="100%">
			<tr>
				<td>
					 CostCenter：<input class="easyui-combobox" name="costCenterId" editable="false"
						url="${path}/costCenter/getListByIsTEF?istef=1" valueField="id"
						textField="name" panelHeight="auto">
				</td>
				<td>
					HNTE：<input class="easyui-combobox" name="hnteId"
						url="${path}/dict/getListByType?type=HNTE" valueField="id"
						editable="false" textField="name" panelHeight="auto">
				</td>
				<td align="center">
					<a href="#" onclick="clearForm();" class="easyui-linkbutton" iconCls="icon-cancel">Clear</a>
				</td>
			</tr>
			<tr>
				<td>
					Register StartDate：<input style="width: 200px" id="regStartDate" name="regStartDate" editable="false" class="easyui-datebox"/>
				</td>
				<td>Register EndDate：<input style="width: 200px" id="regEndDate" name="regEndDate" editable="false" class="easyui-datebox"/></td>
				<td align="center">
					<a href="#" onclick="_search();" class="easyui-linkbutton" iconCls="icon-search">Query</a>
				</td>
			</tr>
		</table>
	</form>
	<div style="padding: 10" id="tabdiv">
		<table id="tefTable"></table>
	</div>
	
</body>
</html>
