<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title>flex grid test</title>

<link rel="stylesheet" type="text/css"
	href="${theme}/grid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="${theme}/jqueryui/overcast/jquery-ui-1.8.20.custom.css" />

<script type="text/javascript"
	src="${path}/script/grid/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="${path}/script/grid/jquery-ui-1.8.20.custom.min.js"></script>
<script type="text/javascript"
	src="${path}/script/grid/jquery.jqGrid.min.js"></script>

<!-- 
<style>  
html, body {  
    margin: 0;  
    padding: 0;  
    font-size: 75%;  
}  
</style>  
 -->

<script type="text/javascript">
	//jQuery.extend(jQuery.jgrid.defaults, { altRows:true });
	$(function() {
		$("#gridTable").jqGrid(
				{
					url : '${path}/user/list',
					mtype : 'POST',
					datatype : "json",
					autowidth : true,
					//height: '100%',
					//height: 'auto',
					//colNames:['编号','真实名', '用户名',],
					colModel : [ {
						index : 'employeeId',
						label : '编号',
						name : 'EMPLOYEE_ID',
						sorttype : 'int',
						editable : true
					}, {
						index : 'actualName',
						name : 'ACTUAL_NAME',
						editable : true
					}, {
						index : 'userName',
						name : 'USER_NAME',
						editable : true
					}, ],
					sortname : 'EMPLOYEE_ID',
					sortorder : 'asc',
					viewrecords : true,
					emptyrecords : '记录为空！',
					prmNames : {
						search : "search"
					},
					rowNum : 30,
					rowList : [ 100, 200, 300 ],
					//scroll:true,
					jsonReader : {
						root : "dataList",
						repeatitems : false
					},
					pager : "#gridPager",
					caption : "jqGrid测试",
					//footerrow : true,
					hidegrid : true,
					//multiselect: true,
					multiselectWidth : 40,
					//pginput: false,
					//loadonce: true,
					loadtext : '数据加载中......',
					//grouping:true, 
					//groupingView : { groupField : ['EMPLOYEE_ID'] },
					//treeGrid: true, ExpandColumn : 'EMPLOYEE_ID',
					editurl : '${path}/user/update',
					onSelectRow : function(rowid) {
						//alert(rowid);
					}
				}).navGrid('#gridPager', {});
		$("#gridTable").jqGrid('filterToolbar', {
			autosearch : true
		});
		
		// 配置对话框   
	    $("#consoleDlg").dialog({  
	        autoOpen: false,      
	        modal: true,    // 设置对话框为模态（modal）对话框   
	        resizable: true,      
	        width: 480,  
	        buttons: {  // 为对话框添加按钮   
	            "取消": function() {$("#consoleDlg").dialog("close")},  
	            "创建": addContact,  
	            "保存": updateContact,  
	            "删除": deleteContact  
	        }  
	    });   

	});

	var echoSelRow = function() {
		var id = $("#gridTable").jqGrid("getGridParam", "selrow");

		alert("当前选中行ID：" + id);
	};
	var getContact = function() {
		var selectedId = $("#gridTable").jqGrid("getGridParam", "selrow");

		var rowData = $("#gridTable").jqGrid("getRowData", selectedId);

		alert("First Name: " + rowData.firstName);
	};
	var addContact = function() {
		var selectedId = $("#gridTable").jqGrid("getGridParam", "selrow");

		var dataRow = {
			id : 99,
			lastName : "Zhang",
			firstName : "San",
			email : "zhang_san@126.com",
			telNo : "0086-12345678"
		};

		if (selectedId) {
			$("#gridTable").jqGrid("addRowData", 99, dataRow, "before",
					selectedId);

		} else {
			$("#gridTable").jqGrid("addRowData", 99, dataRow, "first");

		}
	};
	var updateContact = function() {
		var selectedId = $("#gridTable").jqGrid("getGridParam", "selrow");

		var dataRow = {
			lastName : "Li",
			firstName : "Si",
			email : "li_si@126.com"
		};

		var cssprop = {
			color : "#FF0000"
		};

		$("#gridTable").jqGrid('setRowData', selectedId, dataRow, cssprop);
	};
	var deleteContact = function() {
		var selectedId = $("#gridTable").jqGrid("getGridParam", "selrow");
		$("#gridTable").jqGrid('delRowData', selectedId);
	};
	var changeGridOptions = function() {
		$("#gridTable").jqGrid("setGridParam", {
			rowNum : 50,
			height: 'auto'
		}).trigger('reloadGrid');
		$("#gridTable").jqGrid("setCaption", "Contact List").trigger('reloadGrid');
		alert($("#gridTable").jqGrid("getGridParam", "caption"));
		alert($("#gridTable").jqGrid("getGridParam", "rowNum"));
	};
	var resetWidth = function() {
		$("#gridTable").jqGrid("setGridWidth", 300, false);
		$("#gridTable").jqGrid("setGridHeight", 700);
	};
	
	var openDialog4Adding = function() {  
	    var consoleDlg = $("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	    consoleDlg.find("input").removeAttr("disabled").val("");  
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('创建')").show();  
	    consoleDlg.dialog("option", "title", "创建新联系人").dialog("open");  
	};  
	var openDialog4Updating = function() {  
	    var consoleDlg = $("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	      
	    consoleDlg.find("input").removeAttr("disabled");  
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('保存')").show();  
	    consoleDlg.dialog("option", "title", "修改联系人信息");  
	      
	    loadSelectedRowData();  
	}  
	var openDialog4Deleting = function() {  
	    var consoleDlg = $("#consoleDlg");  
	    var dialogButtonPanel = consoleDlg.siblings(".ui-dialog-buttonpane");  
	      
	    consoleDlg.find("input").attr("disabled", true);  
	    dialogButtonPanel.find("button:not(:contains('取消'))").hide();  
	    dialogButtonPanel.find("button:contains('删除')").show();  
	    consoleDlg.dialog("option", "title", "删除联系人");  
	      
	    loadSelectedRowData();  
	}   
	
</script>

</head>
<body>
	<div id="mysearch"></div>
	<table id="gridTable"></table>
	<div id="gridPager"></div>
	
	<div>  
        <button onclick="openDialog4Adding()">添加联系人</button>  
        <button onclick="openDialog4Updating()">修改联系人</button>  
        <button onclick="openDialog4Deleting()">删除联系人</button>  
    </div> 
	
	<div id="consoleDlg">  
            <div id="formContainer">  
                <form id="consoleForm">  
                    <input type="hidden" id="selectId"/>  
                    <table class="formTable">  
                        <tr>  
                            <th>姓：</th>  
                            <td>  
                                <input type="text" class="textField" id="lastName" name="lastName" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>名：</th>  
                            <td>  
                                <input type="text" class="textField" id="firstName" name="firstName" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>国籍：</th>  
                            <td>  
                                <input type="text" class="textField" id="nationality" name="nationality" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>身份证号：</th>  
                            <td>  
                                <input type="text" class="textField" id="idCardNo" name="idCardNo" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>电子邮箱：</th>  
                            <td>  
                                <input type="text" class="textField" id="email" name="email" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>电话：</th>  
                            <td>  
                                <input type="text" class="textField" id="telNo" name="telNo" />  
                            </td>  
                        </tr>  
                        <tr>  
                            <th>地址：</th>  
                            <td>  
                                <input type="text" class="textField" id="address" name="address" />  
                            </td>  
                        </tr>                       
                    </table>  
                </form>  
            </div>  
        </div>  
	
	
	
	<div>
		<button onclick="echoSelRow()">当前行ID</button>
		<button onclick="getContact()">当前联系人</button>
		<button onclick="addContact()">添加行</button>
		<button onclick="updateContact()">修改行</button>
		<button onclick="deleteContact()">删除行</button>
		<button onclick="changeGridOptions()">改变Grid选项</button>
		<button onclick="resetWidth()">改变Grid宽度</button>
	</div>
</body>
</html>