package com.infinova.authenticationservice.feiginservice;
/**
 * @Project:integratedmanagementsystem
 * @File:deprService
 * @Package:com.infinova.authenticationservice.feiginService
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/9/2 10:09
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName:deprService
 * @Description:
 * @author:ldw
 * @date:2019/9/2 10:09
 */
@FeignClient(name = "depr-service")
public interface DeprService {

    /**
     * 通知 下服务 更新部门
     * @return
     */
    @RequestMapping(value = "depr/dept/changeDept" ,method = RequestMethod.PUT)
    AjaxMessageResult<Object> syncDepartment(@RequestParam("oldDeptId") String oldDeptId,
                                         @RequestParam("newDeptId") String newDeptId,
                                         @RequestParam("newDeptName") String newDeptName);

}
