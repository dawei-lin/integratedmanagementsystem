package com.infinova.imscommon.beans.res;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.AccessDeniedException;

public class AjaxExceptionResult extends AjaxMessageResult<Object> {

	public static final Logger logger = LogManager.getLogger(AjaxExceptionResult.class);

	public AjaxExceptionResult() {
		this.setCode("-1");
		this.setDesc("system.failure.operation");
	}

	public AjaxExceptionResult(Exception ex) {
		this();

		logger.error(this.getDesc(), ex);
	}

}
