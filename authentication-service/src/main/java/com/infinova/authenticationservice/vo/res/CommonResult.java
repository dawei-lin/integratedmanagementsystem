package com.infinova.authenticationservice.vo.res;

import io.swagger.annotations.ApiModelProperty;

public class CommonResult {

	// 0表示成功，非0表示失败，默认为成功
	@ApiModelProperty(value = "")
	private String code = "1";
	// 默认为成功
	private String desc = "system.desc.success";


	private static final String error = "system.desc.error";

	private static final String error_code = "0";

	// 消息类型
	private String msgType;

	public void setMsg(String code,String data) {
		this.code = code;
		this.desc = data;
	}

	public void setErrorMsg(){
		this.code = error_code;
		this.desc = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

}
