<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<title>注册用户</title>
<script type="text/javascript">
	function submitTran() {
		var form = $("form[name=registerUser]");
		var options = {
			url : "${conPath }/registerUser",
			type : "post",
			success : function(data) {
				var jsondata = eval("(" + data + ")");
				if (jsondata.error == "0") {
					alert(jsondata.message);
					window.opener.location.reload();
					window.close();
				} else {
					var message = jsondata.message;
					alert(message);
				}

			}
		};
		form.ajaxSubmit(options);
	}
</script>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<form action="${conPath }/registerUser" method="post"
		name="registerUser">
		<table class="table">
			<tbody>
				<tr>
					<th>用户名</th>
					<td><input type="hidden" readonly="readonly" name="userId"
						value="${requestScope.pointedUser.userId }" /> <input type="text"
						name="username" value="${requestScope.pointedUser.username }" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input type="password" name="password"
						value="${requestScope.pointedUser.password }" /></td>
				</tr>
				<tr>
					<th>确认密码</th>
					<td><input type="password" name="ensurePw"
						value="${requestScope.pointedUser.password }" /></td>
				</tr>
				<tr>
					<th>电子邮箱</th>
					<td><input type="email" name="email"
						value="${requestScope.pointedUser.email }" /></td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td><input type="text" name="telephone"
						value="${requestScope.pointedUser.telephone }" /></td>
				</tr>
				<tr>
					<th>学校名称</th>
					<td><input type="text" name="schName"
						value="${requestScope.pointedUser.schName }" /></td>
				</tr>
				<tr>
					<th>管理员</th>
					<td background="gray"><input type="text" name="operator"
						readonly="readonly" value="${sessionScope.logStatus }" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" value="注册"
						onclick="submitTran()" /> <input type="reset" value="重置"
						style="float: right" /></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>