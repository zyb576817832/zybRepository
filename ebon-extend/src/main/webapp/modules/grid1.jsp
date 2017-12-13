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

<script type="text/javascript">
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
});
	
$("#singleSearchDialog").dialog({   // (1)创建自定义查询对话框  
    autoOpen: false,  
    modal: true,  
    resizable: true,      
    width: 350,  
    title: "自定义单条件查询",  
    buttons: {  
        "查询": singleSearch      // (2)在对话框中添加查询按钮  
    }  
});

var openSingleSearchDialog = function() {  
    $("#singleSearchDialog").dialog("open");  
};  
var resetSingleSearchDialog = function() {  
    $("radio","#singleSearchDialog").attr("checked", false);  
    $(":text","#singleSearchDialog").val("");  
};

var singleSearch = function() {  
    var sdata = {   // (3)构建查询需要的参数  
        searchField: $(":radio:checked", "#singleSearchDialog").val(),  
        searchString: $("#searchString", "#singleSearchDialog").val(),  
        searchOper: $("#searchOper", "#singleSearchDialog").val()  
    };  
      
    // (4)获得当前postData选项的值  
    var postData = $("#gridTable").jqGrid("getGridParam", "postData");  
      
    // (5)将查询参数融入postData选项对象  
    $.extend(postData, sdata);  
      
    $("#gridTable").jqGrid("setGridParam", {  
        search: true    // (6)将jqGrid的search选项设为true  
    }).trigger("reloadGrid", [{page:1}]);   // (7)重新载入Grid表格，以使上述设置生效  
      
    $("#singleSearchDialog").dialog("close");  
};  
var clearSearch = function() {  
    var sdata = {   // (8)构建一套空的查询参数  
        searchField: "",  
        searchString: "",  
        searchOper: ""  
    };  
      
    var postData = $("#gridTable").jqGrid("getGridParam", "postData");  
      
    $.extend(postData, sdata);  //(9)将postData中的查询参数覆盖为空值  
      
    $("#gridTable").jqGrid("setGridParam", {  
        search: false   // (10)将jqGrid的search选项设为false  
    }).trigger("reloadGrid", [{page:1}]);  
      
    resetSingleSearchDialog();  
};  
</script>

</head>
<body>
	<h3>jqGrid测试 09</h3>
	<div id="mysearch"></div>
	<br />
	<div>
		<table id="gridTable"></table>
		<div id="gridPager"></div>
	</div>
	<br />
	<div>
		<button onclick="openSingleSearchDialog()">自定义单条件查询</button>
		<button onclick="clearSearch()">重置</button>
	</div>

	<div id="singleSearchDialog">
		<table class="formTable">
			<thead>
				<tr>
					<th>查询条件</th>
					<th>查询方式</th>
					<th>查询值</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="radio" name="searchField" value="id">编码</input><br />
						<input type="radio" name="searchField" value="lastName">姓</input><br />
						<input type="radio" name="searchField" value="firstName">名</input>
					</td>
					<td><select id="searchOper">
							<option value="eq">等于</option>
							<option value="gt">大于</option>
							<option value="lt">小于</option>
					</select></td>
					<td><input type="text" id="searchString"></input></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>