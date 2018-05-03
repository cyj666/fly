package com.fly.filter;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fly.sessionDao.RedisDb;
import com.fly.sessionDao.RedisSessionDao;

/**
 * 单点登录？
 * @author DELL
 *
 */
public class SSOFilter extends AccessControlFilter{
	
	/*@Autowired
	private RedisDb redisDb;*/
	
	@Autowired
	RedisSessionDao redisSessonDao;
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Subject subject = getSubject(request, response);
		if (subject==null) {
			return false;
		}
		return super.preHandle(request, response);
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		/*Session session = getSubject(request, response).getSession(false);
		if (session != null) {  //只处理未登录时的情景，已登陆则跳过
			System.out.println(session.getId());
			return true;
		}*/
		
		return false;
	}

	
	//未登录时进行处理：从redis以sid为key取session查看是否已登陆,并继续传递下去
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request2 = (HttpServletRequest) request;
		Cookie[] cookie = request2.getCookies();
		String sid = null;
		if(cookie==null) {
			return true;
		}
		for (Cookie cookie2 : cookie) {
			System.out.println(cookie2.getName()+":"+cookie2.getValue());
			if (cookie2.getName().equals("sid")) {
				sid = cookie2.getValue();
			}
		}
		
		if (sid==null) {
			return true;
		}
		SimpleSession session = (SimpleSession) redisSessonDao.byteToSession(RedisDb.getObject(sid.getBytes()));
		if (session==null) {
			return true;
		}
		System.out.println(session.getId()+"/"+session.getHost()+"/"+session.getTimeout()+"/"+session.getLastAccessTime());
		Map<Object, Object> map = session.getAttributes();
		if (map==null) {
			return true;
		}
		for(Map.Entry<Object, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		System.out.println(session);		
		return true;
	}

}
