package com.fly.filter;


import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;


public class ForceLogoutFilter extends AccessControlFilter {
	
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {  
       Session session = getSubject(request, response).getSession(false);       
        if(session == null) {  //当未登录时，不进行处理
            return true;  
        }  
        return session.getAttribute("LOGOUT") == null;  
    }  
		
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {  
        try {  
        	//SecurityUtils.getSubject().logout();
            getSubject(request, response).logout();//强制退出  
        } catch (Exception e) {/*ignore exception*/}  
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forcelogout=1";  
        WebUtils.issueRedirect(request, response, loginUrl);  
        return false;  
    }
   
	
	
}
