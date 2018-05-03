package com.fly.pojo;

import java.io.Serializable;
import java.util.Date;

import cn.hutool.core.date.DateUtil;

/**
 * 回复类
 * @author DELL
 *
 */
public class Reply implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5380230256433304928L;

	private Integer id;
	private Topic topic;//回复的主题
	private User user;  //作者
	private String content;//回复内容
	private Integer like; //点赞数
	private Integer floor;//楼层
	private Date createTime;
	private Boolean accept;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLike() {
		return like;
	}
	public void setLike(Integer like) {
		this.like = like;
	}
	
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Boolean getAccept() {
		return accept;
	}
	@Override
	public String toString() {
		return "Reply [id=" + id + ", topic=" + topic + ", user=" + user + ", content=" + content + ", like=" + like
				+ ", floor=" + floor + ", createTime=" + DateUtil.formatDate(createTime) + ", accept=" + accept + "]";
	}
	public void setAccept(Boolean accept) {
		this.accept = accept;
	}
	
	
	
}
