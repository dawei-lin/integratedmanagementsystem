package com.infinova.authenticationservice.vo.res;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;

public class AjaxInvalidResult extends AjaxMessageResult<Object> {

	public static final Logger logger = LogManager.getLogger(AjaxInvalidResult.class);

	public AjaxInvalidResult() {
		this.setCode("-2");
		this.setDesc("parameter invalid");
	}

	public AjaxInvalidResult(String code) {
		this.setCode(code);
	}

	public AjaxInvalidResult(String code, Object msg) {
		this.setCode(code);
		this.setMsg(msg);
	}

	public AjaxInvalidResult(int code, Locale locale) {
		this.setCode(String.valueOf(code));
	}

	public AjaxInvalidResult(BindingResult result) {
		this();

		List<ObjectError> list = result.getAllErrors();
		StringBuffer errorMsg = new StringBuffer();
		for (ObjectError error : list) {
			FieldError fieldError = (FieldError) error;
			errorMsg.append("," + fieldError.getField() + ":" + error.getDefaultMessage());
		}
		this.setDesc(errorMsg.toString().substring(1));
		logger.error(this.getDesc());
	}

//	public AjaxInvalidResult(InfinovaBindingResult result) {
//		this();
//
//		List<InfinovaError> list = result.getErrors();
//		StringBuffer errorMsg = new StringBuffer();
//		for (InfinovaError error : list) {
//			errorMsg.append("," + error.getField() + ":" + error.getDefaultMessage());
//		}
//		this.setDesc(errorMsg.toString().substring(1));
//		logger.error(this.getDesc());
//	}

}
