package com.infinova.authenticationservice.utils;


import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户IP
 */
public class HttpServletRequestUtil {

	public static String getIpAddr(HttpServletRequest request) {
    	if(null == request)
    		return "";
        String ip = request.getHeader("X-Real-IP"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("x-forwarded-for"); 
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
	}
}
