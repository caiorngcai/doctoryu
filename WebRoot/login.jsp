<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<script type="text/javascript">
		//由于javascript代码是一行一行解释执行的，故要在页面加载完成后才开始执行此方法，不然会报空指针异常。
			window.onload=function(){
				var str=decodeURI('${cookie.rename.value}');
				document.getElenmentByName("username")[0].vaule=str;
			}
		</script>
  </head>
  
  <body>
    <div align="center">
    <h1>大夫网登陆</h1><hr>
    <font color="red">${msg}</font>
    	<form action="/LoginServlet" method="POST">
    		<table>
    			<tr>
    				<td>用户名:</td>
    				<td><input type="text" name="username" value=""/></td>
    			</tr>
    			<tr>
    				<td>密码:</td>
    				<td><input type="password" name="password"/></td>
    			</tr>
    			<tr>
    				<td>病卡号码:</td>
    				<td><input type="password" name="password"/></td>
    			</tr>
    			<tr>
    				<td><input type="checkbox" name="rename" value="true"
    					<c:if test="${cookie.rename!=null}">
    					checked='checked'
    					</c:if>
    				/>
    				记住用户名</td>
    			<td><input type="checkbox" name="autologin" value="true" />30天内自动登陆</td>
    			</tr>
    			<tr>
  				<td colspan="2"><input type="submit" value="登录" /></td>
  			</tr>
    		</table>
    	</form>
    </div>
  </body>
</html>
