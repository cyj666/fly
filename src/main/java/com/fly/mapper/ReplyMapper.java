package com.fly.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fly.pojo.Reply;

public interface ReplyMapper {

	public List<Reply> getReplyDetails(@Param("topicId")Integer topicId,@Param("replyId")Integer replyId);
	public int countReply(@Param("topicId")Integer topicId);
	
	public void addReply(Reply reply);
	public void addUserReply(@Param("userId")Integer userId,@Param("replyId")Integer replyId);
	public void addTopicReply(@Param("topicId")Integer topicId,@Param("replyId")Integer replyId);
	
	public void deleteReply(@Param("Id")Integer Id);
	public void deleteTopicReply(@Param("replyId")Integer replyId);
	
	
	public void updateReply(Reply reply);
	
}
