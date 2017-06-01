package com.cai.web;

import java.io.IOException;

import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//手动杀死session就可以注销用户
		if(request.getSession(false)!=null){
			request.getSession().invalidate();
			
			//删除自动登录cookie
			Cookie autologinC=new Cookie("autologin","");
			autologinC.setPath("/");
			autologinC.setMaxAge(0);
			response.addCookie(autologinC);
		}
		response.sendRedirect("/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
