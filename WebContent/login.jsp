<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="random" class="java.util.Random" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="cache-control" content="no-cache"/>
 <meta http-equiv="expires" content="0"/>  
<title>登入页面</title>
<link
	href="${pageContext.request.contextPath }/layout_skin/css/style_log.css"
	rel="stylesheet" type="text/css" />
<script>
	String.prototype.trim=function()
	{
	     return this.replace(/(^\s*)|(\s*$)/g,"");
	}
	var message="${error}";
	if(message!="")
		alert(message);
	
	function refresh(obj){
		obj.src = "ValidateCode?" + Math.random(); 
	}
	function mouseover(obj){ 
	    obj.style.cursor = "pointer"; 
	  } 
</script>
</head>

<body class="login">
	<div class="login_m">
		<div class="login_logo">
			<h4>测试登入</h4>
		</div>
		<div class="login_boder">
			<div class="login_padding" id="login_model">
				<!-- 表单提交的时候，会自动触发form表单的onsubmit() -->
				<form action="${pageContext.request.contextPath }/login.do"
					method="post">
					<h2>用户名：</h2>
					<input type="text" id="username" class="txt_input txt_input2"
						name="username" />
					<h2>密码：</h2>
					<input type="password" name="password" id="userpwd"
						class="txt_input" />
					<div>
						验证码：<input type="text" id="validate" name="validate" maxlength="4"
							style="width: 50px" /> <img
							src="${pageContext.request.contextPath }/ValidateCode" alt="加载失败"
							align="middle" onclick="refresh(this)"
							onmouseover="mouseover(this)" />
					</div>
					<h2 style="display: inline;">身份</h2>
					<label style="margin-left: 15px;" for="i1">管理员</label><input
						type="radio" name="identity" id="i1" value="管理员" /> <label
						for="i2">普通用户</label><input type="radio" name="identity" id="i2"
						value="普通用户" /> <input type="submit" class="sub_button"
						id="button" value="登录" style="opacity: 0.7; float: right;" />
				</form>
			</div>
		</div>
	</div>
	<!--login_padding  Sign up end-->
	</div>
	<!--login_boder end-->
	</div>
	<!--login_m end-->
	<input type="hidden" value="${request.error }"></input>
	<br />
	<br />
	<p align="center">
		<a href="mailto:470954345@qq.com" target="_blank" title="mail">联系我们</a>
	</p>



</body>
</html>