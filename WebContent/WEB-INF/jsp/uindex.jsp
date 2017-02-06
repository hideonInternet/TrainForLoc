<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户页面</title>
<c:set value="${pageContext.request.contextPath }" var="conPath"
	scope="application"></c:set>
<link href="${applicationScope.conPath }/layout_skin/css/index.css"
	rel="stylesheet" type="text/css" />
<script>
	window.onload = function() {
		document.getElementById("myframe").style.width = document.body.clientWidth
				- 100 + "px";
	}
	function confirmQuit() {
		if (confirm("您确定要退出吗?")) {
			return true;
		} else
			return false;
	}
</script>
</head>
<body>
	<div class="container">
		<div class="header">
			<a><img
				src="${applicationScope.conPath }/layout_skin/images/header.png"
				width="100%" alt="在此处插入徽标" name="Insert_logo" height="90"
				id="Insert_logo" style="background-color: #000000; display: block;" /></a>
			<!-- end .header -->
		</div>
		<div class="sidebar1">
			<ul class="nav">
				<li><a href="#" target="display">查看通知</a></li>
				<li><a href="#" target="display">个人信息管理</a></li>
				<li><a href="${applicationScope.conPath }/dividePage.do" target="display">个人作业管理</a></li>
				<li><a href="${conPath }/scoreDividePage?userId=${sessionScope.userId }" target="display">个人成绩管理</a></li>
				<li><a href="${applicationScope.conPath }/logout.do"
					id="safetyquit" onclick="return confirmQuit()">${sessionScope.logStatus }，点此安全退出</a></li>
			</ul>
			<!-- end .sidebar1 -->
		</div>
		<div class="content">
			<iframe name="display" id="myframe" src="${applicationScope.conPath }/dividePage.do"></iframe>
			<!-- end .content -->
		</div>
		<div class="footer">
			<p>此 .footer 包含声明 position:relative，以便为 .footer 指定 Internet
				Explorer 6 hasLayout，并使其以正确方式清除。如果您不需要支持 IE6，则可以将其删除。</p>
			<!-- end .footer -->
		</div>
		<!-- end .container -->
	</div>
</body>
</html>
