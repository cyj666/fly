package com.fly.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fly.pojo.Topic;

public interface TopicMapper {
	public List<Topic> getTopicDetails(@Param("topicId")Integer topicId,@Param("tabId")Integer tabId,@Param("condition")Integer condition);
	public List<Topic> getTopic(Topic topic);
	public List<Topic> getTopicDetailsByTabs(@Param("tab1")Integer tab1,@Param("tab2")Integer tab2);
	
	public void addTopic(Topic topic);
	public void addUserTopic(@Param("userId")Integer userId,@Param("topicId")Integer topicId);
	public void addTopicTabs(@Param("topicId")Integer topicId,@Param("tabId")Integer tabId);
	
	
	public void deleteTopic(@Param("Id")Integer Id);
	public void deleteUserTopic(@Param("topicId")Integer topicId);
	public void deleteTopicTab(@Param("topicId")Integer topicId);
	public void deleteTopicReply(@Param("topicId")Integer topicId);
	
	
	public void updateTopic(Topic topic);
	public void updateTopicTab(@Param("tabId")Integer tabId,@Param("topicId")Integer topicId);
}
