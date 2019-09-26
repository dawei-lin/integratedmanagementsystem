package com.infinova.imscommon.utils;

import java.util.UUID;

/**
 * GUID帮助类
 * 
 * @author yzhb
 *
 */
public class GuidUtil {

	public static String randomGuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
