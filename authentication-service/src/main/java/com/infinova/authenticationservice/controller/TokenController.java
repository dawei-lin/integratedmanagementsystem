package com.infinova.authenticationservice.controller;
/**
 * @Project:integratedmanagementsystem
 * @File:TokenController
 * @Package:com.infinova.authenticationservice.controller
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/22 11:33
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.authenticationservice.service.TokenCheckService;
import com.infinova.authenticationservice.service.TokenService;
import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName:TokenController
 * @Description: check token
 * @author:ldw
 * @date:2019/7/22 11:33
 */
@RestController
@RequestMapping("/token")
public class TokenController {


    @Autowired
    private TokenCheckService tokenCheckService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/check")
    public AjaxMessageResult<Object> checkToken( @RequestParam("token") String token,
                                                 @RequestParam("url") String url,
                                                 @RequestParam("method") String method) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            System.out.println(method);
            if (!tokenCheckService.checkAuthority(token,url,method)){
                // 生成服务间调用token
                data.setCode("0");
                data.setDesc("登录过期");
                return data;
            }else {
                token = tokenService.createShortToken(tokenService.getUserDetailsInfo(token));
                data.setMsg(token);
            }
        } catch (Exception e) {
            data.setCode("0");
            data.setDesc("登录过期");
            return data;
        }
        return data;
    }






}
