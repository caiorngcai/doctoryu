package com.cai.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cai.domian.User;
import com.cai.factory.BasicFactory;
import com.cai.service.UserService;
import com.cai.util.MD5Utils;

public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service=BasicFactory.getFactory().GetInstance(UserService.class);
		//获取用户名密码
		String username= request.getParameter("username");
		String password=MD5Utils.md5(request.getParameter("password"));
		//调用service层根据用户名密码查找用户的方法
		User user=service.findUserByNameAndPsw(username,password);
		//用户不存在
		if(user==null)
		{
			request.setAttribute("msg","用户名密码不正确！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}
		//用户未激活
		if(user.getState()==0){
			request.setAttribute("msg","用户尚未激活，不能登录！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}//用户登录
		request.getSession().setAttribute("user",user);
		//记住用户名
		if("true".equals(request.getParameter("rename"))){
			Cookie renameC=new Cookie("rename",URLEncoder.encode(user.getUsername(),"utf-8"));
			renameC.setPath("/");
			renameC.setMaxAge(3600*24*30);
			response.addCookie(renameC);
		}else{
			Cookie remnameC = new Cookie("remname","");
			remnameC.setPath("/");
			remnameC.setMaxAge(0);
			response.addCookie(remnameC);
		}
		//30天自动登录
		if("true".equals(request.getParameter("autologin"))){
			Cookie autologinC=new Cookie("autologin",URLEncoder.encode(user.getUsername()+":"+user.getPassword(),"utf-8"));
			autologinC.setPath("/");
			autologinC.setMaxAge(3600*24*30);
			response.addCookie(autologinC);
		}
		response.sendRedirect("/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
