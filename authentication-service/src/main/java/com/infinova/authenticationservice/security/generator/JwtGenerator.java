package com.infinova.authenticationservice.security.generator;

import com.alibaba.fastjson.JSONObject;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.entity.TokenModel;
import com.infinova.authenticationservice.vo.Token;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: ldw
 */
@Component
@Slf4j
public class JwtGenerator {

    /**
     * token过期秒数
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;
    /**
     * 私钥
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    private static Key KEY = null;
    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    /**
     * 报存token到redis
     * @param UserDetailsInfo
     * @return
     */
    public Token saveToken(UserDetailsInfo UserDetailsInfo) {
        UserDetailsInfo.setToken(UUID.randomUUID().toString());
        UserDetailsInfo.setLoginTime(System.currentTimeMillis());
        UserDetailsInfo.setExpireTime(UserDetailsInfo.getLoginTime() + expireSeconds * 1000);

        TokenModel model = new TokenModel();
        model.setId(UserDetailsInfo.getToken());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setExpireTime(new Date(UserDetailsInfo.getExpireTime()));
        model.setVal(JSONObject.toJSONString(UserDetailsInfo));

        log.info("报存token到model");
//        tokenDao.save(model);
//        // 登陆日志
//        logService.save(UserDetailsInfo, "登陆", true, null);

        String jwtToken = createJWTToken(UserDetailsInfo);

        return new Token(jwtToken, UserDetailsInfo.getLoginTime());
    }

    /**
     * 生成jwt
     *
     * @param UserDetailsInfo
     * @return
     */
    private String createJWTToken(UserDetailsInfo UserDetailsInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, UserDetailsInfo.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;
    }

    /**
     * 刷新更新时间
     * @param UserDetailsInfo
     */
    public void refresh(UserDetailsInfo UserDetailsInfo) {
        UserDetailsInfo.setLoginTime(System.currentTimeMillis());
        UserDetailsInfo.setExpireTime(UserDetailsInfo.getLoginTime() + expireSeconds * 1000);

//        TokenModel model = tokenDao.getById(UserDetailsInfo.getToken());
//        model.setUpdateTime(new Date());
//        model.setExpireTime(new Date(UserDetailsInfo.getExpireTime()));
//        model.setVal(JSONObject.toJSONString(UserDetailsInfo));
//
//        tokenDao.update(model);
        log.info("刷新redis");
    }

    /**
     * 获取用户信息
     * @param jwtToken
     * @return
     */
    public UserDetailsInfo getUserDetailsInfo(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            //TokenModel model = tokenDao.getById(uuid);
            log.info("从redis取出tokenModel");
            TokenModel model = new TokenModel();
            model.setVal("123");
            return toUserDetailsInfo(model);
        }
        return null;
    }

    /**
     * 删除token
     * @param jwtToken
     * @return
     */
    public boolean deleteToken(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            TokenModel model = new TokenModel();
            model.setVal("123");
            log.info("从redis中取出token");
            UserDetailsInfo UserDetailsInfo = toUserDetailsInfo(model);
            if (UserDetailsInfo != null) {
//                tokenDao.delete(uuid);
//                logService.save(UserDetailsInfo, "退出", true, null);
                log.info("从redis移除token");
                return true;
            }
        }

        return false;
    }

    private UserDetailsInfo toUserDetailsInfo(TokenModel model) {
        if (model == null) {
            return null;
        }

        // 校验是否已过期
        if (model.getExpireTime().getTime() > System.currentTimeMillis()) {
            return JSONObject.parseObject(model.getVal(), UserDetailsInfo.class);
        }

        return null;
    }


    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (JwtGenerator.class) {
                if (KEY == null) {// 双重锁
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }

        return KEY;
    }



    private String getUUIDFromJWT(String jwt) {
        if ("null".equals(jwt) || StringUtils.isBlank(jwt)) {
            return null;
        }

        Map<String, Object> jwtClaims = null;
        try {
            jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
        } catch (ExpiredJwtException e) {
            log.error("{}已过期", jwt);
        } catch (Exception e) {
            log.error("{}", e);
        }

        return null;
    }
}
