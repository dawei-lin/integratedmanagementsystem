package com.infinova.authenticationservice.service.impl;
/**
 * @Project:integratedmanagementsystem
 * @File:TokenCheckServiceImpl
 * @Package:com.infinova.authenticationservice.service.impl
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/22 15:58
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.authenticationservice.dao.PermissionDao;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.security.securityconfig.SelfUserDetailsService;
import com.infinova.authenticationservice.service.TokenCheckService;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.utils.RedisUtil;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName:TokenCheckServiceImpl
 * @Description:
 * @author:ldw
 * @date:2019/7/22 15:58
 *
 *
 */
@Service
public class TokenCheckServiceImpl implements TokenCheckService {

    private static Key KEY = null;

    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    private static final Long MINUTES_10 = 10 * 60 * 1000L;

    private static final String TOKEN_KEY = "token";

    private static final String AUTH_REDIS = "auth_redis";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private SelfUserDetailsService userDetailsService;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Boolean checkAuthority(String token,String url,String method) {

        if (StringUtils.isNotBlank(token)) {
            System.out.println("token 校验："+token);
            UserDetailsInfo userDetailsInfo = tokenService.getUserDetailsInfo(token);
            if (userDetailsInfo != null) {
                userDetailsInfo = checkLoginTime(userDetailsInfo);

                return true;

                // 确保本地存在权限表缓存
//                checkLocalAuth();
//                if (url.length()>1&&url.startsWith("/")){
//                    url = url.substring(1);
//                }
//                url = url.replaceAll("/",":");
//                url = url +":" + method.toLowerCase();
//
//                return checkAuth(url,userDetailsInfo);


            }
        }

        return false;
    }


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
     * 判断缓存中是否存在权限集，没有的话加载到缓存
     */
    private void checkLocalAuth(){
        System.out.println("checkLocalAuth");
        if (!redisUtil.hasKey(AUTH_REDIS)){
            System.out.println("权限缺失补齐");
            List<Permission> permissions = permissionDao.listAll();
            Iterator permissionListIterator = permissions.iterator();
            while (permissionListIterator.hasNext()) {
                Permission permission = (Permission) permissionListIterator.next();
                redisUtil.sSet(AUTH_REDIS,permission.getPermission());
            }
        }
    }

    /**
     * 用户权限校验   由于前端菜单等原因，校验数据根本不全
     * @param url
     * @param userDetailsInfo
     * @return
     */
    private boolean checkAuth(String url,UserDetailsInfo userDetailsInfo){
        // 判断url是否收到权限限制
        if (!redisUtil.sHasKey(AUTH_REDIS,url)){
            // 请求不受权限控制
            return true;
        }else {
            List<Permission> permissions = userDetailsInfo.getPermissions();
            Map<String,String> map = new HashMap<>();
            Iterator<Permission> iterator = permissions.iterator();
            while (iterator.hasNext()){
                Permission permission = iterator.next();
                map.put(permission.getPermission(),null);
            }

            // 判断用户是否存在此权限
            if (map.containsKey(url)){
                return true;
            }else {
                return false;
            }
        }
    }
}
