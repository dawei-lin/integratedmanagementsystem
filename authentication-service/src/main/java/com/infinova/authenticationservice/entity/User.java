package com.infinova.authenticationservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@ApiModel(value = "User  用户对象")
public class User extends BaseEntity<String> {

	private static final long serialVersionUID = -6525908145032868837L;

	@ApiModelProperty(value = "用户名", dataType = "String", required = true)
	private String username;
	@ApiModelProperty(value = "工号", dataType = "String", required = true)
	private String workCode;

	@ApiModelProperty(value = "密码", dataType = "String", required = true)
	@Ignore
	private String password;

	@ApiModelProperty(value = "真实姓名", dataType = "String", required = false)
	private String realname;
	@ApiModelProperty(value = "头像链接", dataType = "String", required = false)
	private String headImgUrl;
	@ApiModelProperty(value = "手机号", dataType = "String", required = false)
	private String phone;
	@ApiModelProperty(value = "邮件地址", dataType = "String", required = false)
	private String email;

	@ApiModelProperty(value = "用户级别", dataType = "Integer", required = false, example = "0")
	private Integer userLever;

	@ApiModelProperty(value = "部门ID", dataType = "Integer", required = false, example = "0")
	private Integer departmentId;

	@ApiModelProperty(value = "部门名称", dataType = "String", required = false)
	private String departmentName;

	@ApiModelProperty(value = "直属上级工号", dataType = "String", required = false)
	private String manager;

	@ApiModelProperty(value = "所有上级工号", dataType = "String", required = false)
	private String managerStr;

	@ApiModelProperty(value = "状态码", dataType = "Integer", required = false, example = "0")
	private Integer status;

	@ApiModelProperty(value = "上次登录IP", dataType = "String", required = false)
	private String lastLoginIp;

	@ApiModelProperty(value = "上次登录时间", dataType = "String", required = false)
	private String lastLoginTime;

	@ApiModelProperty(value = "是否能多点登录", dataType = "Boolean", required = false)
	private boolean myltiLogin;

	@ApiModelProperty(value = "创建时间", dataType = "Date", required = false)
	private Date createTime;

	@ApiModelProperty(value = "更新日期", dataType = "Date", required = false)
	private Date updateTime;

	@ApiModelProperty(value = "账号有效期", dataType = "Date", required = false)
	private Date validTime;

	@ApiModelProperty(value = "用户描述", dataType = "String", required = false)
	private String description;

	private Integer isDeal;

	public interface Status {
		int DISABLED = 0;
		int VALID = 1;
		int LOCKED = 2;
	}

}
