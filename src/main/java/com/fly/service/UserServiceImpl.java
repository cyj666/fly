package com.fly.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.mapper.UserMapper;
import com.fly.pojo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	
	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUserId(id);
		return userMapper.getUser(user);
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername(username);
		return userMapper.getUser(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userMapper.getAllUser();
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userMapper.addUser(user);
		user.setUserId(userMapper.getUsers(user).getUserId());
		userMapper.addUInfo(user);
		userMapper.addUserInfo(user.getUserId(), user.getUserId());
	}

	/**
	 * 删除用户：先删除信息表，最后删除中间表
	 */
	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		userMapper.deleteUser(user);
		userMapper.deleteUInfo(user.getUserId());
		userMapper.deleteUserInfo(user.getUserId());
	}

	/**
	 *同步更新用户表和中间表
	 *
	 */
	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userMapper.updateUser(user);
		userMapper.updateUserInfo(user);
	}

	@Override
	public User getUserDetails(String username) {
		// TODO Auto-generated method stub
		return userMapper.getUserDetails(username);
	}

	@Override
	public void addUserRole(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		userMapper.addUserRole(userId, roleId);
	}

	@Override
	public void addRolePermision(Integer roleId, Integer permissionId) {
		// TODO Auto-generated method stub
		userMapper.addRolePermision(roleId, permissionId);
	}

	@Override
	public void deleteUserRole(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		userMapper.deleteUserRole(userId, roleId);
	}

	@Override
	public void deleteRolePermision(Integer roleId, Integer permissionId) {
		// TODO Auto-generated method stub
		userMapper.deleteRolePermision(roleId, permissionId);
	}

	@Override
	public List<User> getAllUserDetails() {
		// TODO Auto-generated method stub
		return userMapper.getAllUserDetails();
	}

	@Override
	public void updateRolePermision(Integer roleId, Integer permissionId) {
		// TODO Auto-generated method stub
		userMapper.updateRolePermision(roleId, permissionId);
	}

	@Override
	public void signIn(String username) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserDetails(username);
		user.setLastSignTime(new Date());
		user.setCountDay(user.getCountDay()+1);
		userMapper.updateUserInfo(user);
	}

	@Override
	public void addCollection(String username, Integer topicId) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserDetails(username);
		userMapper.addCollection(user.getUserId(), topicId);
	}

	@Override
	public void deleteCollection(String username, Integer topicId) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserDetails(username);
		userMapper.deleteCollection(user.getUserId(), topicId);
	}

}
