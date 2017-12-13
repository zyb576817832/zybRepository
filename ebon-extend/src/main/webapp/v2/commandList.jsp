<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title>Command列表展示</title>
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
        title:'CommandList列表', //标题  
        method:'post',
        iconCls:'', //图标  
        singleSelect:true, //多选  
        height:getHeight(0.88), //高度  
        fitColumns: true, //自动调整各列，用了这个属性，下面各列的宽度值就只是一个比例。  
        striped: true, //奇偶行颜色不同  
        collapsible:true,//可折叠  
        url:"${path}/commandController/commandList.do", //数据来源  
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
            {title:'id', field:'ID', width:100, sortable: true, align: "left",
        		formatter:function(value,row,index){return row.ID;}},
        {title:'type', field:'TYPE', width:150, sortable: true, align: "left",
        		formatter:function(value,row,index){
            			if(row.TYPE==0){
            				return '自动';
            			}else{
            				return '手动';
            			}
        			}}, 
        {title:'状态', field:'STATUS', width:150, sortable: true, align: "left",
	            formatter:function(value,row,index){
		            	if(row.STATUS==0){
            				return '失败';
            			}else{
            				return '成功';
            			}
	            	}},
		{title:'开始日期', field:'STARTDATE', width:150, sortable: true, align: "left",
	            formatter:function(value,row,index){return row.STARTDATE;}},
	   	{title:'结束日期', field:'SENDEDTIME', width:150, sortable: true, align: "left",
				formatter:function(value,row,index){return row.SENDEDTIME;}},
	    {title:'描述', field:'DESCRIPTION', width:150, sortable: true, align: "left",
		        formatter:function(value,row,index){return row.DESCRIPTION;}},	
		{title:'详细描述', field:'COMMENTS', width:150, sortable: true, align: "left",
		        formatter:function(value,row,index){return row.COMMENTS;}},
		{title:'系统', field:'SYS', width:150, sortable: true, align: "left",
			    formatter:function(value,row,index){
			    	if(row.SYS==0){
			    		return 'UCS';
			    	}else{
			    		return 'LIMS';
			    	}
			    }}
        ]],   //styler:function(value,row,index){return "${theme}/easyui/icons/undo.png";},
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
	<div style="padding: 10" id="tabdiv">
		<table id="tefTable"></table>
	</div>	
</body>
</html>
