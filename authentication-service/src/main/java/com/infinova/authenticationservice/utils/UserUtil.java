package com.infinova.authenticationservice.utils;

import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {


	private static final String TOKEN_KEY = "token";

	@Autowired
	private static TokenService tokenService;

	public static UserDetailsInfo getUserDetailsInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			if (authentication instanceof AnonymousAuthenticationToken) {
				return null;
			}

			if (authentication instanceof UsernamePasswordAuthenticationToken) {
				return (UserDetailsInfo) authentication.getPrincipal();
			}
		}

		return null;
	}


	public static UserDetailsInfo getUserDetailsInfo(HttpServletRequest request) {

		String token = getToken(request);

		return tokenService.getUserDetailsInfo(token);
	}







	/**
	 * 根据参数或者header获取token
	 *
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getParameter(TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(TOKEN_KEY);
		}

		return token;
	}

}
