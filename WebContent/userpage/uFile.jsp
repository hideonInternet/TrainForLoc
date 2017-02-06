<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<script type="text/javascript">
	function uploadAnswer_(url){
		 var win = window.open(url,"newwindow","height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
			return false;
	}
	var message="${param.message}";
	if(message!=""){
		alert(message);
	}
	function tackle(url){
		$.ajax({  
	        type : "get",  //提交方式  
	        url : url,//路径 
	        error: function(XMLHttpRequest, textStatus, e) {
	        	alert(e);
	        	console.log("oilDetection.js  method exportOilDetection" + e);
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
<title>个人作业管理</title>
</head>
<body>
	<table class="table" >
  	<tbody>
  		<tr>
			<th>作业名</th><th>上传时间</th><th>上传者</th><th colspan="3">操作</th>
  		</tr>
  		<c:forEach items="${sessionScope.pageBean.pageData }" var="file" varStatus="sta">
  			<tr>
  				<td>${fn:substringAfter(file.fileName,'_')}</td>
  				<td>${file.uploadTime }</td>
  				<td>${file.operator }</td>
  				<c:url value="/adminService" var="download">
  					<c:param name="fileId" value="${file.fileId }"></c:param>
  					<c:param name="tackle" value="download"></c:param>
  				</c:url>
  				<td><a href="${download }" title="下载" >下载</a></td>
  				<c:url value="/userpage/upload.jsp" var="uploadAns">
  					<c:param name="fileId" value="${file.fileId }"></c:param>
  					<c:param name="tackle" value="uploadAns"></c:param>
  				</c:url>
  				<td><a href="${uploadAns }" title="上传作业" onclick="return uploadAnswer_('${uploadAns}')">上传作业</a></td>
  				<c:url value="/downloadStaAns" var="downloadSta">
  					<c:param name="fileId" value="${file.fileId }"></c:param>
  					<c:param name="tackle" value="downloadSta"></c:param>  				</c:url>
  				<td><a href="${downloadSta }" title="下载标准答案" >下载标准答案</a></td>
  			</tr>
  		</c:forEach>
  		<tr><td colspan="6">
  			当前${sessionScope.pageBean.currentPage }/${sessionScope.pageBean.totalPage }页     &nbsp;&nbsp;		
  				<a href="${pageContext.request.contextPath }/dividePage.do?currentPage=1">首页</a>
  				<a href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.currentPage-1}">上一页 </a>
  				<a href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.currentPage+1}">下一页 </a>
  				<a href="${pageContext.request.contextPath }/dividePage.do?currentPage=${sessionScope.pageBean.totalPage}">末页</a>
  		</td></tr>
  	</tbody>
  </table>
</body>
</html>