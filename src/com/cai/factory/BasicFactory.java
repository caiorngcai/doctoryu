package com.cai.factory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import sun.security.jca.GetInstance;

public class BasicFactory {
	private static BasicFactory factory=new BasicFactory();
	private static Properties prop=null;
	private BasicFactory(){}
	//静态代码块，在类之前加载
	static{
		try {
			prop=new Properties();//记住导入的是util包的
			//返回类加载器，查找具有给定名称的资源，而且资源就在src目录下，编译后在class目录下，故直接写名称就可以
			prop.load(new FileReader(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
			}
	//构造方法，使得要使用工厂时不用new
	public static BasicFactory getFactory() {
		return factory;
	}
	public <T> T GetInstance(Class<T> clazz){
		try{
		String infName=clazz.getSimpleName();//得到要创建类实现的接口的名称
		String implName=prop.getProperty(infName);//通过配置方式得到要创建类的名称
		return (T) Class.forName(implName).newInstance();//通过名称得到class，再通过newinstance方法得到一个实例。
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
