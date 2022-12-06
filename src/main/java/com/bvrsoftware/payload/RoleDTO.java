package com.bvrsoftware.payload;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;
	private Long role_id;
    private String role_name;
    private Set<UserRoleDTO> userRole = new HashSet<>();

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<UserRoleDTO> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRoleDTO> userRole) {
        this.userRole = userRole;
    }
}
