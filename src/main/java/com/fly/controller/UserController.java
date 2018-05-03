package com.fly.controller;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.SignedInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fly.pojo.Role;
import com.fly.pojo.User;
import com.fly.service.RoleService;
import com.fly.service.UserService;

import cn.hutool.core.date.DateUtil;

@Controller
public class UserController {
	
	public  static final Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired  
	private SessionDAO sessionDAO; 
	
	//@RequiresPermissions(value= {"get:user"})
	//@RequestMapping(value="getAllUser",method=RequestMethod.GET,produces="application/json;charset=utf-8")
	@ResponseBody
	public String getAllUser() {
		//User user = userService.getUserById(1);
		List<User> list = userService.getAllUser();	
		list = userService.getAllUserDetails();
		String json = JSON.toJSONString(list,SerializerFeature.WriteDateUseDateFormat);		
		String JSON = "{\"total\":"+list.size()+",\"rows\":"+json+"}";
		return JSON;
	}
	
	//@RequiresPermissions(value= {"get:user"})
	//@RequestMapping(value="/getUser")
	@ResponseBody
	public String getUser(@RequestParam(value="username",defaultValue="admin")String username) {
		User user = userService.getUserDetails(username);
		String json = JSON.toJSONString(user,SerializerFeature.WriteDateUseDateFormat);
		String JSON = "{\"total\":1"+",\"rows\":"+json+"}";
		return JSON;
		
	}
	
