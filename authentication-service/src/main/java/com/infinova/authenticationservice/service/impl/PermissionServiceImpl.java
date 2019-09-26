package com.infinova.authenticationservice.service.impl;


import com.infinova.authenticationservice.dao.PermissionDao;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.service.PermissionService;
import com.infinova.authenticationservice.utils.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public void save(Permission permission) {
		permissionDao.save(permission);

		log.debug("新增菜单{}", permission.getName());
	}

	@Override
	public void update(Permission permission) {
		permissionDao.update(permission);
	}

	@Override
	@Transactional
	public void delete(String id) {
		permissionDao.deleteRolePermission(id);
		permissionDao.deleteByParentId(id);
		permissionDao.delete(id);

		log.debug("删除菜单id:{}", id);
	}

	@Override
	@Transactional
	public void changeSort(String sort1, String sort2) {
		String str = StrUtil.getRandomString(sort1.length());
		permissionDao.updateSort(sort1, str);
		permissionDao.updateSort(sort2, sort1);
		permissionDao.updateSort(str, sort2);
	}

}
