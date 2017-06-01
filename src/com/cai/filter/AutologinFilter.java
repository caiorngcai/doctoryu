package com.cai.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cai.domian.User;
import com.cai.factory.BasicFactory;
import com.cai.service.UserService;
import com.sun.net.httpserver.Filter.Chain;

public class AutologinFilter implements Filter {

	public void destroy() {
		

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		if(req.getSession(false)==null||req.getSession().getAttribute("user")==null){
			Cookie cs[]=req.getCookies();
			Cookie findC=null;
			if(cs!=null){
				for(Cookie c:cs){
					if("autologin".equals(c.getName())){
						findC=c;
						break;
					}
				}
			}
			if(findC!=null){
				String v=URLDecoder.decode(findC.getValue(),"utf-8");
				String username=v.split(":")[0];
				String password=v.split(":")[1];
				UserService service=BasicFactory.getFactory().GetInstance(UserService.class);
				User user=service.findUserByNameAndPsw(username, password);
				if(user!=null){
					req.getSession().setAttribute("user",user);
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

}
