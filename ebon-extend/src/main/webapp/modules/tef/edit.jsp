<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/meta.jsp"%>
<html>
<head>
<title>报工</title>
<script type="text/javascript">

	function showMsg(title, msg, isAlert) {
		if (isAlert !== undefined && isAlert) {
			$.messager.alert(title, msg);
		} else {
			$.messager.show({
				title : title,
				msg : msg,
				showType : 'show'
			});
		}
	}

	function showProcess(isShow, title, msg) {
		if (!isShow) {
			$.messager.progress('close');
			return;
		}
		var win = $.messager.progress({
			title : title,
			msg : msg
		});
	}

	function submitForm() {
		$('#updateTef').form('submit', {
			url : '${path}/tef/update',
			onSubmit : function() {
				var flag = $(this).form('validate');
				if (flag) {
					showProcess(true, 'Prompting', 'Submitting...');
				}
				return flag;
			},
			success : function(data) {
				showProcess(false);
				if (data == 1) {
					showMsg('Prompting', 'Submit success!');
					if (parent !== undefined) {
						if ($.isFunction(window.reloadParent)) {
							reloadParent.call();
						} else {
							parent.$("#tefTable").datagrid('reload');
							parent.closeMyWindow();
						}
					}
				} else {
					$.messager.alert('Prompting', data);
				}
			},
			onLoadError : function() {
				showProcess(false);
				$.messager.alert('Prompting', 'Submit fail because the network is busy! Please retry!');
			}
		});
	}
</script>
</head>
<body>
	<div
		style="background: #fafafa; padding: 10px; width: 530px; height: 325px;">
		<form id="updateTef" method="post" validate>
			<table align="center"
				style="width: 400px; height: auto; margin-top: 20px">
				<input type="hidden" name="tefId" value="${tefInfo.tefId}" />
				<tr>
					<td align="right">costCenterName：</td>
					<td><input class="easyui-combobox" name="costCenterId"
						url="${path}/costCenter/getListByIsTEF?istef=1" valueField="id" editable="false"
						textField="name" panelHeight="auto" required="true" value="${tefInfo.costCenterId}"
						missingMessage="NOT NULL"></td>
				</tr>
				<tr>
					<td align="right">hnte：</td>
					<td><input class="easyui-combobox" name="hnteId"
						url="${path}/dict/getListByType?type=HNTE" valueField="id"
						editable="false" textField="name" panelHeight="auto" value="${tefInfo.hnteId}"
						required="true" missingMessage="NOT NULL"></td>
				</tr>
				<tr>
					<td align="right">regDate：</td>
					<td><input style="width: 200px" id="regDate" name="regDate" value="${tefInfo.regDate}"
						class="easyui-datebox" required="true" validType="date" editable="false"
						missingMessage="NOT NULL"></input></td>
				</tr>
				<tr>
					<td align="right">workHour：</td>
					<td><input id="workHour" name="workHour" style="width: 200px" value="${tefInfo.workHour}"
						valueField="number" class="easyui-numberbox" required="true" missingMessage="NOT NULL" validType="length[1,6]"/></td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<a class="easyui-linkbutton" iconcls="icon-save" href="javascript:void(0)" onclick="javascript:submitForm();">Save</a>
						<a class="easyui-linkbutton" href="javascript:void(0)" iconCls="icon-cancel" onclick="parent.closeMyWindow();">Close</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>