package com.fly.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.pojo.Reply;
import com.fly.pojo.Tab;
import com.fly.pojo.Topic;
import com.fly.service.ReplyService;
import com.fly.service.TopicService;
import com.fly.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TopicController {
	
	@Autowired 
	TopicService topicService;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	UserService userService;

	
	@RequestMapping(value="topic/{p}",method=RequestMethod.GET,produces="text/html;charset=utf-8")	
	public String getTopic(@PathVariable(value="p")Integer p,Model model
			,@RequestParam(value="page",defaultValue="1")int pageNum,
			@RequestParam(value="rows",defaultValue="10")int pageSize) {
		Topic topic = topicService.getTopicById(p);
		topicService.addTopicViewNo(p);
		PageHelper.startPage(pageNum, pageSize);
		List<Reply> replies = replyService.getReplyDetailsByTopicId(p);
		PageInfo<Reply> pages = new PageInfo<>(replies);
		long total = pages.getTotal();
		model.addAttribute("pages", pages);
		model.addAttribute("topic", topic);
		model.addAttribute("total", total);
		if (SecurityUtils.getSubject().getPrincipal()!=null) {
			String username =  SecurityUtils.getSubject().getPrincipal().toString();
			model.addAttribute("user", /*userService.getUserByUsername(username)*/userService.getUserDetails(username));
		}
		model.addAttribute("replies", pages.getList());
		model.addAttribute("hotTopic", topicService.getAllTopic(2));//本周热议
		return "/jie/detail";		
	}
	
	@RequestMapping(value="/jie/add",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String addTopic(@RequestParam(value="class")Integer clazz,  //专栏
			@RequestParam(value="title")String title,
			@RequestParam(value="project")String project,
			@RequestParam(value="version")String version,
			@RequestParam(value="browser")String browser,
			@RequestParam(value="content")String content,
			@RequestParam(value="experience")Integer experience,  //飞吻
			@RequestParam(value="vercode")String vercode) {		
		Integer[] tabs = {clazz,1};  //默认加个未结
		String username =  SecurityUtils.getSubject().getPrincipal().toString();
 		topicService.addTopic(title, content, 0, new Date(), experience, userService.getUserByUsername(username).getUserId(), tabs);
		String result = "{\"status\":0,\"msg\":\""+"发表成功"+"。\",\"action\":\"index\"}";
		return result;		
	}
	
	@RequestMapping(value="delete/{p}",method=RequestMethod.GET,produces="text/html;charset=utf-8")	
	@ResponseBody
	public String deleteTopic(@PathVariable(value="p")Integer p) {		
		return topicService.deleteTopic(p);		
	}
	
	/**
	 * 实现删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/api/jie-delete",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String deleteTopic2(@RequestParam(value="id")Integer id) {		
		topicService.deleteTopic(id);
		return "{\"status\":0,\"msg\":\""+"删除成功"+"。\",\"action\":\"index\"}";	
	}
	
	
	/**
	 * 开发中（未完成）
	 * 实现加精/置顶功能
	 * filed=status加精
	 *      =stick置顶
	 * @param id
	 * @param rank
	 * @param filed
	 * @return
	 */
	@RequestMapping(value="/api/jie-set",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String setTopic(@RequestParam(value="id")Integer id,
			@RequestParam(value="rank")Integer rank,
			@RequestParam(value="field")String filed) {	
	    Topic topic = topicService.getTopicById(id);
		List<Tab> tabs = topic.getTabs();
		List<Integer> t = new ArrayList<>();
		
		switch (filed) {
		case "stick":
			for (Tab tab : tabs) {
				if (tab.getId().equals(9)) {
					return "{\"status\":1,\"msg\":\""+"已经置顶了！"+"。\",\"action\":\"index\"}";
				}
				t.add(tab.getId());
			}
			t.add(9);
			break;
		case "status":
			for (Tab tab : tabs) {
				if (tab.getId().equals(10)) {
					return "{\"status\":1,\"msg\":\""+"已经加精了！"+"。\",\"action\":\"index\"}";
				}
				t.add(tab.getId());
			}
			t.add(10);
			break;

		default:
			break;
		}
		topicService.updateTopicTab(id, t);
		return "{\"status\":0,\"msg\":\""+"操作成功"+"。\",\"action\":\"index\"}";		
	}
	
}

