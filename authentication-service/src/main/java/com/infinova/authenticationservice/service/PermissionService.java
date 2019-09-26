package com.infinova.authenticationservice.service;


import com.infinova.authenticationservice.entity.Permission;

public interface PermissionService {

	void save(Permission permission);

	void update(Permission permission);

	void delete(String id);

	void changeSort(String sort1, String sort2);

}
