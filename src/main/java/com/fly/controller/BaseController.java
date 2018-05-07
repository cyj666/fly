package com.fly.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fly.pojo.Reply;
import com.fly.pojo.Topic;
import com.fly.pojo.User;
import com.fly.service.ReplyService;
import com.fly.service.TopicService;
import com.fly.service.UserService;
import com.fly.tool.CaptchaUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;

@Controller
public class BaseController {
		
	@Autowired
	UserService userService;
	
	@Autowired
	TopicService topicService;
	
	@Autowired
	ReplyService replyService;
	
		
	
	/**
	 * 验证码实现
	 */
	@RequestMapping(value = "/captchaCode", method = RequestMethod.GET)
	@ResponseBody
	public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CaptchaUtil.outputCaptcha(request, response);
	}
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getUrl(Model model,@RequestParam(value="condition",defaultValue="1")Integer condition) {	
		List<Topic> stick = topicService.getTopicByTabId(9); //初始化置顶的主题
		model.addAttribute("stick", stick);
		List<Topic> all = topicService.getAllTopic(condition);//默认按时间	
		if (SecurityUtils.getSubject().getPrincipal()!=null) {			
			User user = userService.getUserDetails(SecurityUtils.getSubject().getPrincipal().toString());
			if (DateUtil.today().equals(DateUtil.formatDate(user.getLastSignTime()))) {
				model.addAttribute("isSign", true);
			}else {
				model.addAttribute("isSign", false);
			}
			model.addAttribute("user", user);
		}
		model.addAttribute("all", all);
		model.addAttribute("condition", condition);
		model.addAttribute("help", topicService.getTopicByTabId(8)); //温馨通道
		List<Topic> hotTopic = topicService.getAllTopic(2);
		Collections.sort(hotTopic);
		model.addAttribute("hotTopic", hotTopic);//本周热议
		model.addAttribute("hotUser", userService.getAllUserDetails());//本周热议
		return "/index";
	}
	
	@RequestMapping(value="/{ac}",method=RequestMethod.GET)
	public String getUrl0(@PathVariable(value="ac")String ac) {		
		return ac;
	}
	
	@RequestMapping(value="/user/{ac}",method=RequestMethod.GET)
	public String getUr1(@PathVariable(value="ac")String ac,Model model,
			@RequestParam(value="topicPage",defaultValue="1")Integer topicPage,
			@RequestParam(value="replyPage",defaultValue="1")Integer replyPage) {	
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		if (username==null) {
			return "user/"+ac;
		}
		User user = userService.getUserDetails(username);
		if (user==null) {
			return "user/"+ac;
		}
		List<Topic> topics = new ArrayList<>();
		List<Reply> replies = new ArrayList<>();
		if (user.getTopics()!=null) {
			for (Topic t : user.getTopics()) {
				t = topicService.getTopicById(t.getId());
					topics.add(t);
			}
			user.setTopics(topics);
		}		
		if (user.getReplies()!=null) {
		for (Reply r : user.getReplies()) {			
			r = replyService.getReplyDetailsByReplyId(r.getId());
				replies.add(r);
		}
		user.setReplies(replies);
		}
		List<Topic> collections = new ArrayList<>();
		if (user.getCollections()!=null) {
		for (Topic topic : user.getCollections()) {
			topic = topicService.getTopicById(topic.getId());
			collections.add(topic);
		}
		user.setCollections(collections);
		}
		model.addAttribute("user", user);
		model.addAttribute("topicPage", topicPage);
		model.addAttribute("replyPage", replyPage);
		topics = null;
		replies = null;
		return "user/"+ac;
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public String getUr1(@RequestParam(value="uid")Integer uid,Model model,
			@RequestParam(value="topicPage",defaultValue="1")Integer topicPage,
			@RequestParam(value="replyPage",defaultValue="1")Integer replyPage) {	
		User user = userService.getUserById(uid);
		user = userService.getUserDetails(user.getUsername());
		List<Topic> topics = new ArrayList<>();
		List<Reply> replies = new ArrayList<>();
		for (Topic t : user.getTopics()) {
			t = topicService.getTopicById(t.getId());
				topics.add(t);
		}
		user.setTopics(topics);
		for (Reply r : user.getReplies()) {			
			r = replyService.getReplyDetailsByReplyId(r.getId());
				replies.add(r);
		}
		user.setReplies(replies);
		model.addAttribute("user", user);
		model.addAttribute("topicPage", topicPage);
		model.addAttribute("replyPage", replyPage);
		topics = null;
		replies = null;
		return "user/home";
	}
	
	
	

	@RequestMapping(value="/case/{ac}",method=RequestMethod.GET)
	public String getUr2(@PathVariable(value="ac")String ac) {	
		return "case/"+ac;
	}
	
	@RequestMapping(value="/jie/{ac}",method=RequestMethod.GET)
	public String getUr3(@PathVariable(value="ac")String ac,
			@RequestParam(value="n",defaultValue="0",required=false)Integer n,
			Model model) {
			
		model.addAttribute("n", n);
		List<Topic> topics = topicService.getTopicDetailsByTabs(null, n);	
		model.addAttribute("topics", topics);
		
		List<Topic> hotTopic = topicService.getAllTopic(2);
		Collections.sort(hotTopic);
		model.addAttribute("hotTopic", hotTopic);//本周热议
		return "jie/"+ac;
	}
	
	@RequestMapping(value="/jie/index/{ac}",method=RequestMethod.GET)
	public String getUr5(@PathVariable(value="ac")String ac,
			@RequestParam(value="n",defaultValue="0",required=false)Integer n,
			@RequestParam(value="page",defaultValue="1",required=false)Integer page,
			@RequestParam(value="rows",defaultValue="10",required=false)Integer rows,
			Model model) {
		//topicService.getAllTopic(condition1);
		model.addAttribute("n", n);
		if (n==0) {
			n=null;
		}
		//PageHelper.startPage(page, rows);
		List<Topic> topics = new ArrayList<>();
		switch (ac) {
		case "quiz":
			topics = topicService.getTopicDetailsByTabs(4, n);
			break;
		case "share":{
			topics = topicService.getTopicDetailsByTabs(5, n);
			break;
		}
		case "discuss":{
			topics = topicService.getTopicDetailsByTabs(3, n);
			break;
		}
		case "suggest":{
			topics = topicService.getTopicDetailsByTabs(6, n);
			break;
		}
		case "notice":{
			topics = topicService.getTopicDetailsByTabs(7, n);
			break;
		}
		case "news":{
			topics = topicService.getTopicDetailsByTabs(8, n);
			break;
		}
		default:
			topics = topicService.getAllTopic(null);
			break;
		}
	//	PageInfo<Topic> pages = new PageInfo<>(topics);
		//long total = pages.getTotal();
		//model.addAttribute("total", total);
		model.addAttribute("topics", topics);
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		//model.addAttribute("pages", pages);
		if (n==null) {
			n=0;
		}
		model.addAttribute("n", n);
		List<Topic> hotTopic = topicService.getAllTopic(2);
		Collections.sort(hotTopic);
		model.addAttribute("hotTopic", hotTopic);//本周热议
		return "jie/index/"+ac;
	}
	
	
	
	@RequestMapping(value="/other/{ac}",method=RequestMethod.GET)
	public String getUr4(@PathVariable(value="ac")String ac) {	
		return "other/"+ac;
	}
	
	
	
	@RequestMapping(value="/sign/in",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String SignIn(@RequestParam(value="token")Integer userId) {
		String username =  userService.getUserById(userId).getUsername();
		if (DateUtil.today().equals(DateUtil.formatDate(userService.getUserDetails(username).getLastSignTime()))) {
			System.out.println(DateUtil.today());
			return "{\"status\":0,\"msg\":\""+"签到失败,今天已经签到过了"+"。\",\"action\":\"index\",\"data\":{\"token\":\"1\"}}";
		}
		userService.signIn(username);
		return "{\"status\":1,\"msg\":\""+"签到成功"+"。\",\"action\":\"index\",\"data\":{\"token\":\"1\"}}";
	}
	
	@RequestMapping(value="/sign/status",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String signStatus() {
		String username = (String)SecurityUtils.getSubject().getPrincipal();
		if (username==null) {
			return "{\"status\":1,\"msg\":\""+"GG"+"。\",\"action\":\"index\",\"data\":{\"token\":\"null\"}}";
		}
		int uid = userService.getUserByUsername(username).getUserId();
		String result = "{\"status\":0,\"msg\":\""+"发表成功"+"。\",\"action\":\"index\",\"data\":{\"token\":\""+uid+"\"}}";
		return result;
	}
	
	
	@RequestMapping(value="/user/upload",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String upload(@RequestParam(value="file")MultipartFile file) throws IllegalStateException, IOException {	
		if(!file.isEmpty()) {
			 //上传文件路径
            String path = "http://192.168.1.192/png/";  //网络上的头像路径
            //上传文件名
            String filename = file.getOriginalFilename();
            File filepath = new File("D:\\logs\\png",filename);  //正式存放头像文件的路径
            //判断路径是否存在，如果不存在就创建一个
            if (!filepath.getParentFile().exists()) { 
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File("D:\\logs\\png" + File.separator + filename));
            return "{\"status\":0,\"code\":\""+"0"+"。\",\"url\":\""+path +"/" + filename+"\"}";
        } else {
        	return "{\"status\":0,\"msg\":\""+"上传失败"+"。\",\"url\":\"error\"}";
        }		
	}
	
	/**
	 * 该接口用于实现提供资料、上传头像，和重置密码的功能
	 * @param avatar
	 * @param nowpass
	 * @param pass
	 * @param repass
	 * @param model
	 */
	@RequestMapping(value="/user/set",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String set(
			/*用于上传头像*/
			@RequestParam(value="avatar",required=false)String avatar,
			/*用于重置密码*/
			@RequestParam(value="nowpass",required=false)String nowpass,
			@RequestParam(value="pass",required=false)String pass,
			@RequestParam(value="repass",required=false)String repass,
			/*用于提供详细资料*/
			@RequestParam(value="email",required=false)String email,
			@RequestParam(value="username",required=false)String username,
			@RequestParam(value="sex",required=false)Integer sex,
			@RequestParam(value="city",required=false)String city,
			@RequestParam(value="sign",required=false)String sign,
			Model model) {		
		//由于前端模板的多个功能都使用的这个接口路径，只是所传的参数不同
		String name = SecurityUtils.getSubject().getPrincipal().toString();
		User user = userService.getUserDetails(name);
		//1.处理上传头像
		if (avatar!=null&&nowpass==null&&email==null) {  
			model.addAttribute("url", avatar);
			user.setAvatar(avatar);
			userService.updateUser(user);
			return "{\"status\":0,\"msg\":\""+"操作成功"+"。\"}";
		}
		//2.处理重置密码
		if (avatar==null&&nowpass!=null&&email==null) {
			String md5 =  new SimpleHash("MD5", nowpass).toString();
			if (md5.equals(user.getPassword())) {
				if (pass.equals(repass)) {
					user.setPassword(new SimpleHash("MD5", pass).toString());
					userService.updateUser(user);
					return "{\"status\":0,\"msg\":\""+"修改密码成功"+"。\"}";
				}else {
					return "{\"status\":1,\"msg\":\""+"两次密码不一致！"+"。\"}";
				}				
			}else {
				return "{\"status\":1,\"msg\":\""+"原密码错误！"+"。\"}";
			}
		}
		//3.处理资料填写
		if (avatar==null&&nowpass==null&&email!=null) {
			user.setEmail(email);
			user.setAddress(city);
			user.setSex(sex==0?"男":"女");
			user.setSign(sign);
			user.setUsername(username);
			userService.updateUser(user);
			return "{\"status\":0,\"msg\":\""+"操作成功"+"。\"}";
		}
		return "{\"status\":1,\"msg\":\""+"操作异常！"+"。\"}";
		
		/**do something 
		 * 将其存放在数据库中的用户信息表中，
		 */
	}
	
	/**
	 * api/upload接口
	 * 用于回复的图
	 * 该模板的上传头像的实际步骤：1.现从前台取得图像，2.服务器将用户上传的头像保存到自己的某个位置（这里就是以nginx作为静态服务器用来存放图像资源）；
	 * 3.服务器保存完之后返回一个url路径，保存到数据库中去
	 * @param avatar
	 * @param model
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value="/api/upload",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String uploadImg(@RequestParam(value="file")MultipartFile file) throws IllegalStateException, IOException {		
		if(!file.isEmpty()) {
			 //上传文件路径
           String path = "http://192.168.1.192/png/";  //网络上的头像路径
           //上传文件名
           String filename = file.getOriginalFilename();
           File filepath = new File("D:\\logs\\png",filename);  //正式存放头像文件的路径
           //判断路径是否存在，如果不存在就创建一个
           if (!filepath.getParentFile().exists()) { 
               filepath.getParentFile().mkdirs();
           }
           //将上传文件保存到一个目标文件当中
           file.transferTo(new File("D:\\logs\\png" + File.separator + filename));
           return "{\"status\":0,\"code\":\""+"0"+"。\",\"url\":\""+path +"/" + filename+"\"}";
       } else {
       	return "{\"status\":1,\"msg\":\""+"上传失败"+"。\",\"url\":\"error\"}";
       }		
	}
	
	
	/**
	 * 用户采纳
	 * @param replyId
	 * @return
	 */
	@RequestMapping(value="/api/jieda-accept",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String accept(@RequestParam(value="id")Integer replyId)  {
		Reply reply = replyService.getReplyDetailsByReplyId(replyId);
		reply.setAccept(true);
		replyService.updateReply(reply);
       	return "{\"status\":0,\"msg\":\""+"采纳成功"+"。\",\"url\":\"topic"+replyId+"\"}";
		
	}
	
	/*
	 * 用户点赞（貌似有点bug）
	 */
	@RequestMapping(value="/api/jieda-zan",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String zan(@RequestParam(value="id")Integer replyId,
			@RequestParam(value="ok")Boolean ok)  {
		Reply reply = replyService.getReplyDetailsByReplyId(replyId);
		if (!ok) {
			reply.setLike(reply.getLike()+1);			
		}else {
			reply.setLike(reply.getLike()-1);
		}
		replyService.updateReply(reply);
       	return "{\"status\":0,\"msg\":\""+"采纳成功"+"。\",\"url\":\"topic"+replyId+"\"}";
		
	}
	
	/**
	 * 用户注册
	 * @param username
	 * @param email
	 * @param pass
	 * @param repass
	 * @param vercode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/user/reg",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String saveUser(@RequestParam(value="username")String username,
			@RequestParam(value="email")String email,
			@RequestParam(value="pass")String pass,
			@RequestParam(value="repass")String repass,
			@RequestParam(value="vercode")String vercode,
			HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		/*String randomString = vercode.toUpperCase();
		if (!session.getAttribute("randomString").equals(randomString)) {
			String result = "{\"status\":1,\"msg\":\"验证码出错！请检查。"+session.getAttribute("randomString")+"}";
			return result;
		}*/
		User user = new User();
		String result;
		if (!pass.equals(repass)) {			
			result = "{\"status\":1,\"msg\":\"两次密码不一致\"}";
			return result;
		}		
		if (userService.getUserByUsername(username)!=null) {
			result = "{\"status\":1,\"msg\":\"新增用户失败，原因：账号重复\"}";
			return result;		
		}
		user.setUsername(username);
		user.setPassword(new SimpleHash("MD5", pass).toString());
		user.setCreateTime(new Date());
		user.setLocked(false);
		user.setStatus(0);
		user.setEmail(email);
		user.setSign("这个人很懒，什么都没有留下");
		user.setAvatar("http://192.168.1.192/png/11.jpg");
		userService.addUser(user);	
		user = userService.getUserByUsername(username);
		int[] roleIds = {2};
		for (Integer roleId  : roleIds) {
			userService.addUserRole(user.getUserId(), roleId);
		}
		
		result = "{\"status\":0,\"msg\":\"添加成功\"}";
		//log.fatal("用户"+SecurityUtils.getSubject().getPrincipal()+"添加账号"+username+"，时间："+DateUtil.formatDateTime(new Date()));
		return result;
	}
	
	/**
	 * /collection/add
	 * 实现收藏功能的接口
	 */
	@RequestMapping(value="/collection/add",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String collect(@RequestParam(value="cid")Integer cid) {
		try {
			userService.addCollection(SecurityUtils.getSubject().getPrincipal().toString(), cid);
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"status\":1,\"msg\":\"已经收藏过了！\",\"type\":\"remove\"}";
		}
		
		return "{\"status\":0,\"msg\":\"收藏成功\",\"type\":\"remove\"}";
	}
	
	
	
	/**
	 * /collection/add
	 * 实现收藏功能的接口
	 */
	@RequestMapping(value="/collection/remove",method=RequestMethod.POST,produces="application/json;charset=utf-8")
	@ResponseBody
	public String removecollect(@RequestParam(value="cid")Integer cid) {
		userService.deleteCollection(SecurityUtils.getSubject().getPrincipal().toString(), cid);
		return "{\"status\":0,\"msg\":\"取消成功\",\"type\":\"add\"}";
	}
	
	
	@RequestMapping(value="/common",method=RequestMethod.GET,produces="text/html;charset=utf-8")
	@ResponseBody
	public String common() {
		return userService.get().toString();
	}
}
