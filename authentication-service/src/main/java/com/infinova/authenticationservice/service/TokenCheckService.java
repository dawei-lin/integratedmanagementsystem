package com.infinova.authenticationservice.service;
/**
 * @Project:integratedmanagementsystem
 * @File:TokenCheckService
 * @Package:com.infinova.authenticationservice.service
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/22 15:57
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

/**
 * @ClassName:TokenCheckService
 * @Description:
 * @author:ldw
 * @date:2019/7/22 15:57
 *
 *
 */
public interface TokenCheckService {


    // 请求 权限校验
    Boolean checkAuthority(String token,String url,String method);

}
