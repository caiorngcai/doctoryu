package com.cai.service;

import java.sql.Connection;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


import com.cai.dao.UserDao;
import com.cai.domian.User;
import com.cai.factory.BasicFactory;
import com.cai.util.DaoUtils;

public class UserServiceImpl implements UserService {
	//利用工厂类获得dao对象
	UserDao dao=BasicFactory.getFactory().GetInstance(UserDao.class);
	
	public void regist(User user) {
			Connection conn=null;
			try {
			conn=DaoUtils.getConn();
				//conn.setAutoCommit(false);
				if(dao.FindUserByName(user.getUsername(),conn)!=null){
				throw new RuntimeException("用户名已经存在");
				}
			
			user.setRole("user");
			user.setState(0);
			user.setAvtivecode(UUID.randomUUID().toString());
			dao.addUser(user,conn);
			//激活邮件的操作
			Properties prop = new Properties();
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.host", "localhost");
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.debug", "true");
			Session session = Session.getInstance(prop);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("aa@cai.com"));
			msg.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
			msg.setSubject(user.getUsername()+",来自doctoryu的激活邮件");
			msg.setText(user.getUsername()+",点击如下连接激活账户,如果不能点击请复制到浏览器地址栏访问:http://www.doctoryu.com/ActiveServlet?activecode="+user.getAvtivecode());
			
			Transport trans = session.getTransport();
			trans.connect("aa", "123");
			trans.sendMessage(msg, msg.getAllRecipients());
			}catch (Exception e) {	
				e.printStackTrace();			
			}
	

	}
	public void activeUser(String activecode) {
		User user=dao.findUserByActivecode(activecode);
		if(user==null)
		{
			throw new RuntimeException("激活码错误！");
		}
		if(user.getState()==1){
			throw new RuntimeException("用户已经激活！");
		}
		if(System.currentTimeMillis()-user.getUpdatetime().getTime()>1000*24*3600){
			throw new RuntimeException("激活码已经超时，需要在二十四小时内激活！");
		}
		dao.updateState(user.getId(),1);
	}
	public User findUserByNameAndPsw(String username, String password) {
		return dao.finUserByNameAndPsw(username,password);
	}
}