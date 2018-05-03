package com.fly.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fly.service.ReplyService;

@Controller
public class ReplyController {

	@Autowired
	ReplyService replyService;
	
	/*
	 * 回复接口
	 */
	@RequestMapping(value="/jie/reply",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String addTopic(@RequestParam(value="content")String content,
			@RequestParam(value="uid")Integer uid /*用户ID*/,
			@RequestParam(value="tid")Integer tid) {		
		replyService.addReply(content, uid, tid);
		String result = "{\"status\":0,\"msg\":\""+"发表成功"+"。\",\"action\":\""+tid+"\"}";
		return result;		
	}
	
	/*/jie/getDa/
	 * 删除回复接口
	 */
	@RequestMapping(value="/api/jieda-delete",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String deleteTopic(@RequestParam(value="id")Integer id) {		
		replyService.deleteReply(id);
		String result = "{\"status\":0,\"msg\":\""+"删除成功"+"。\"}";
		return result;		
	}
	
	
	/*@RequestMapping(value="/jie/getDa",method=RequestMethod.POST,produces="application/json;charset=utf-8")	
	@ResponseBody
	public String updateTopic(@RequestParam(value="id")Integer id) {		
		replyService.deleteReply(id);
		String result = "{\"status\":0,\"msg\":\""+"删除成功"+"。\"}";
		return result;		
	}*/
	
}
