package com.fly.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fly.pojo.User;

public interface UserMapper {
	
	public User getUser(User user);  // 得到用户自身信息 
	public User getUsers(User user);
	public List<User> getAllUser();
	public User getUserDetails(@Param("username")String username);  //根据用户名得到详细用户信息
	public List<User> getAllUserDetails();
	
	public void addUser(User user);
	public void addCollection(@Param("userId")Integer userId,
			@Param("topicId")Integer topicId);
	public void deleteCollection(@Param("userId")Integer userId,
			@Param("topicId")Integer topicId);	
	public void deleteUser(User user);
	public void updateUser(User user);
	public void updateUserInfo(User user);
	
	public void addUserRole(@Param("userId")Integer userId,
			@Param("roleId")Integer roleId);
	public void addRolePermision(@Param("roleId")Integer roleId,
			@Param("permissionId")Integer permissionId);
	public void addUserInfo(@Param("userId")Integer userId,@Param("uInfoId")Integer uInfoId);
	public void addUInfo(User user);
	
	
	public void deleteUserRole(@Param("userId")Integer userId,
			@Param("roleId")Integer roleId);
	public void deleteRolePermision(@Param("roleId")Integer roleId,
			@Param("permissionId")Integer permissionId);
	
	public void updateRolePermision(@Param("roleId")Integer roleId,
			@Param("permissionId")Integer permissionId);
	
	
	public void deleteUInfo(@Param("id")Integer id);  //删除用户信息表
	public void deleteUserInfo(@Param("userId")Integer userId);//删除用户中间表
	
}
