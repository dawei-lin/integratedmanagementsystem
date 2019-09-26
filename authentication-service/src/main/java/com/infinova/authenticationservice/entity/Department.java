package com.infinova.authenticationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    private Integer id;

    private String departmentName;

    //@ApiModelProperty(value = "部门编号", dataType = "String", required = true)
    private String departmentCode;

    //@ApiModelProperty(value = "父级部门ID", dataType = "String", required = true)
    private String parentId;

    //@ApiModelProperty(value = "父级部门", dataType = "String", required = true)
    private String parentName;

    //@ApiModelProperty(value = "备注", dataType = "String", required = true)
    private String remark;

    //@ApiModelProperty(value = "创建时间", dataType = "Date", required = false)
    private Date creatTime;

    //@ApiModelProperty(value = "部门级别", dataType = "Integer", required = false)
    private Integer lever;

    //@ApiModelProperty(value = "子部门列表", dataType = "List<Department>", required = false)
    private List<Department> children;

   // @ApiModelProperty(value = "排序", dataType = "String", required = false)
    private String sort;

    private String title;

    private String key;

    private String value;

    public Department(Integer id, String departmentName, String departmentCode, String parentId, String parentName, String remark, Date creatTime, Integer lever, String sort, String title, String key, String value) {
        this.id = id;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.parentId = parentId;
        this.parentName = parentName;
        this.remark = remark;
        this.creatTime = creatTime;
        this.lever = lever;
        this.sort = sort;
        this.title = title;
        this.key = key;
        this.value = value;
    }
}
