package com.infinova.authenticationservice.service;


import com.infinova.authenticationservice.vo.Token;
import com.infinova.authenticationservice.vo.UserDetailsInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Token管理器<br>
 * 可存储到redis或者数据库<br>
 * 具体可看实现类<br>
 * 默认基于redis，实现类为 com.boot.security.server.feignservice.impl.TokenServiceJWTImpl<br>
 * @author xrx
 *
 */
public interface TokenService {

	Token saveToken(UserDetailsInfo UserDetailsInfo);

    String createShortToken(UserDetailsInfo UserDetailsInfo);

    void refresh(UserDetailsInfo UserDetailsInfo);

	UserDetailsInfo getUserDetailsInfo(String token);

	boolean deleteToken(String jwtToken);

	boolean checkLocalToken(String jwtToken);

}
