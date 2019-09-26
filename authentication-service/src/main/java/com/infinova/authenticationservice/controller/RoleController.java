package com.infinova.authenticationservice.controller;

import com.google.common.collect.Maps;

import com.infinova.authenticationservice.dao.RoleDao;
import com.infinova.authenticationservice.entity.Role;
import com.infinova.authenticationservice.page.PageTableHandler;
import com.infinova.authenticationservice.page.PageTableRequest;
import com.infinova.authenticationservice.page.PageTableResponse;
import com.infinova.authenticationservice.service.RoleService;
import com.infinova.authenticationservice.vo.RoleVO;
import com.infinova.authenticationservice.vo.res.AjaxExceptionResult;
import com.infinova.authenticationservice.vo.res.AjaxInvalidResult;
import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色相关接口
 * 
 * @author xrx
 *
 */
//@Api(tags = "角色操作")
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleDao roleDao;

	//@HttpReqAnnotaion
	@PostMapping
	//@ApiOperation(value = "保存角色")@ApiParam(name = "roleVO", value = "角色及持有的权限编号", required = true)
	 //@PreAuthorize("hasAuthority('sys:role:add')")
	public void saveRole(@RequestBody RoleVO roleVO) {
		roleService.saveRole(roleVO);
	}

	@PostMapping("/list")
	//@ApiOperation(value = "角色列表")@ApiParam(name = "request", value = "分页查询对象", required = true)
	 //@PreAuthorize("hasAuthority('sys:role:query')")
	public AjaxMessageResult listRoles(
			 @RequestBody PageTableRequest request) {
		AjaxMessageResult ajaxMessageResult = new AjaxMessageResult();

		PageTableResponse pageTableResponse = new PageTableHandler(new PageTableHandler.CountHandler() {
			@Override
			public int count(PageTableRequest request) {
				return roleDao.count(request.getParams());
			}
		}, new PageTableHandler.ListHandler() {
			@Override
			public List<RoleVO> list(PageTableRequest request) {
				List<Role> list = roleDao.list(request.getParams(), request.getOffset(), request.getLimit());
				List<RoleVO> resList = new ArrayList<>();
				for(Role role : list){
					RoleVO roleVO = new RoleVO();
					BeanUtils.copyProperties(role,roleVO);
					roleVO.setPermissionIds(roleDao.selectRolePermission(role.getId()));
					resList.add(roleVO);
				}
				return resList;
			}
		}).handle(request);

		ajaxMessageResult.setMsg(pageTableResponse);

		return ajaxMessageResult;
	}

	@GetMapping("/{id}")
	//@ApiOperation(value = "根据id获取角色")
	// @PreAuthorize("hasAuthority('sys:role:query')")@ApiParam(name = "id", value = "角色id", required = true)
	public AjaxMessageResult<Object> get(
			 @PathVariable String id) {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(roleDao.getById(id));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping("/all")
	//@ApiOperation(value = "所有角色")
	 //@PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public AjaxMessageResult<Object> roles() {
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(roleDao.list(Maps.newHashMap(), null, null));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	@GetMapping(params = "userId")
	//@ApiOperation(value = "根据用户id获取拥有的角色")@ApiParam(name = "userId", value = "用户id", required = true)
	// @PreAuthorize("hasAnyAuthority('sys:user:query','sys:role:query')")
	public AjaxMessageResult<Object> roles(String userId) {
		if (StringUtil.isNullOrEmpty(userId)) {
			return new AjaxInvalidResult();
		}
		AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
		try {
			data.setMsg(roleDao.listByUserId(userId));
		} catch (Exception e) {
			return new AjaxExceptionResult(e);
		}
		return data;
	}

	//@HttpReqAnnotaion
	@DeleteMapping("/{id}")
	//@ApiOperation(value = "删除角色")@ApiParam(name = "id", value = "用户id", required = true)
	 //@PreAuthorize("hasAuthority('sys:role:del')")
	public void delete(@PathVariable String id) {
		roleService.deleteRole(id);
	}

}
