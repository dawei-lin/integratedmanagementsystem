package com.infinova.imszuul.service.impl;
/**
 * @Project:integratedmanagementsystem
 * @File:CheckServiceImpl
 * @Package:com.infinova.imszuul.service.impl
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/22 14:55
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.imszuul.bean.res.AjaxMessageResult;
import com.infinova.imszuul.service.CheckService;
import org.springframework.stereotype.Service;

/**
 * @ClassName:CheckServiceImpl
 * @Description:
 * @author:ldw
 * @date:2019/7/22 14:55
 *
 *
 */
@Service
public class CheckServiceImpl implements CheckService {

    @Override
    public AjaxMessageResult<Object> checkToken(String token,String url,String method) {
        AjaxMessageResult<Object> ajaxMessageResult = new AjaxMessageResult<>();
        ajaxMessageResult.setCode("0");
        ajaxMessageResult.setDesc("服务调用失败");
        return ajaxMessageResult;
    }

    @Override
    public void test() {

    }
}
