package com.cai.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodeFilter implements Filter{
	private ServletContext context=null;
	private String encode=null;
	private FilterConfig config=null;
	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//响应乱码解决，比较容易，一句代码
		response.setCharacterEncoding(encode);//这句可有可无
		response.setContentType("text/html;charset="+encode);
		//请求乱码解决:
		//利用装饰设计模式改变request对象和获取请求参数相关的方法,从而解决请求参数乱码问题
		chain.doFilter(new MyHttpServletRequest((HttpServletRequest)request), response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config=filterConfig;
		this.context=filterConfig.getServletContext();
		this.encode=context.getInitParameter("encode");
	}
	private  class MyHttpServletRequest extends HttpServletRequestWrapper{
		private HttpServletRequest request=null;
		private boolean isNotEncode=true;
		public MyHttpServletRequest(HttpServletRequest request) {
			super(request);
			this.request=request;
		}
		@Override
		public Map<String,String[]> getParameterMap() {
			try{
				if(request.getMethod().equalsIgnoreCase("POST")){
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
					//主要是GET请求比较麻烦
				}else if(request.getMethod().equalsIgnoreCase("GET")){
					Map<String,String[]> map = request.getParameterMap();
					if(isNotEncode){
						for(Map.Entry<String, String[]> entry : map.entrySet()){
							String [] vs = entry.getValue();
							for(int i=0;i<vs.length;i++){
								vs[i] = new String(vs[i].getBytes("iso8859-1"),encode);
							}
						}
						isNotEncode = false;
					}
					return map;
				}else{
					return request.getParameterMap();//其他请求方式
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		@Override
		public String[] getParameterValues(String name) {
			
			return getParameterMap().get(name);
		}
		@Override
		public String getParameter(String name) {
			return getParameterValues(name)==null?null:getParameterValues(name)[0];//防止因为乱传参数返回空指针
		}
	}
}