	@RequestMapping(value="user/login",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String login(@RequestParam(value="email")String username,
			@RequestParam(value="pass")String password,
			@RequestParam(value="vercode")String captcha,
			HttpServletRequest request,HttpServletResponse response,Model model) {
		HttpSession session = request.getSession();		 
		String randomString = captcha.toUpperCase();
		String msg = null;
		User user =  new User();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			if (!session.getAttribute("randomString").equals(randomString)) {
				msg="验证码出错！请检查。";
				model.addAttribute("message", msg);	
				String result = "{\"success\":false,\"msg\":\"验证码出错！请检查。\"}";
				return result;
			}

			//默认记住我			
			token.setRememberMe(true);
			token.setHost(request.getRemoteAddr());
			Subject subject = SecurityUtils.getSubject();
		    subject.login(token); 
		    msg="登录成功！登录IP:"+token.getHost();		    
		    user =  userService.getUserDetails(username);
		    user.setLastIp(request.getRemoteAddr());
		    user.setLastTime(new Date());
		    userService.updateUser(user);
	        model.addAttribute("message", msg);	
	        request.getSession().setAttribute("user", user);
	        String result = "{\"status\":0,\"msg\":\""+msg+"。\",\"action\":\"index\"}";
	        log.fatal("用户"+username+"登录成功，时间："+DateUtil.formatDateTime(new Date())
	         );
			return result;  
		} catch (IncorrectCredentialsException e) {  
	        msg ="帐号"+ token.getPrincipal()+"登录密码错误。";  
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (ExcessiveAttemptsException e) {  
	        msg = "登录失败次数过多";  
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (LockedAccountException e) {  
	        msg ="帐号"+ token.getPrincipal() +"已被锁定. " ;  
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (DisabledAccountException e) {  
	        msg = "帐号"+ token.getPrincipal() +"已被禁用";  
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (ExpiredCredentialsException e) {  
	        msg = "帐号"+ token.getPrincipal() +"已过期";
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (UnknownAccountException e) {  
	        msg = "帐号"+ token.getPrincipal() +"不存在";
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (UnauthorizedException e) {  
	        msg = "您没有得到相应的授权！";  
	        model.addAttribute("message", msg);  
	        System.out.println(msg);  
	    } catch (AuthenticationException e) {
			// TODO: handle exception
	    	 msg = "无此账号，验证错误";  
		     model.addAttribute("message", msg);  
		     System.out.println(msg);
		} catch (Exception e) {
			// TODO: handle exception
	    	 msg = "未知错误";  
		     model.addAttribute("message", msg);  
		     System.out.println(msg);
		} 			
		
		String result = "{\"status\":false,\"msg\":\""+msg+"。\"}";
		return result;
		
	}
	
	@RequiresUser
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {  		
		String result = "false";
		String username = "";
		try {
			response.setCharacterEncoding("UTF-8");
			Subject subject = SecurityUtils.getSubject();
			username = (String) subject.getPrincipal();
			subject.logout();
			result = "已成功登出";
		} catch (Exception e) {
			// TODO: handle exception
			result = "登出发生异常";
		}
		log.fatal("用户"+username+result+DateUtil.formatDateTime(new Date()));
	    return "redirect:/?logout=1";
	}  
	
	
	//@RequiresPermissions(value="add:user")
	
	
	//@RequiresPermissions(value="delete:user")
	@RequestMapping(value="deleteUser",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String deleteUser(@RequestParam(value="ids")Integer[] ids) {
		User user = new User();
		for (Integer i : ids) {
			user.setUserId(i);
			userService.deleteUser(user);
			userService.deleteUserRole(i, null);
		}		
		String result;		
		result = "{\"success\":true,\"msg\":\"删除成功\"}";
		log.fatal("用户"+SecurityUtils.getSubject().getPrincipal()+"删除账号"+"，时间："+DateUtil.formatDateTime(new Date()));
		return result;
	}
	
	//@RequiresPermissions(value="update:user")
	@RequestMapping(value="updateUser",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String updateUser(
			@RequestParam(value="userId")Integer userId,
			@RequestParam(value="status")Integer status,
			@RequestParam(value="roleId",required=false)Integer[] roleId) {
		User user = new User();
		user = userService.getUserById(userId);
		user.setStatus(status);
		userService.updateUser(user);
		user = userService.getUserDetails(user.getUsername());
		Set<Role> roles = user.getRoleSet();
		//先从用户角色中间表中删除所有对应关系，在依次添加
		for (Role role : roles) {			
			userService.deleteUserRole(userId, role.getRoleId());
		}
		for (Integer i : roleId) {
			userService.addUserRole(userId, i);
		}
		String result;		
		result = "{\"success\":true,\"msg\":\"修改成功\"}";
		return result;
	}
	
	//@RequiresPermissions(value="update:user")
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST,produces="text/html;charset=utf-8") 
	@ResponseBody
	public String resetPwd(@RequestParam(value="userId")Integer userId) {
		User user = userService.getUserById(userId);
		user.setPassword(new SimpleHash("MD5", "000001").toString());  //自定义初始密码为000001
		userService.updateUser(user);
		return "000001";
	}
	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	@ResponseBody
	 public String processUnauthenticatedException(ServletRequest request,ServletResponse response) {
	 
		String result = "{\"status\":1,\"msg\":\""+"无权"+"。\"}";
		/*try {
			WebUtils.issueRedirect(request, response, "login?kickout=1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return result;    
	    }
	
	@RequestMapping("/{sessionId}/forceLogout") 
	@ResponseBody
    public String forceLogout(@PathVariable("sessionId") String sessionId,   
        RedirectAttributes redirectAttributes) {  
        try {  
            Session session = sessionDAO.readSession(sessionId);  
            if(session != null) {  
                session.setAttribute(  
                    "LOGOUT", Boolean.TRUE);  
            }  
        } catch (Exception e) {/*ignore*/}  
        /*redirectAttributes.addAttribute("forcelogout", "1");  //重定向携带的参数
        return "redirect:/login";  */
        return "success";
    }  
	
	@RequestMapping(value="getSessionId",method=RequestMethod.GET,produces="text/html;charset=utf-8")
	@ResponseBody
	public Serializable getSessionId(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		String sid = null;
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName()+":"+cookie.getValue());
			if (cookie.getName().equals("sid")) {
				sid = cookie.getValue();
				cookie.setMaxAge(-1);
			}
			
			
			
		}
		Session session = sessionDAO.readSession(sid);
		//session.setAttribute("LOGOUT", Boolean.TRUE);
		return sid+":"+sessionDAO.toString()+":"+session.toString();
	}
	
	
	@RequestMapping(value="getSessionList",method=RequestMethod.GET,produces="text/html;charset=utf-8")
	public String getSessionList(HttpServletRequest request,HttpServletResponse response,Model model) {
		Collection<Session> sessions = sessionDAO.getActiveSessions();//取出来的集合中除了session，还有linkedList,直接遍历会报异常
		String s = sessions.toString();
		String[] sids = s.split(",");//转换成字符串之后再根据“,”,分组，再根据id查找对应session放到集合中去
		List<Session> list = new ArrayList<>();
		Session session = null;
		for (String sid : sids) {
			if (StringUtils.contains(sid, "id=")) {
				try {
					session = sessionDAO.readSession(sid.substring(3,39));
				} catch (Exception e) {
					session=null;
				}
				if (session!=null&&session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEY")!=null) {
					list.add(session);
				}
			}
		}		
		 model.addAttribute("sessions", list);  
	     model.addAttribute("sessionCount", list.size()); 
		return "system/session"; 
	}
	
	
	
	
}
