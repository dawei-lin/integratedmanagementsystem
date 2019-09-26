package com.infinova.authenticationservice.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.vo.Token;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * token存到redis的实现类<br>
 * jwt实现的token
 * 
 * @author ldw
 *
 */
@Primary
@Service
public class TokenServiceJWTImpl implements TokenService {

	private static final Logger log = LoggerFactory.getLogger(TokenServiceJWTImpl.class);

	/**
	 * token过期秒数
	 */
	@Value("${token.expire.seconds}")
	private Integer expireSeconds;

	/**
	 * 服务间调用token
	 */
	@Value("${token.expire.seconds}")
	private Integer shortTime;
	@Autowired
	private RedisTemplate<String, UserDetailsInfo> redisTemplate;

	/**
	 * 私钥
	 */
	@Value("${token.jwtSecret}")
	private String jwtSecret;

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";
	private static final String USER_NAME = "USER_NAME";
	private static final String USER_DEPARTMENT = "USER_DEPARTMENT";

	@Override
	public Token saveToken(UserDetailsInfo UserDetailsInfo) {
		UserDetailsInfo.setToken(UUID.randomUUID().toString());
		cacheUserDetailsInfo(UserDetailsInfo);

		// 登陆日志
//		Log loginfo = new Log("统一认证", 1, UserDetailsInfo.getUsername(), HttpServletRequestUtil.getIpAddr(request),
//				request.getMethod() + " " + request.getRequestURI(), "登录");
//		logService.save(loginfo);

		String jwtToken = createJWTToken(UserDetailsInfo);
		log.info(jwtToken);

		return new Token(jwtToken, UserDetailsInfo.getLoginTime());
	}

	/**
	 * 生成jwt
	 * 
	 * @param UserDetailsInfo
	 * @return
	 */
	public String createJWTToken(UserDetailsInfo UserDetailsInfo) {
		Map<String, Object> claims = new HashMap<>();
		// 放入一个随机字符串，通过该串可找到登陆用户
		claims.put(LOGIN_USER_KEY, UserDetailsInfo.getToken());

		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();

		return jwtToken;
	}


	/**
	 * 生成jwt  有效期一分钟
	 *
	 * @param UserDetailsInfo
	 * @return
	 */
	@Override
	public String createShortToken(UserDetailsInfo UserDetailsInfo) {
		Map<String, Object> claims = new HashMap<>(4);
		// 放入一个随机字符串，通过该串可找到登陆用户
		claims.put(USER_NAME, UserDetailsInfo.getUsername());
		claims.put(USER_DEPARTMENT,UserDetailsInfo.getDepartmentName());
		claims.put(LOGIN_USER_KEY,UserDetailsInfo.getToken());
		String jwtToken = Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis()+shortTime*1000))
				.signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();

		return jwtToken;
	}

	private void cacheUserDetailsInfo(UserDetailsInfo UserDetailsInfo) {
		UserDetailsInfo.setLoginTime(System.currentTimeMillis());
		UserDetailsInfo.setExpireTime(UserDetailsInfo.getLoginTime() + expireSeconds * 1000);
		// 根据uuid将 UserDetailsInfo缓存
		redisTemplate.boundValueOps(getTokenKey(UserDetailsInfo.getToken())).set(UserDetailsInfo, expireSeconds,
				TimeUnit.SECONDS);
	}

	/**
	 * 更新缓存的用户信息
	 */
	@Override
	public void refresh(UserDetailsInfo UserDetailsInfo) {
		cacheUserDetailsInfo(UserDetailsInfo);
	}

	@Override
	public UserDetailsInfo getUserDetailsInfo(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}
		return null;
	}

	@Override
	public boolean deleteToken(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			UserDetailsInfo UserDetailsInfo = redisTemplate.opsForValue().get(key);
			if (UserDetailsInfo != null) {
				redisTemplate.delete(key);
//				// 退出日志
//				Log loginfo = new Log("统一认证", 1, UserDetailsInfo.getUsername(),
//						HttpServletRequestUtil.getIpAddr(request), request.getMethod() + " " + request.getRequestURI(), "退出");
//				logService.save(loginfo);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean checkLocalToken(String jwtToken) {

		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			UserDetailsInfo UserDetailsInfo = redisTemplate.opsForValue().get(key);
			if (UserDetailsInfo != null) {
				return true;
			}
		}

		return false;
	}

	private String getTokenKey(String uuid) {
		return "tokens:" + uuid;
	}

	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (TokenServiceJWTImpl.class) {
				// 双重锁
				if (KEY == null) {
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}
		return KEY;
	}

	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}
		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken)
					.getBody();

			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
			log.error("{}已过期", jwtToken);
		} catch (Exception e) {
			log.error("{}", e);
		}
		return null;
	}


}
