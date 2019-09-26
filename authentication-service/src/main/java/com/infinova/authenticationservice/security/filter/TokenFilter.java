package com.infinova.authenticationservice.security.filter;

import com.infinova.authenticationservice.security.securityconfig.SelfUserDetailsService;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token过滤器
 * 
 * @author ldw
 *
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

	private static final String TOKEN_KEY = "token";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SelfUserDetailsService userDetailsService;


	private static final Long MINUTES_10 = 10 * 60 * 1000L;

	/**
	 * 校验
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getToken(request);
		if (StringUtils.isNotBlank(token)) {
			UserDetailsInfo userDetailsInfo = tokenService.getUserDetailsInfo(token);
			if (userDetailsInfo != null) {
				userDetailsInfo = checkLoginTime(userDetailsInfo);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetailsInfo, null, null);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 校验时间<br>
	 * 过期时间与当前时间对比，临近过期10分钟内的话，自动刷新缓存
	 * 
	 * @param userDetailsInfo
	 * @return
	 */
	private UserDetailsInfo checkLoginTime(UserDetailsInfo userDetailsInfo) {
		long expireTime = userDetailsInfo.getExpireTime();
		long currentTime = System.currentTimeMillis();
		if (expireTime - currentTime <= MINUTES_10) {
			String token = userDetailsInfo.getToken();
			userDetailsInfo = (UserDetailsInfo) userDetailsService.loadUserByUsername(userDetailsInfo.getUsername());
			userDetailsInfo.setToken(token);
			tokenService.refresh(userDetailsInfo);
			// TODO 更新时间
			System.out.println("更新时间");
		}
		return userDetailsInfo;
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
