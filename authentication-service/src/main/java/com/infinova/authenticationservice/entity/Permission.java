package com.infinova.authenticationservice.entity;

import lombok.ToString;

import java.util.List;

@ToString
//@ApiModel(value = "Permission  权限菜单对象")
public class Permission extends BaseEntity<String> {

	private static final long serialVersionUID = 6180869216498363919L;

	//@ApiModelProperty(value = "名称", dataType = "String", required = true)
	private String name;
	//@ApiModelProperty(value = "国际化名称", dataType = "String", required = false)
	private String nameIntl;
	//@ApiModelProperty(value = "级别, 0最高", dataType = "Integer", required = true, example = "0")
	private Integer level;
	// @ApiModelProperty(value = "父权限编号", dataType = "String", required = true)
	private String parentId;
	//@ApiModelProperty(value = "权限", dataType = "String", required = false)
	private String permission;
	//@ApiModelProperty(value = "排序", dataType = "String", required = true)
	private String sort;
	//@ApiModelProperty(value = "跳转链接", dataType = "String", required = false)
	private String href;
	//@ApiModelProperty(value = "跳转类型, 0页面重定向, 1打开新的标签页, 2打开新的浏览器窗口", dataType = "Integer", required = false, example = "0")
	private Integer jumpType;
	//@ApiModelProperty(value = "图标链接", dataType = "String", required = false)
	private String iconUrl;
	//@ApiModelProperty(value = "权限描述", dataType = "String", required = false)
	private String description;
	//@ApiModelProperty(value = "扩展信息", dataType = "String", required = false)
	private String extInfo;
	//@ApiModelProperty(value = "是否授权", dataType = "Boolean", required = false)
	private Boolean disabled;
	//@ApiModelProperty(value = "子权限列表", dataType = "List<Permission>", required = false)
	private List<Permission> children;

	public String getNameIntl() {
		return nameIntl;
	}

	public void setNameIntl(String nameIntl) {
		this.nameIntl = nameIntl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}


	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}
}
