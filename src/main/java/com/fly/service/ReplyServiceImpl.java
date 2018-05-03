package com.fly.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.mapper.ReplyMapper;
import com.fly.pojo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	/**
	 * 根据主题ID得到详细的回帖内容
	 */
	@Override
	public List<Reply> getReplyDetailsByTopicId(Integer topicId) {
		// TODO Auto-generated method stub
		return replyMapper.getReplyDetails(topicId, null);
	}

	/**
	 * 根据回帖ID得到详细的回帖内容
	 */
	@Override
	public Reply getReplyDetailsByReplyId(Integer replyId) {
		// TODO Auto-generated method stub
		return replyMapper.getReplyDetails(null, replyId).get(0);
	}

	/**
	 * 回贴
	 */
	@Override
	public void addReply(String content,Integer userId,Integer topicId) {
		// TODO Auto-generated method stub
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setCreateTime(new Date());
		reply.setLike(0);
		reply.setFloor(replyMapper.countReply(topicId)+1);  //第一条回复从1楼开始
		reply.setAccept(false);
		
		replyMapper.addReply(reply);  //先单表添加
		replyMapper.addUserReply(userId, reply.getId());
		
		replyMapper.addTopicReply(topicId, reply.getId());
		
	}

	/**
	 * 删回复
	 */
	@Override
	public void deleteReply(Integer Id) {
		// TODO Auto-generated method stub
		replyMapper.deleteReply(Id);
		replyMapper.deleteTopicReply(Id);
	}

	
	/**
	 * 修改回复属性值（赞数，楼层，是否采纳）
	 */
	@Override
	public void updateReply(Reply reply) {
		// TODO Auto-generated method stub
		replyMapper.updateReply(reply);
	}

}
