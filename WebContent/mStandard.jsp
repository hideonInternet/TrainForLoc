<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="adminFileService"
	class="com.jxyj.train.adminservice.AdminFileService" scope="page"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩管理</title>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<script>
	function addLine() {
		var trs = document.getElementsByTagName('tr');
		var trObj = trs[trs.length - 2];
		var th = trObj.getElementsByTagName('th')[0].innerHTML;
		var newNu = th.substr(0, 1).charCodeAt(0) + 1;
		newNu = String.fromCharCode(newNu);
		var newLine = document.createElement('tr');

		var newTh = document.createElement('th');
		newTh.innerHTML = newNu + ':';

		var newTd1 = document.createElement('td');
		var newInput1 = document.createElement('input');
		newInput1.setAttribute('type', 'text');
		newInput1.setAttribute('name', 'stdContent');
		newTd1.appendChild(newInput1);

		var newTd2 = document.createElement('td');
		var newLabel1 = document.createElement('label');
		newLabel1.innerHTML = '分值';
		var newInput2 = document.createElement('input');
		newInput2.setAttribute('type', 'number');
		newInput2.setAttribute('name', 'grade');
		newTd2.appendChild(newLabel1);
		newTd2.appendChild(newInput2);

		var newTd3 = document.createElement('td');
		var newA = document.createElement('a');
		newA.innerHTML = "删除";
		newA.onclick = function() {
			return deleteTr();
		}
		newA.setAttribute("href", "#");
		newTd3.appendChild(newA);

		newLine.appendChild(newTh);
		newLine.appendChild(newTd1);
		newLine.appendChild(newTd2);
		newLine.appendChild(newTd3);

		insertAfter(newLine, trObj);
	}

	function insertAfter(newElement, targetElement) {
		//增加功能，判断是否为元素节点，如果不是，则返回false
		if (newElement.nodeType != 1)
			return false;
		if (targetElement.nodeType != 1)
			return false;
		parentNode = targetElement.parentNode;
		if (parentNode.lastChild == targetElement) {
			targetElement.appendChild(newElement);
		} else {
			parentNode.insertBefore(newElement, targetElement.nextSibling);
		}
	}
	function saveStd() {
		if (checkInput()) {
			var form = $("form[name=stdForm]");
			var options = {
				type : "post",
				url : "${conPath}/adminGradeManage.do",
				success : function(data) {
					var jsondata = eval("(" + data + ")");
					var message = jsondata.message;
					alert(message);
					window.location.href = '${conPath}/mGrade.jsp';
				}
			};
			form.ajaxSubmit(options);
		}
		return false;
	}
	function deleteTr() {
		<!--找到改删除的tr标签-->
		var trs = document.getElementsByTagName('tr');
		var trObj = trs[trs.length - 2];
		trObj.parentNode.removeChild(trObj);
		return false;
	}
	function checkInput() {
		var flag=true;
		$('input[name=grade]').each(function() {
			if (isNaN($(this).val())) {
				alert('评分不合法，请重新输入');
				flag=false
				return flag;
			}
		});
		return flag;
	}
</script>
</head>
<body>
	<form action="${conPath}/adminGradeManage.do" method="post"
		name="stdForm">
		<input type="hidden" name="fileId" value=${param.fileId } />
		<table class="table">
			<thead>
				<tr id="firstLine">
					<th>作业</th>
					<td><c:forEach items="${adminFileService.allFile }" var="file">
							<c:if test="${file.fileId eq param.fileId}">
								${fn:substring(file.fileName,fn:indexOf(file.fileName,'_')+1,fn:indexOf(file.fileName,'.')) }
							</c:if>
						</c:forEach></td>
					<th>操作人</th>
					<td>${sessionScope.logStatus }</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th>序号</th>
					<th colspan="2">内容</th>
					<th>操作</th>
				</tr>
				<tr>
					<th>A:</th>
					<td><input name="stdContent" type="text" /></td>
					<td><label>分值</label><input name="grade" type="text" /></td>
					<td><a></a></td>
				</tr>
			</tbody>
			<tr>
				<th colspan="4" align="right"><input type="button" value="增加一行"
					onclick="addLine()"> <input type="submit" value="保存"
					onclick="return saveStd()" /></th>
			</tr>
		</table>
	</form>
</body>
</html>