<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   	<h1>大夫网</h1>
  </head>
  
  <body>
  <c:if test="${sessionScope.user==null}"> 
  	欢迎光临，游客 
  	<a href="/regist.jsp">注册</a>
  	<a href="/login.jsp">登陆</a>
  </c:if>
  <c:if test="${sessionScope.user!=null}">
  	欢迎回来，${sessionScope.user.username}
  	<a href="/LogoutServlet">注销</a><hr>
  	
  		<form action="/UploadServlet" method="POST" enctype="multipart/form-data">
  		病情描述:<input type="text"  name="desc"  height="50"/>
  		患处图片:<input type="file" name="img" />
  		<input type="submit" value="提交"/>
  	</form>
  
  </c:if>
  </body>
</html>
