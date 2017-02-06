<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看成绩</title>
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$().ready(
					function() {
						if ("${requestScope.error }" == "false") {
							alert("该生还未有任何成绩记录");
							if("${sessionScope.identity}"=="管理员"){
								window.location.href = "${applicationScope.conPath }/userDividePage.do";	
							}else{
								window.location.href="${applicationScope.conPath }/dividePage.do";
							}
						}
					})
	function backUserM(){
		if("${sessionScope.identity}"=="管理员"){
			window.location.href = "${applicationScope.conPath }/userDividePage.do";	
		}else{
			window.location.href="${applicationScope.conPath }/dividePage.do";
		}
	}
</script>
</head>
<body>
	<label>学生姓名：${requestScope.userName }</label>
	<c:forEach items="${requestScope.map }" varStatus="var" var="entry">
		<div>
			<label>${fn:substring(entry.key,fn:indexOf(entry.key,'_')+1,fn:indexOf(entry.key,'.'))  }</label>
			<table>
				<tr>
					<th>序号</th>
					<th>评分内容</th>
					<th>满分</th>
					<th>得分</th>
				</tr>
				<c:forEach items="${entry.value }" var="value" varStatus="var2">
					<tr>
						<td><em>${var2.count }</em></td>
						<td>${value.stdContent }</td>
						<td>${value.grade }</td>
						<td>${value.score }</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<hr>
	</c:forEach>
	<input type="button" value="返回管理页面" onclick="backUserM()"/>
</body>
</html>