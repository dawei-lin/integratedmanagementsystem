package com.infinova.authenticationservice.vo;

import com.infinova.authenticationservice.entity.Role;
import lombok.ToString;

import java.util.List;

@ToString
//@ApiModel(value = "RoleVO  角色及持有的权限编号列表")
public class RoleVO extends Role {

	private static final long serialVersionUID = 4218495592167610193L;

	//@ApiModelProperty(value = "权限编号列表", dataType = "List<String>", required = false)
	private List<String> permissionIds;

	public List<String> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<String> permissionIds) {
		this.permissionIds = permissionIds;
	}
}
