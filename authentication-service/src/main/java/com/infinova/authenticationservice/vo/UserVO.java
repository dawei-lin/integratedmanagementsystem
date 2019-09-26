package com.infinova.authenticationservice.vo;


import com.infinova.authenticationservice.entity.User;

import java.util.List;

public class UserVO extends User {

	private static final long serialVersionUID = -184009306207076712L;

	private List<String> roleIds;

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

}
