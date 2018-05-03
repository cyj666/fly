package com.fly.service;

import java.util.List;

import com.fly.pojo.Permission;

public interface PermissionService {
	public Permission getpermission(Permission permission);
	public Permission getpermissionByName(String permissionName);
	public List<Permission> getAllpermission();
	
	public void addpermission(Permission permission);
	public void deletepermission(Permission permission);
	public void updatepermission(Permission permission);
}
