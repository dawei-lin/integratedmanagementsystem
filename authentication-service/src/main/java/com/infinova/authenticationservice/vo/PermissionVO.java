package com.infinova.authenticationservice.vo;
/**
 * @Project:CooperativeProductMS_server
 * @File:PermissionVO
 * @Package:com.infinova.pcms.pcmssecurity.vo
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/8/12 11:21
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName:PermissionVO
 * @Description: 配合前端组件，增加key,value
 * @author:ldw
 * @date:2019/8/12 11:21
 *
 *
 */
@Getter
@ToString
public class PermissionVO implements Serializable {

    private static final long serialVersionUID = 7380584222912447694L;

    @ApiModelProperty(value = "id编号", dataType = "ID", required = false, example = "0")
    private String id;
    @ApiModelProperty(value = "名称", dataType = "String", required = true)
    private String name;
    @ApiModelProperty(value = "国际化名称", dataType = "String", required = false)
    private String nameIntl;
    @ApiModelProperty(value = "级别, 0最高", dataType = "Integer", required = true, example = "0")
    private Integer level;
    @ApiModelProperty(value = "父权限编号", dataType = "String", required = true)
    private String parentId;
    @ApiModelProperty(value = "权限", dataType = "String", required = false)
    private String permission;
    @ApiModelProperty(value = "排序", dataType = "String", required = true)
    private String sort;
    @ApiModelProperty(value = "权限描述", dataType = "String", required = false)
    private String description;
    @ApiModelProperty(value = "是否授权", dataType = "Boolean", required = false)
    private Boolean disabled;
    @ApiModelProperty(value = "id编号", dataType = "ID", required = false, example = "0")
    private String key;
    @ApiModelProperty(value = "id编号", dataType = "ID", required = false, example = "0")
    private String value;
    @ApiModelProperty(value = "id编号", dataType = "ID", required = false, example = "0")
    private String title;
    @ApiModelProperty(value = "子权限列表", dataType = "List<Permission>", required = false)
    private List<PermissionVO> children;


    public void setId(String id) {
        this.id = id;
        this.value = id;
        this.key = id;
    }

    public void setName(String name) {
        this.name = name;
        this.title = name;
    }

    public void setNameIntl(String nameIntl) {
        this.nameIntl = nameIntl;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setChildren(List<PermissionVO> children) {
        this.children = children;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameIntl() {
        return nameIntl;
    }

    public Integer getLevel() {
        return level;
    }

    public String getParentId() {
        return parentId;
    }

    public String getPermission() {
        return permission;
    }

    public String getSort() {
        return sort;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getTitle() {
        return title;
    }

    public List<PermissionVO> getChildren() {
        return children;
    }
}
