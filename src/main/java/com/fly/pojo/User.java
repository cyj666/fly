package com.fly.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fly.pojo.Role;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8860168915950390225L;
	private Integer userId;
	private String username;
	private String password;
	private Date createTime;
	private Date lastTime;
	private String lastIp;
	private Integer status;
	private Boolean locked;
	private Set<Role> roleSet = new HashSet<Role>();  //用户角色
	
	private List<Topic> topics;  //该用户所发主题
	private List<Reply> replies; //该用户的回复
	private Integer vip;  //VIP等级
	private String sex;
	private String sign;//个性签名
	private String address;
	private String email;
	private String phone;
	private Integer credit;
	private String info;//认证信息
	private List<Topic> collections;//收藏的主题
	private List<Message> message; //留言信息
	private Date lastSignTime;  //上一次签到时间
	private Integer countDay; //用户连续签到天数（30天一个周期）
	private String avatar;//存放用户头像路径
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public Set<Role> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public Integer getVip() {
		return vip;
	}
	public void setVip(Integer vip) {
		this.vip = vip;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public List<Topic> getCollections() {
		return collections;
	}
	public void setCollections(List<Topic> collections) {
		this.collections = collections;
	}
	public List<Message> getMessage() {
		return message;
	}
	public void setMessage(List<Message> message) {
		this.message = message;
	}
	
	public Date getLastSignTime() {
		return lastSignTime;
	}
	public void setLastSignTime(Date lastSignTime) {
		this.lastSignTime = lastSignTime;
	}
	public Integer getCountDay() {
		return countDay;
	}
	public void setCountDay(Integer countDay) {
		this.countDay = countDay;
	}
	@Override
	public String toString() {
		return String.format(
				"User [userId=%s, username=%s, password=%s, createTime=%s, lastTime=%s, lastIp=%s, status=%s, locked=%s, roleSet=%s, topics=%s, replies=%s, vip=%s, sex=%s, sign=%s, address=%s, email=%s, phone=%s, credit=%s, info=%s, collections=%s, message=%s, lastSignTime=%s, countDay=%s]",
				userId, username, password, createTime, lastTime, lastIp, status, locked, roleSet, topics, replies, vip,
				sex, sign, address, email, phone, credit, info, collections, message, lastSignTime, countDay);
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
	
	
}
