package com.fly.service;

import java.util.Date;
import java.util.List;

import com.fly.pojo.Topic;

public interface TopicService {
	
	public Topic getTopicById(Integer topicId);
	public List<Topic> getTopicByTabId(Integer tabId);
	public List<Topic> getAllTopic(Integer condition);
	public List<Topic> getTopicDetailsByTabs(Integer tab1,Integer tab2);
	
	public String addTopic(String title,String content,
			Integer viewNo,Date createTime,Integer reward,Integer userId,
			Integer[] tabs);
	
	public String deleteTopic(Integer Id);
	
	public String updateTopic(Topic topic);
	public String updateTopicTab(Integer topicId,List<Integer> tabs);
	public void addTopicViewNo(Integer topicId);
}
