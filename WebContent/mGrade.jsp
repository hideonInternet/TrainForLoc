<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="adminUserService"
	class="com.jxyj.train.adminservice.AdminUserService"></jsp:useBean>
<jsp:useBean id="adminFileService"
	class="com.jxyj.train.adminservice.AdminFileService" scope="page"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<title>成绩上传</title>
<script type="text/javascript">
	function chooseStd(OptionObj) {
		var newLines = document.getElementsByClassName('newLine');

		for (var i = newLines.length - 1; i >= 0; i--) {
			newLines[i].parentNode.removeChild(newLines[i]);
		}
		if (OptionObj.value != 0) {
			$.ajax({
						type : "get",
						data : {
							fileId : $('#fileId').val(),
						},
						url : "${conPath}/standardDividePage",
						success : function(data) {
							var jsondata = eval("(" + data + ")");
							if (jsondata.error < 0) {
								if (confirm(jsondata.message)) {
									var fileId = $('#fileId').val();
									window.location.href = "${conPath}/mStandard.jsp?fileId="
											+ fileId;
								}
							} else {
								createTr(jsondata);
							}
						}
					});
		}
	}
	function createTr(data) {
		var lineNum = data.length;
		var newTr = document.createElement('tr');
		newTr.setAttribute('class', 'newLine');
		var startLine;
		for (var i = 0; i < lineNum; i++) {
			var newTh = document.createElement('th');
			var newTd = document.createElement('td');
			var newTd2 = document.createElement('td');
			var newTd3 = document.createElement('td');

			newTh.innerHTML = String.fromCharCode(65 + i) + ':';
			newTd.innerHTML = data[i].stdContent;
			newTd2.innerHTML = data[i].grade;
			newTd2.setAttribute("class","grade");
			newTd3.innerHTML = '<input type="text" class="score" name="score"/> '
					+ '<input type="hidden" id="stdId" name="stdId" value='+data[i].stdId+' />';

			newTr.appendChild(newTh);
			newTr.appendChild(newTd);
			newTr.appendChild(newTd2);
			newTr.appendChild(newTd3);

			if (i == 0) {
				startLine = document.getElementById('firstLine');
			}
			insertAfter(newTr, startLine);
			startLine = newTr;
			newTr = document.createElement('tr');
			newTr.setAttribute('class', 'newLine');
		}

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
	function upload_grade() {
		if (checkInput()) {
			var form = $("form[name=scroeForm]");
			var options = {
				type : 'post',
				url : '${conPath}/adminUploadScore',
				success : function(data) {
					$('.score').each(function(){
						var obj=$(this);
					});
					var jsondata= eval("(" + data + ")");
					if(jsondata.error>=0){
						alert(jsondata.message);
					}
				}
			};
			form.ajaxSubmit(options);
			
		}
		return false;
	}
	function checkInput() {

		var flag = true;
		if ($('.score').length > 0) {
			$('.score').each(function() {
				if ($(this).val() == "") {
					alert('请完整填写该生分数');
					flag = false;
					return false;
				}
				if(isNaN($(this).val())){
					alert('成绩格式不合法，请重新输入');
					flag = false;
					return false;
				}
			});
		}
		var grades=document.getElementsByClassName("grade");
		var scores=document.getElementsByClassName("score");
		for(var index=0;index<grades.length;index++){	
			var score=parseInt(scores[index].value);
			var grade=parseInt(grades[index].innerHTML);
			if(grade<score){
				alert("录入成绩大于评分标准!");
				flag=false;
				break;
			}
		}
		if ($('#userId').val() == 0) {
			alert('请选择一名学生');
			flag = false;
		} else if ($('#fileId').val() == 0) {
			alert('请选择作业');
			flag = false;
		} 
		return flag;
	}
	
</script>
</head>
<body>
	<form name="scroeForm" action="${conPath }/adminUploadScore"
		method="post">
		<table>
			<thead style="text-align: center;">
				<tr>
					<th><label>作业:</label> <select onchange="chooseStd(this)"
						id="fileId" name="fileId">
							<option value="0">选择作业</option>
							<c:forEach items="${adminFileService.allFile }" var="file">
								<option value="${file.fileId }">${fn:substring(file.fileName,fn:indexOf(file.fileName,'_')+1,fn:indexOf(file.fileName,'.')) }</option>
							</c:forEach>
					</select></th>
					<th><label>学生:</label> <select id="userId" name="userId">
							<option value="0">选择学生</option>
							<c:forEach items="${adminUserService.allUser }" var="user">
								<option value="${user.userId }">${user.username}</option>
							</c:forEach>
					</select></th>
					<th>操作人</th>
					<td>${sessionScope.logStatus }</td>
				</tr>
			</thead>
			<tbody class="table">
				<tr id="firstLine">
					<th>序号</th>
					<th>内容</th>
					<th>满分</th>
					<th>得分</th>
				</tr>
				<tr>
					<td colspan="4" align="right"><input type="submit" value="提交"
						onclick="return upload_grade()" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<hr>
</body>
</html>