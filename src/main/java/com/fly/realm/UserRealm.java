package com.fly.realm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.fly.pojo.Permission;
import com.fly.pojo.Role;
import com.fly.pojo.User;
import com.fly.service.UserService;

public class UserRealm extends JdbcRealm {

	@Autowired
	private UserService userService;
	
	/*@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		// TODO Auto-generated method stub
		super.setCredentialsMatcher(credentialsMatcher);
	}
	
	@Override
	public void setPermissionsLookupEnabled(boolean permissionsLookupEnabled) {
		// TODO Auto-generated method stub
		super.setPermissionsLookupEnabled(permissionsLookupEnabled);
	}
	
	@Override
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		super.setDataSource(dataSource);
	}
	
	@Override
	public void setAuthenticationQuery(String authenticationQuery) {
		// TODO Auto-generated method stub
		super.setAuthenticationQuery(authenticationQuery);
	}*/
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		
		if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
		//SimpleAuthorizationInfo
		//SimpleCookie
 		String username = (String) principals.getPrimaryPrincipal();
		User user = userService.getUserDetails(username);
		Role role = new Role();
		Permission permission = new Permission();
		//角色名的集合 
		Set<String> roleSet = new HashSet<>();
		//权限名的集合 
		Set<Permission> pSet = new HashSet<>();
		
		Set<String> piSet = new HashSet<>();
		Iterator<?> iterator = user.getRoleSet().iterator();
		while(iterator.hasNext()) {
			role = (Role) iterator.next();
			roleSet.add(role.getRoleName());
			pSet = role.getPermissionSet();
			//System.out.println(role.getPermissionSet());
		}
		
		iterator = pSet.iterator();
		while (iterator.hasNext()) {
			permission = (Permission) iterator.next();
			piSet.add(permission.getPermissionName());
		}
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(roleSet);
		simpleAuthorizationInfo.addStringPermissions(piSet);
		//simpleAuthorizationInfo.setStringPermissions(piSet);
		//simpleAuthorizationInfo =(SimpleAuthorizationInfo) super.doGetAuthorizationInfo(principals);
		return simpleAuthorizationInfo;
	}
	

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		AuthenticationInfo authenticationInfo = null;
		String username = (String) token.getPrincipal();
		User user = userService.getUserByUsername(username);
		if (Boolean.TRUE.equals(user.getLocked())) {
			
			throw new LockedAccountException("账号被锁");
		}
		if (user.getStatus().equals(1)) {
			throw new DisabledAccountException();
		}
		if (user.getStatus().equals(2)) {
			throw new ExpiredCredentialsException();
		}		
		

		authenticationInfo = super.doGetAuthenticationInfo(token);
		return authenticationInfo;
	}
}
