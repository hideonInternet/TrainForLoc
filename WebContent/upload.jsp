<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${conPath }/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${conPath }/js/jquery-form.js"></script>

<title>文件上传</title>
<script type="text/javascript">
	function submitTran(){
		var form=$("form[name=fileUpload]");
		var options ={
				url:"${conPath }/adminUpload",
				type:"post",
				success:function(data){
					var jsondata = eval("("+data+")"); 
	                if(jsondata.error == "0"){  
	                	alert("上传文件成功！");
						window.close();
	                }else{  
	                    var message = jsondata.message;  
	                    alert(message);  
	                }  
					
				}
		};
		form.ajaxSubmit(options);  
	}
</script>
</head>
<body>
 <form id="fileUpload" name="fileUpload" action="${conPath }/adminUpload" method="post" enctype="multipart/form-data">  
        <input type="hidden" name="fileId" value="${param.fileId}"/> 
        <input type="hidden" name="tackle" value="${param.upStandard}"/>
        <input type="hidden" name="operator" value="${sessionScope.logStatus}"/>   
        <div><input type="file" name="file_upload"/></div>  
        <c:if test="${maxSize!=null}">  
            <div style="font: 12">文件最大不能超过${maxSize}MB</div>  
        </c:if>  
        <c:if test="${fileType!=null}">
            <div style="font: 12">文件格式必须是：${fileType}</div>  
        </c:if>
        <div><input type="button" value="上传" id="uploadButton" onclick="submitTran()"/></div>  
    </form>  
</body>
</html>