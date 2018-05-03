package com.fly.service;

import java.util.List;

import com.fly.pojo.Reply;

public interface ReplyService {

	public List<Reply> getReplyDetailsByTopicId(Integer topicId);
	public Reply getReplyDetailsByReplyId(Integer replyId);
	
	public void addReply(String content,Integer userId,Integer topicId);
	
	public void deleteReply(Integer Id);
	
	public void updateReply(Reply reply);
	
}
