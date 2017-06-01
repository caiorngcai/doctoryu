package com.cai.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cai.factory.BasicFactory;
import com.cai.service.UserService;

public class ActiveServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserService service=BasicFactory.getFactory().GetInstance(UserService.class);
		//获取激活码
		String activecode=request.getParameter("activecode");
	
		//调用service层激活用户的方法
		service.activeUser(activecode);
		//提示返回
		response.getWriter().write("恭喜您激活成功，三秒后返回主页。。。");
		response.setHeader("Refresh","3;url=/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//刚开始在这里写代码，由于是get方式提交数据，相当于什么都没做，故页面没反应。
		doGet(request, response);
	}

}
