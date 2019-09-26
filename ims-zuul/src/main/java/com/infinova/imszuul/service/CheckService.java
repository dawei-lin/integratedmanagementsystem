package com.infinova.imszuul.service;
/**
 * @Project:integratedmanagementsystem
 * @File:CheckService
 * @Package:com.infinova.imszuul.service
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/22 14:52
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.imszuul.bean.res.AjaxMessageResult;
import com.infinova.imszuul.service.impl.CheckServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName:CheckService
 * @Description:
 * @author:ldw
 * @date:2019/7/22 14:52
 */
@FeignClient(name = "auth-service",fallback = CheckServiceImpl.class)
public interface CheckService {

    /**
     * @param token
     * @param url
     * @param method
     * @return
     */
    @RequestMapping(value = "auth/token/check")
    AjaxMessageResult<Object> checkToken(@RequestParam("token")  String token,
                                         @RequestParam("url")  String url,
                                         @RequestParam("method") String method);

    @RequestMapping(value = "auth/department/treetest")
    void test();

}
