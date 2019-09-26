package com.infinova.authenticationservice.security.securityconfig;

import com.infinova.authenticationservice.dao.PermissionDao;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.entity.User;
import com.infinova.authenticationservice.service.UserService;
import com.infinova.authenticationservice.utils.PermissionUtil;
import com.infinova.authenticationservice.vo.UserDetailsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 根据 userID 获取数据库 user 信息
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {


    private static Logger logger = LoggerFactory.getLogger(SelfUserDetailsService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUser(username);

        if (null == user) {
            throw new AuthenticationCredentialsNotFoundException("用户名或密码错误！");
        } else if (user.getStatus() == User.Status.LOCKED) {
            throw new LockedException("用户被锁定,请联系管理员");
        } else if (user.getStatus() == User.Status.DISABLED) {
            throw new DisabledException("用户已作废");
        } else if (user.getValidTime().before(new Date())) {
            throw new DisabledException("账号已过期");
        }

        UserDetailsInfo userDetailsInfo = new UserDetailsInfo();
        BeanUtils.copyProperties(user, userDetailsInfo);

        List<Permission> permissions = permissionDao.listByUserId(user.getId());
        userDetailsInfo.setPermissions(permissions);

        Permission tree = PermissionUtil.listToTree(userDetailsInfo.getPermissions());
        userDetailsInfo.setPermission(tree);


        return userDetailsInfo;
    }
}
