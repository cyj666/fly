package com.fly.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.mapper.ReplyMapper;
import com.fly.mapper.TopicMapper;
import com.fly.pojo.Tab;
import com.fly.pojo.Topic;

/**
 * 主题业务实现类
 * @author DELL
 *
 */
@Service
public class TopicServiceImpl implements TopicService{

	@Autowired
	TopicMapper topicMapper;
	
	@Autowired
	ReplyMapper replyMapper;
	
	/**
	 * 根据主题ID获取主题
	 */
	@Override
	public Topic getTopicById(Integer topicId) {
		// TODO Auto-generated method stub
		return topicMapper.getTopicDetails(topicId,null,null).get(0);  //肯定只有一条
	}

	/**
	 * 获取所有主题（未优化），之后可加limit
	 */
	@Override
	public List<Topic> getAllTopic(Integer condition) {
		// TODO Auto-generated method stub
		return topicMapper.getTopicDetails(null,null,condition);
	}

	/**
	 * 发布新主题
	 */
	@Override
	public String addTopic(String title,String content,
			Integer viewNo,Date createTime,Integer reward,
			Integer userId,
			Integer[] tabs) {
		// TODO Auto-generated method stub
		Topic topic = new Topic();
		topic.setTitle(title);
		topic.setContent(content);		
		topic.setViewNo(viewNo);
		topic.setCreateTime(createTime);
		topic.setReward(reward);
		topicMapper.addTopic(topic);
		String msg ="";
		topicMapper.addUserTopic(userId, topic.getId());
		if (tabs!=null&&tabs.length>0) {
			for (Integer i : tabs) {
				topicMapper.addTopicTabs(topic.getId(), i);
			}	
		}	
		System.out.println("发表成功！");
		msg = "success";
		return msg;
	}

	
	/**
	 * 删除主题
	 * 顺带删除一系列中间表
	 */
	@Override
	public String deleteTopic(Integer Id) {
		// TODO Auto-generated method stub
		List<Topic> topics= topicMapper.getTopicDetails(Id,null,null);
		if (topics==null||topics.size()==0) {
			return "不存在该条记录";
		}
		Topic topic = topics.get(0);
		topicMapper.deleteTopic(topic.getId());
		topicMapper.deleteTopicTab(topic.getId());
		topicMapper.deleteUserTopic(topic.getId());
		topicMapper.deleteTopicReply(Id);
		return "success";
	}

	
	/**
	 * 修改主题
	 * 仅限简单修改
	 */
	@Override
	public String updateTopic(Topic topic) {
		// TODO Auto-generated method stub
		/*List<Topic> topics= topicMapper.getTopicDetails(topic.getId(),null,null);
		if (topics==null||topics.size()==0) {
			return "不存在该条记录";
		}
		Topic topic1 = topics.get(0);
		topicMapper.updateTopic(topic1);
		for (Tab tab : topic1.getTabs()) {
			
			topicMapper.updateTopicTab(topic1.getId(), tab.getId());
		}*/
		
		topicMapper.updateTopic(topic);
		/*topicMapper.deleteTopicTab(topic.getId());
		for (Tab tab : topic.getTabs()) {			
			topicMapper.addTopicTabs(topic.getId(), tab.getId());
		}*/
		return "ok";
	}

	@Override
	public List<Topic> getTopicByTabId(Integer tabId) {
		// TODO Auto-generated method stub		
		return topicMapper.getTopicDetails(null, tabId,null);
	}

	@Override
	public void addTopicViewNo(Integer topicId) {
		// TODO Auto-generated method stub
		Topic topic = topicMapper.getTopicDetails(topicId, null,null).get(0);
		topic.setViewNo(topic.getViewNo()+1);//每当访问一次，查看数加1
		topicMapper.updateTopic(topic);
	}

	@Override
	public List<Topic> getTopicDetailsByTabs(Integer tab1, Integer tab2) {
		// TODO Auto-generated method stub
		return topicMapper.getTopicDetailsByTabs(tab1, tab2);
	}

	@Override
	public String updateTopicTab(Integer topicId, List<Integer> tabs) {
		// TODO Auto-generated method stub
		topicMapper.deleteTopicTab(topicId);
		for (Integer tabId : tabs) {
			topicMapper.addTopicTabs(topicId, tabId);
		}		
		return "ok";
	}

}
