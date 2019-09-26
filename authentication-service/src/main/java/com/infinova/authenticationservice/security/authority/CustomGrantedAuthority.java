package com.infinova.authenticationservice.security.authority;

import com.infinova.authenticationservice.entity.Menu;
import com.infinova.authenticationservice.entity.Permission;
import com.infinova.authenticationservice.entity.Role;
import com.infinova.authenticationservice.utils.JSONUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: 权限信息载体封装
 *
 * @author ldw
 */
public class CustomGrantedAuthority implements GrantedAuthority {

    /**
     * 角色信息
     */
    private final List<Role> role;

    private final List<Permission> permission;

    private final List<Menu> menu;


    /**
     * 构造角色权限数据
     *
     * @param role
     * @param permission
     */
    public CustomGrantedAuthority(List<Role> role, List<Menu> menu, List<Permission> permission) {
        this.role = role;
        this.menu = menu;
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        Map<String, List> map = new HashMap<>();
        map.put("role", role);
        map.put("permission", permission);
        map.put("menu", menu);
        return JSONUtils.toJSON(map);
    }
}
