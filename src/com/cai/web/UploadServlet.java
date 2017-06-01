package com.cai.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.cai.domian.PatientDesc;
import com.cai.domian.User;
import com.cai.factory.BasicFactory;
import com.cai.service.PatientDescService;
import com.cai.service.PatientDescServiceImpl;
import com.cai.util.IOUtils;
import com.cai.util.PicUtils;
import com.sun.corba.se.impl.interceptors.PICurrent;

public class UploadServlet extends HttpServlet {
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String value=null;
		String imgurl=null;
		User user=new User();
		//ProdService service = BasicFactory.getFactory().GetInstance(ProdService.class);
		PatientDescService service=BasicFactory.getFactory().GetInstance(PatientDescService.class);
		try{
			//由于前端不能直接访问web-inf目录下的文件，故要用map保存数据
			Map<String,String> parammap=new HashMap<String, String>();
			String encode=this.getServletContext().getInitParameter("encode");
		//上传文件到servlet
			//1.获取文件上传工厂和和核心类
			DiskFileItemFactory factory=new DiskFileItemFactory();
			factory.setSizeThreshold(1024*100);//设置缓冲区的最大大小
			//设置临时文件的存储路径
			factory.setRepository(new File(this.getServletContext().getRealPath("WEB-INF/temp")));
			ServletFileUpload fileupload=new ServletFileUpload(factory);
			fileupload.setHeaderEncoding(encode);
			fileupload.setFileSizeMax(1024*1024*1);
			fileupload.setFileSizeMax(1024*1024*10);
			
			if(!fileupload.isMultipartContent(request)){
				throw new RuntimeException("用正确的表单的上传！");
			}
			List<FileItem> list=fileupload.parseRequest(request);
			for(FileItem item: list){
				//普通字段，病情描述的内容
				if(item.isFormField()){
					String name=item.getFieldName();
							value=item.getString(encode);
					parammap.put(name, value);
				}else{
					//图片
					String realname=item.getName();//图片的真实名称
					String uuidname=UUID.randomUUID().toString()+"_"+realname;
					String hash=Integer.toHexString(uuidname.hashCode());
					String upload=this.getServletContext().getRealPath("WEB-INF/upload");//upload的绝对路径
					imgurl="/WEB-INF/upload";
					for(char c:hash.toCharArray()){
						//原本路径加上hashcode组成的路径
						upload+="/"+c;
						imgurl+="/"+c;
					}
					imgurl +="/"+uuidname;
					parammap.put("imgurl",imgurl);
					
					File uploadfile=new File(upload);
					if(!uploadfile.exists()){
						uploadfile.mkdirs();
					}
					InputStream in=item.getInputStream();
					OutputStream out=new FileOutputStream(new File(upload,uuidname));//???
					IOUtils.In2Out(in, out);
					IOUtils.close(in, out);
					item.delete();
				
					
				}
			}
			//调用service层添加病情的方法
			//Product prod=new Product();
			PatientDesc patientdesc=new PatientDesc();
			//如果没有map，此处写法为
			//BeanUtils.populate(prod, request.getParameterMap());
			//但是事实上数据在web-inf下，无法访问到
				//这个函数执行不成功，所以直接给bean赋值好了
			//BeanUtils.populate(patientdesc, parammap);
			patientdesc.setDesc(value);
			patientdesc.setImgurl(imgurl);
			patientdesc.setPatientname(user.getUsername());
			//这句调用的sql有问题
			//service.addDesc(patientdesc);
			//提示成功，返回主页
			response.getWriter().write("添加病情成功，3秒后返回主页。。");
			response.setHeader("Refresh","3;url=/index.jsp");
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
