package com.infinova.authenticationservice.vo.res;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;

public class AjaxExceptionResult extends AjaxMessageResult<Object> {

	public static final Logger logger = LogManager.getLogger(AjaxExceptionResult.class);

	public AjaxExceptionResult() {
		this.setCode("-1");
		this.setDesc("操作失败");
	}

	public AjaxExceptionResult(Exception ex) {
		this();

		logger.error(this.getDesc(), ex);

		if (ex instanceof AccessDeniedException) {
			this.setCode("-3");
		} else if (ex instanceof ApplicationException) {
			ApplicationException ape = (ApplicationException) ex;
			this.setCode(ape.getErrorCode());
		}

	}

}
