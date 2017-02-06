<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<script type="text/javascript">
function openRegister_(Id){
	var tempUrl;
	if(Id>0){
		tempUrl="${conPath}/modifyInf?userId="+Id;
	}else
		tempUrl="${conPath}/registerUser.jsp";
	var url=tempUrl;
    var win = window.open(url,"newwindow","height=800,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	return false;
}  

function deleteUse(id){
	if(confirm("删除该学生将直接删除该学生上传的所有作业，继续请按确定")){
	$.ajax({  
        type : "get",  //提交方式  
        url : "${conPath}/deleteUser.do",//路径 
        data:{userId:id},
        success : function(data) {//返回数据根据结果进行相应的处理  
        	var jsondata = eval("("+data+")");  
            if(jsondata.error == "0"){  
            	alert(jsondata.message);
            	window.location.reload();
            }else{  
                var message = jsondata.message;  
                alert(message);  
            }  
        }  
    });  
	}
	return false;
}
</script>
<link href="${applicationScope.conPath }/layout_skin/css/mFile.css"
	rel="stylesheet" type="text/css"/>
</head>
<body>
	<table class="table" >
		<tbody>
			<tr>
				<th>学生姓名</th>
				<th>注册院校</th>
				<th>电话</th>
				<th>电子邮箱</th>
				<th>注册人员</th>
				<th>注册时间</th>
				<th colspan="3">操作</th>
			</tr>
			<c:forEach items="${sessionScope.userpageBean.pageData }" var="user"
				varStatus="sta">
				<tr>
					<td>${user.username }</td>
					<td>${user.schName }</td>
					<td>${user.telephone }</td>
					<td>${user.email }</td>
					<td>${user.operator }</td>
					<td>${user.registerTime }</td>
					<td><a href="${conPath}/deleteUser.do" onclick="return deleteUse(${user.userId})">删除</a></td>
					<td><a href="${conPath}/modifyInf" onclick="return openRegister_(${user.userId})">修改信息</a></td>
					<td><a href="${conPath }/scoreDividePage?userId=${user.userId }" >查看成绩</a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="9">当前${sessionScope.userpageBean.currentPage }/${sessionScope.userpageBean.totalPage }页
					&nbsp;&nbsp; <a
					href="${pageContext.request.contextPath }/userDividePage.do?currentPage=1">首页</a>
					<a
					href="${pageContext.request.contextPath }/userDividePage.do?currentPage=${sessionScope.userpageBean.currentPage-1}">上一页
				</a> <a
					href="${pageContext.request.contextPath }/userDividePage.do?currentPage=${sessionScope.userpageBean.currentPage+1}">下一页
				</a> <a
					href="${pageContext.request.contextPath }/userDividePage.do?currentPage=${sessionScope.userpageBean.totalPage}">末页</a>
				</td>
			</tr>
			<tr>
				<td colspan="9"><input type="button" value="注册学生"
					onclick="return openRegister_(-1)" /></td>
			</tr>
		</tbody>
	</table>
</body>
</html>