package com.infinova.authenticationservice.service.impl;

import com.infinova.authenticationservice.dao.RoleDao;
import com.infinova.authenticationservice.entity.Role;
import com.infinova.authenticationservice.service.RoleService;
import com.infinova.authenticationservice.vo.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional
	public void saveRole(RoleVO roleVO) {
		Role role = roleVO;

		log.info(roleVO.toString());
		log.info(role.toString());
		List<String> permissionIds = roleVO.getPermissionIds();

		if (role.getId() != null) {
			// 修改
			updateRole(role, permissionIds);
		} else {
			// 新增
			saveRole(role, permissionIds);
		}
	}

	private void saveRole(Role role, List<String> permissionIds) {
		Role r = roleDao.getRole(role.getRolename());
		if (r != null) {
			throw new IllegalArgumentException(role.getRolename() + "已存在");
		}
		roleDao.save(role);
		role = roleDao.getRole(role.getRolename());

		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(role.getId(), permissionIds);
		}
		log.debug("新增角色{}", role.getRolename());
	}

	private void updateRole(Role role, List<String> permissionIds) {
		Role r = roleDao.getRole(role.getRolename());
		if (r != null && r.getId() != role.getId()) {
			throw new IllegalArgumentException(role.getRolename() + "已存在");
		}
		roleDao.update(role);

		roleDao.deleteRolePermission(role.getId());
		if (!CollectionUtils.isEmpty(permissionIds)) {
			roleDao.saveRolePermission(role.getId(), permissionIds);
		}
		log.debug("修改角色{}", role.getRolename());
	}

	@Override
	@Transactional
	public void deleteRole(String id) {
		roleDao.deleteRolePermission(id);
		roleDao.deleteRoleUser(id);
		roleDao.delete(id);
		log.debug("删除角色id:{}", id);
	}

}
