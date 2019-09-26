package com.infinova.authenticationservice.security.securityconfig;


import com.infinova.authenticationservice.entity.User;
import com.infinova.authenticationservice.security.filter.TokenFilter;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.service.UserService;
import com.infinova.authenticationservice.utils.HttpServletRequestUtil;
import com.infinova.authenticationservice.utils.ResponseUtil;
import com.infinova.authenticationservice.vo.Token;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import com.infinova.authenticationservice.vo.res.MessageResult;
import com.infinova.authenticationservice.vo.res.ResponseInfo;
import javafx.scene.control.TableFocusModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * spring security处理器
 * 
 * @author ldw
 *
 */
@Configuration
public class SecurityHandlerConfig {

	private static final String TOKEN_KEY = "token";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	/**
	 * 登陆成功，返回Token
	 */
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return new AuthenticationSuccessHandler() {

			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
				//  生成token 并 返回
				UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authentication.getPrincipal();

				Map<String,String> map = new HashMap<>(8);

				//获取上次登录IP,时间
				User user =  userService.getUser(userDetailsInfo.getUsername());

				String lastLoginIp = user.getLastLoginIp();
				String lastLoginTime = user.getLastLoginTime();

				map.put("lastLoginIp",lastLoginIp);
				map.put("lastLoginTime",lastLoginTime);

				//用户名，头像信息
				map.put("realname",userDetailsInfo.getRealname());
				map.put("headImgUrl",user.getHeadImgUrl());

				String loginIp = HttpServletRequestUtil.getIpAddr(request);

				//插入新的登录时间IP 和 token
				userService.changeIpAndLoginTime(user.getId(),loginIp);
				Token token = tokenService.saveToken(userDetailsInfo);

				map.put("token",token.getToken());

				MessageResult messageResult = new MessageResult();

				messageResult.setMsg(map);
				System.out.println("登录成功");
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), messageResult);
			}
		};
	}

	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return new AuthenticationFailureHandler() {

			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
				String msg = null;
				if (exception instanceof BadCredentialsException) {
					msg = "用户名或密码错误！";
				} else {
					msg = exception.getMessage();
				}
				ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
			}
		};

	}

	/**
	 * 未登录，返回401
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {

			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException, ServletException {
				System.out.println("未登录，返回401");

				String msg = authException.getMessage();

				ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
			}
		};
	}

	/**
	 * 退出处理
	 */
	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return new LogoutSuccessHandler() {

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
				ResponseInfo info = new ResponseInfo("1" + "", "退出成功");

				// TODO token验证
				String token = getToken(request);
				if (StringUtils.isNotBlank(token)) {
					tokenService.deleteToken(token);
				}
				ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
			}
		};

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
