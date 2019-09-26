package com.infinova.authenticationservice.vo.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
@JsonSerialize(include = Inclusion.NON_NULL)
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
