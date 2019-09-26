package com.infinova.imszuul.bean.res;

public class CommonResult {

	// 0表示成功，非0表示失败，默认为成功
	private String code = "1";
	// 默认为成功
	private String desc = "成功";
	// 消息类型
	private String msgType;

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
