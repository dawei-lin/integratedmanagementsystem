package com.infinova.authenticationservice.service;


import com.infinova.authenticationservice.vo.RoleVO;

public interface RoleService {

	void saveRole(RoleVO roleVO);

	void deleteRole(String id);
}
