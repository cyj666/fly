package com.fly.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;

/**
 * 主题类
 * @author DELL
 *
 */
public class Topic implements Serializable,Comparable<Topic>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193703657486621875L;
	
	public Topic(Integer id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public Topic() {
		// TODO Auto-generated constructor stub
	}
	
	private Integer id;
	private User user;  //作者
	private String title;   //标题
	private String content;  //内容
	private Integer viewNo;   //查看数
	private Date createTime;  //创建时间
	private List<Tab> tabs;   //所携带的标签
	private List<Reply> replies; //回复的帖子
	private Integer reward;  //悬赏数
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getViewNo() {
		return viewNo;
	}
	public void setViewNo(Integer viewNo) {
		this.viewNo = viewNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<Tab> getTabs() {
		return tabs;
	}
	public void setTabs(List<Tab> tabs) {
		this.tabs = tabs;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public Integer getReward() {
		return reward;
	}
	public void setReward(Integer reward) {
		this.reward = reward;
		
	}
	
	@Override
	public String toString() {
		return "Topic [id=" + id + ", user=" + user + ", title=" + title + ", content=" + content + ", viewNo=" + viewNo
				+ ", createTime=" +DateUtil.formatDateTime(createTime)+ ", tabs=" + tabs + ", replies=" + replies + ", reward=" + reward + "]";
	}
	
	/**
	 * 自定义实现排序，按照回复数从大到小
	 * @param t
	 * @return
	 */
	@Override
	public int compareTo(Topic t) {
		// TODO Auto-generated method stub
		int i = this.getReplies().size() - t.getReplies().size();//先按照回复数排序
		if(i == 0){  
            return DateUtil.minute(this.getCreateTime()) -DateUtil.minute(t.getCreateTime());//如果回复数相等了再用时间进行排序  
        } 
		return -i;
	}
	
	
	
	

}
