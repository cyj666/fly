package com.fly.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
/**
 * 在web.xml中注册
 * @author DELL
 *容器监听器，以后扩展用。
 */
public class TomcatListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(TomcatListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		log.debug("--------------Tomcat服务器开始启动-----------------");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		log.debug("--------------Tomcat服务器即将关闭-----------------");
	}

}
