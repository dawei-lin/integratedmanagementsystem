package com.infinova.authenticationservice.service;
/**
 * @Project:CooperativeProductMS_server
 * @File:DepartmentService
 * @Package:com.infinova.pcms.pcmssecurity.service
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/4/20 11:01
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */


import com.infinova.authenticationservice.entity.Department;

import java.util.List;

/**
 * @ClassName:DepartmentService
 * @Description:
 * @author:ldw
 * @date:2019/4/20 11:01
 *
 *
 */
public interface DepartmentService {

    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record, Integer fatherId);

    List<Department> listDepartmentAll();

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

}
