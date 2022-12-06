package com.bvrsoftware.payload;

import java.io.Serializable;

public class UserRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long user_role_id;

    private UserDTO user;

    private RoleDTO role;

    public Long getUser_role_id() {
        return user_role_id;
    }

    public void setUser_role_id(Long user_role_id) {
        this.user_role_id = user_role_id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

}
