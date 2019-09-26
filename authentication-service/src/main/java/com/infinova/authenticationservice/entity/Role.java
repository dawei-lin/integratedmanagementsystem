package com.infinova.authenticationservice.entity;

import lombok.ToString;

@ToString
//@ApiModel(value = "Role  角色对象")
public class Role extends BaseEntity<String> {

	private static final long serialVersionUID = -3802292814767103648L;

	//@ApiModelProperty(value = "角色名称", dataType = "String", required = true)
	private String rolename;
	//@ApiModelProperty(value = "角色描述", dataType = "String", required = false)
	private String description;

	//@ApiModelProperty(value = "所属部门编号", dataType = "String", required = false)
	private String departmentId;

	//@ApiModelProperty(value = "所属部门", dataType = "String", required = false)
	private String departmentName;

	//@ApiModelProperty(value = "是否可以删除", dataType = "Integer", required = false)
	private Integer isDel;

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
