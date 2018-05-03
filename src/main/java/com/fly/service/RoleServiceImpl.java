package com.fly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.mapper.RoleMapper;
import com.fly.pojo.Role;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public Role getRole(Role role) {
		// TODO Auto-generated method stub
		return roleMapper.getRole(role);
	}

	@Override
	public Role getRoleByName(String roleName) {
		// TODO Auto-generated method stub
		Role role = new Role();
		role.setRoleName(roleName);
		return roleMapper.getRole(role);
	}

	@Override
	public List<Role> getAllRole() {
		// TODO Auto-generated method stub
		return roleMapper.getAllRole();
	}

	@Override
	public void addRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.addRole(role);
	}

	@Override
	public void deleteRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.deleteRole(role);
	}

	@Override
	public void updateRole(Role role) {
		// TODO Auto-generated method stub
		roleMapper.updateRole(role);
	}

	@Override
	public List<Role> getAllRoleDetails() {
		// TODO Auto-generated method stub
		return roleMapper.getAllRoleDetails();
	}

}
