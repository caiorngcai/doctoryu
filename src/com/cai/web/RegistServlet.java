package com.cai.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import sun.security.provider.MD5;

import com.cai.domian.User;
import com.cai.factory.BasicFactory;
import com.cai.service.UserService;
import com.cai.util.MD5Utils;

public class RegistServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BasicFactory bf=BasicFactory.getFactory();
		UserService service=bf.GetInstance(UserService.class);
		try{
		String valistr1=request.getParameter("valistr");
		String valistr2=(String) request.getSession().getAttribute("valistr");
		//表单校验不通过
		if(valistr1==null||valistr2==null||!valistr1.equals(valistr2)){
			request.setAttribute("msg","<font color='red'>验证码不正确！</font>");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
			User user=new User();

			BeanUtils.populate(user,request.getParameterMap());
			user.setPassword(MD5Utils.md5(user.getPassword()));
			service.regist(user);
			response.getWriter().write("注册成功，请到邮箱激活。。。");
			response.setHeader("Refresh","3;url=/index.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
