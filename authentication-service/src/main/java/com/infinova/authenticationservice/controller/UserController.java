package com.infinova.authenticationservice.controller;


import com.infinova.authenticationservice.annotation.HttpReqAnnotaion;
import com.infinova.authenticationservice.dao.UserDao;
import com.infinova.authenticationservice.entity.User;
import com.infinova.authenticationservice.page.PageTableHandler;
import com.infinova.authenticationservice.page.PageTableRequest;
import com.infinova.authenticationservice.page.PageTableResponse;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.service.UserService;
import com.infinova.authenticationservice.utils.UserUtil;
import com.infinova.authenticationservice.vo.UserVO;
import com.infinova.authenticationservice.vo.res.AjaxExceptionResult;
import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import com.infinova.authenticationservice.vo.res.MessageResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关接口
 *
 * @author ldw
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenService tokenService;

    @HttpReqAnnotaion
    @PostMapping
    @ApiOperation(value = "保存用户")
    public User saveUser(@RequestBody UserVO userVO) {
        if (userVO.getId() != null) {
            return userService.updateUser(userVO);
        } else {
            User u = userService.getUser(userVO.getUsername());
            if (u != null) {
                throw new IllegalArgumentException(userVO.getUsername() + "已存在");
            }
            return userService.saveUser(userVO);
        }
    }

    @HttpReqAnnotaion
    @PutMapping
    public User updateUser(
            @RequestBody UserVO userVO) {
        return userService.updateUser(userVO);
    }

    @HttpReqAnnotaion
    @PutMapping(params = "headImgUrl")
    @ApiOperation(value = "修改头像")
    public void updateHeadImgUrl(@ApiParam(name = "headImgUrl", value = "头像图片链接", required = true) String headImgUrl) {
        User user = UserUtil.getUserDetailsInfo();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setHeadImgUrl(headImgUrl);
        userService.updateUser(userVO);
        log.debug("{}修改了头像", user.getUsername());
    }

    @HttpReqAnnotaion
    @PutMapping("/{username}")
    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAuthority('sys:user:password')")
    public void changePassword(
                               @PathVariable String username,
                               @ApiParam(name = "oldPassword", value = "旧密码", required = true)  String oldPassword,
                               @ApiParam(name = "newPassword", value = "新密码", required = true) String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @PostMapping("/list")
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAuthority('sys:user:query')")
    @ApiParam(name = "request", value = "分页查询对象", required = true)
    public MessageResult listUsers(
            @RequestBody PageTableRequest request) {

        MessageResult messageResult = new MessageResult();

        PageTableResponse pageTableResponse = new PageTableHandler(new PageTableHandler.CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return userDao.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {
            @Override
            public List<UserVO> list(PageTableRequest request) {
                List<User> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
                List<UserVO> resList = new ArrayList<>();
                for (User user : list) {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(user, userVO);
                    userVO.setRoleIds(userDao.getUserRole(user.getId()));
                    resList.add(userVO);
                }
                return resList;
            }
        }).handle(request);
        messageResult.setMsg(pageTableResponse);

        return messageResult;
    }

    @ApiOperation(value = "获取当前登录用户")
    @GetMapping("/current")
    public AjaxMessageResult<Object> currentUser() {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            data.setMsg(UserUtil.getUserDetailsInfo());
        } catch (Exception e) {
            return new AjaxExceptionResult(e);
        }
        return data;
    }

    //@ApiOperation(value = "获取当前登录用户 其他信息(headUrl,username,realname)")
    @GetMapping("/currentUserMsg")
    public AjaxMessageResult<Object> currentUserMsg(HttpServletRequest request) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();

        //获取上次登录IP,时间
        log.info(UserUtil.getUserDetailsInfo().getUsername());

        Map<String, Object> map = new HashMap<>();
        User user = userService.getUser(UserUtil.getUserDetailsInfo().getUsername());

        //用户名，头像信息
        map.put("username", user.getUsername());
        map.put("realname", user.getRealname());
        map.put("headImgUrl", user.getHeadImgUrl());
        map.put("departmentId", user.getDepartmentId());
        map.put("departmentName", user.getDepartmentName());
        data.setMsg(map);

        return data;
    }

    // @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('sys:user:query')")
    // @ApiParam(name = "id", value = "用户id", required = true)
    public AjaxMessageResult<Object> user(
            @PathVariable String id) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            data.setMsg(userDao.getById(id));
        } catch (Exception e) {
            return new AjaxExceptionResult(e);
        }
        return data;
    }

    /**
     * 根据角色获取用户信息
     *
     * @return
     */
    @GetMapping("/role")
    // @PreAuthorize("hasAuthority('sys:user:query')")
    // @ApiParam(name = "id", value = "用户id", required = true)
    public AjaxMessageResult<Object> getRoleUserList(String roleName) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            data.setMsg(userDao.getRoleUser(roleName));
        } catch (Exception e) {
            return new AjaxExceptionResult(e);
        }
        return data;
    }


}
