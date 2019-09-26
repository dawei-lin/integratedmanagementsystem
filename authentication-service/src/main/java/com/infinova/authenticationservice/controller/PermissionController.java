package com.infinova.authenticationservice.controller;


import com.infinova.authenticationservice.annotation.HttpReqAnnotaion;
import com.infinova.authenticationservice.dao.PermissionDao;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.service.PermissionService;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.utils.PermissionUtil;
import com.infinova.authenticationservice.utils.UserUtil;
import com.infinova.authenticationservice.vo.PermissionVO;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import com.infinova.authenticationservice.vo.res.AjaxExceptionResult;
import com.infinova.authenticationservice.vo.res.AjaxInvalidResult;
import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限相关接口
 * 
 * @author xrx
 *
 */
//@Api(tags = "权限菜单操作")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private TokenService tokenService;

	@HttpReqAnnotaion
	@PostMapping
	@ApiOperation(value = "保存菜单")
	//@PreAuthorize("hasAuthority('sys:menu:add')")
	public AjaxMessageResult<Object> save(
			@ApiParam(name = "permission", value = "权限对象", required = true) @RequestBody Permission permission) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			permissionService.save(permission);
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@HttpReqAnnotaion
	@DeleteMapping("/{id}")
	@ApiOperation(value = "删除菜单")
	//@PreAuthorize("hasAuthority('sys:menu:del')")
	public AjaxMessageResult<Object> delete(
			@ApiParam(name = "id", value = "权限id", required = true) @PathVariable String id) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			permissionService.delete(id);
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@HttpReqAnnotaion
	@PostMapping("/change")
	@ApiOperation(value = "修改菜单")
	//@PreAuthorize("hasAuthority('sys:menu:add')")
	public AjaxMessageResult<Object> update(
			@ApiParam(name = "permission", value = "权限对象", required = true) @RequestBody Permission permission) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			permissionService.update(permission);
			data.setMsg(permissionDao.getById(permission.getId()));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "根据菜单id获取菜单")
	//@PreAuthorize("hasAuthority('sys:menu:query')")
	public AjaxMessageResult<Object> get(
			@ApiParam(name = "id", value = "权限id", required = true) @PathVariable String id) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(permissionDao.getById(id));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping(params = "roleId")
	@ApiOperation(value = "根据角色id获取菜单")
	//@PreAuthorize("hasAnyAuthority('sys:menu:query','sys:role:query')")
	public AjaxMessageResult<Object> listByRoleId(
			@ApiParam(name = "roleId", value = "角色id", required = true) String roleId) {
		if (StringUtil.isNullOrEmpty(roleId)) {
			return new AjaxInvalidResult();
		}
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(permissionDao.listByRoleId(roleId));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping("/tree")
	@ApiOperation(value = "获取菜单树", notes = "包括所有级别的菜单")
	// @PreAuthorize("hasAuthority('sys:menu:query')")
	public AjaxMessageResult<Object> permissionsTree() {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			List<Permission> permissions = permissionDao.listAll();
			List<PermissionVO> permissionVOS = new ArrayList<>();
			for (Permission permission : permissions) {
				PermissionVO permissionVO = new PermissionVO();
				BeanUtils.copyProperties(permission, permissionVO);
				permissionVO.setId(permission.getId());
				permissionVOS.add(permissionVO);
			}
			data.setMsg(PermissionUtil.VOlistToTree(permissionVOS));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping("/tree/{level}")
	@ApiOperation(value = "获取level级别以上的菜单树", notes = "如level=2, 则返回2级以上菜单, 包括0级, 1级, 2级")
	public AjaxMessageResult<Object> permissionsTreeByLevel(
			@ApiParam(name = "level", value = "菜单级别", required = true) @PathVariable Integer level) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(PermissionUtil.listToTree(permissionDao.listAllByLowestLevel(level)));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping(params = "sort")
	@ApiOperation(value = "通过sort排序号 获取某菜单自身及所有下级菜单构成的树")
	public AjaxMessageResult<Object> treeMenu(@ApiParam(name = "sort", value = "菜单排序号", required = true) String sort) {
		if (StringUtil.isNullOrEmpty(sort)) {
			return new AjaxInvalidResult();
		}
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(PermissionUtil.listToTree(permissionDao.listBySortPrefix(sort)));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@ApiOperation(value = "当前登录用户拥有的权限树")
	@GetMapping("/current/{systemName}")
	public AjaxMessageResult<Object> permissionsCurrent(@PathVariable  String systemName , HttpServletRequest request ) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			UserDetailsInfo userDetailsInfo = tokenService.getUserDetailsInfo(UserUtil.getToken(request));
			data.setMsg(this.getChild(userDetailsInfo.getPermissions().get(0),systemName));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}


	/**
	 * 获取指定系统子树
	 */
	private Permission getChild(Permission p, String systemName) {
		if ( null!= p.getChildren() && p.getChildren().size() > 0) {
			List<Permission> list = p.getChildren();
			for (Permission permission : list) {
				if (systemName.equals(permission.getDescription())) {
					return permission;
				}
			}
		}
		return null;
	}

	/**
	 * 设置子元素
	 * @param p
	 * @param permissions
	 */
	@SuppressWarnings("unused")
	private void setChild(Permission p, List<Permission> permissions) {
		List<Permission> child = permissions.parallelStream().filter(a -> a.getParentId().equals(p.getId()))
				.collect(Collectors.toList());
		p.setChildren(child);
		if (!CollectionUtils.isEmpty(child)) {
			child.parallelStream().forEach(c -> {
				// 递归设置子元素，多级菜单支持
				setChild(c, permissions);
			});
		}
	}

	/**
	 * 校验权限
	 *
	 * @return
	 */
	@GetMapping("/owns")
	@ApiOperation(value = "校验当前用户的权限")
	public AjaxMessageResult<Object> ownsPermission() {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			List<Permission> permissions = UserUtil.getUserDetailsInfo().getPermissions();
			if (CollectionUtils.isEmpty(permissions)) {
				data.setMsg(Collections.emptySet());
				return data;
			}
			data.setMsg(permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
					.map(Permission::getPermission).collect(Collectors.toSet()));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping("/changePlace")
	@ApiOperation(value = "交换权限菜单的排序位置")
	public AjaxMessageResult<Object> changePlace(String permissionId1, String permissionId2) {
		if (StringUtil.isNullOrEmpty(permissionId1) || StringUtil.isNullOrEmpty(permissionId2)) {
			return new AjaxInvalidResult();
		}
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			Permission p1 = permissionDao.getById(permissionId1);
			Permission p2 = permissionDao.getById(permissionId2);
			if (p1 == null || p2 == null) {
				return new AjaxInvalidResult();
			}
			if (p1.getParentId().equals(p2.getParentId())) {
				permissionService.changeSort(p1.getSort(), p2.getSort());
			} else {
				return new AjaxInvalidResult();
			}
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

}
