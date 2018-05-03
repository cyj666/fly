package com.fly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.mapper.PermissionMapper;
import com.fly.pojo.Permission;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	PermissionMapper permissionMapper;
	
	@Override
	public Permission getpermission(Permission permission) {
		// TODO Auto-generated method stub
		return permissionMapper.getPermission(permission);
	}

	@Override
	public List<Permission> getAllpermission() {
		// TODO Auto-generated method stub
		return permissionMapper.getAllPermission();
	}

	@Override
	public void addpermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionMapper.addPermission(permission);
	}

	@Override
	public void deletepermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionMapper.deletePermission(permission);
	}

	@Override
	public void updatepermission(Permission permission) {
		// TODO Auto-generated method stub
		permissionMapper.updatePermission(permission);
	}

	@Override
	public Permission getpermissionByName(String permissionName) {
		// TODO Auto-generated method stub
		Permission permission = new Permission();
		permission.setPermissionName(permissionName);
		return permissionMapper.getPermission(permission);
	}

}
