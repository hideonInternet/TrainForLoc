<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<script>
	function openUpload_(url1) {
		var win = window
				.open(
						url1,
						"newwindow",
						"height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		return false;
	}
	function showSubButton() {
		if (document.getElementById("homework").value != "")
			document.getElementById("subButton").disabled = false;
	}
	var message = "${param.message}";
	if (message != "") {
		alert(message);
	}

	function tackle(url) {
		$.ajax({
			type : "get", //提交方式  
			url : url,//路径 
			success : function(data) {
				if (isJson(data)) {
					var jsondata = eval("(" + data + ")");
					if (jsondata.error == 1) {
						var message = jsondata.message;
						alert(message);
					}
				}
			}
		});
		return false;
	}

	function isJson(obj) {
		var isjson = typeof (obj) == "object"
				&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
				&& !obj.length;
		return isjson;
	}
</script>
</head>
<body>
	<table class="table">
		<tbody>
			<tr>
				<th>作业名</th>
				<th>上传时间</th>
				<th>上传者</th>
				<th colspan="4">操作</th>
			</tr>
			<c:forEach items="${sessionScope.pageBean.pageData }" var="file"
				varStatus="sta">
				<tr>
					<td>${fn:substringAfter(file.fileName,'_')}</td>
					<td>${file.uploadTime }</td>
					<td>${file.operator }</td>
					<c:url value="/adminService" var="delete">
						<c:param name="fileId" value="${file.fileId }"></c:param>
						<c:param name="tackle" value="delete"></c:param>
					</c:url>
					<td><a href="${delete }" title="删除">删除</a></td>
					<c:url value="/adminService" var="download">
						<c:param name="fileId" value="${file.fileId }"></c:param>
						<c:param name="tackle" value="download"></c:param>
					</c:url>
					<td><a href="${download }" title="下载" >下载</a></td>
					<c:url value="/adminService" var="update">
						<c:param name="fileId" value="${file.fileId }"></c:param>
						<c:param name="tackle" value="update"></c:param>
						<c:param name="fileName" value="${file.fileName }"></c:param>
					</c:url>
					<td><a href="${update }" title="更新">更新</a></td>
					<!-- 使用c标签生成url，自带项目名contentPath -->
					<c:url value="/upload.jsp" var="upStandard">
						<c:param name="fileId" value="${file.fileId }"></c:param>
						<c:param name="tackle" value="upStandard"></c:param>
					</c:url>
					<td><a href="#" title="上传标准答案"
						onclick="return openUpload_('${upStandard}')">上传标准答案</a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7">当前${sessionScope.pageBean.currentPage }/${sessionScope.pageBean.totalPage }页
					&nbsp;&nbsp; <a
					href="${pageContext.request.contextPath }/dividePage.do?currentPage=1">首页</a>
					<a
					href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.currentPage-1}">上一页
				</a> <a
					href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.currentPage+1}">下一页
				</a> <a
					href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.totalPage}">末页</a>
				</td>
			</tr>
			<tr>
				<td colspan="7">
					<form action="${conPath }/adminUploadFile" method="post"
						enctype="multipart/form-data">
						<input type="file" name="homework" id="homework"
							onchange="showSubButton()" /> <input type="submit" value="上传作业"
							disabled="disabled" id="subButton" />
					</form>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>