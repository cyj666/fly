package com.fly.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable{
	private static final long serialVersionUID = -4987248128309954399L;  
	  
    private Integer roleId;  
    private String roleName;  
    private String roleDesc; 
    private Set<Permission> permissionSet = new HashSet<Permission>();  
  
    public Role() {  
        super();  
    }  
      
    // --------------------------------------------------------------------------------  
  
    @Override  
    public int hashCode() {  
        final int prime = 31;  
        int result = 1;  
        result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());  
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
        Role other = (Role) obj;  
        if (roleId == null) {  
            if (other.roleId != null)  
                return false;  
        } else if (!roleId.equals(other.roleId))  
            return false;  
        return true;  
    }

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<Permission> getPermissionSet() {
		return permissionSet;
	}

	public void setPermissionSet(Set<Permission> permissionSet) {
		this.permissionSet = permissionSet;
	}  
      
    // --------------------------------------------------------------------------------  
  
   
}
