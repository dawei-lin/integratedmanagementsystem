package com.infinova.imscommon.beans.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
@JsonSerialize(include = Inclusion.NON_NULL)
@ApiModel("返回对象")
public class AjaxMessageResult<T> extends CommonResult {

	// 返回对象，或者object, 或者list
	private T msg;

	public AjaxMessageResult(T t) {
		this.msg = t;
	}

	public AjaxMessageResult() {
	}

	public T getMsg() {
		return msg;
	}

	@SuppressWarnings("unchecked")
	public void setMsg(T msg) {
		if (null == msg) {
			msg = (T) new ArrayList<>();
		}
		this.msg = msg;
	}

}
