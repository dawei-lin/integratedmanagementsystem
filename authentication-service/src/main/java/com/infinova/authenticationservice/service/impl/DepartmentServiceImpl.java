package com.infinova.authenticationservice.service.impl;
/**
 * @Project:CooperativeProductMS_server
 * @File:DepartmentServiceImpl
 * @Package:com.infinova.pcms.pcmssecurity.service.impl
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/4/20 11:02
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */


import com.infinova.authenticationservice.dao.DepartmentDao;
import com.infinova.authenticationservice.entity.Department;
import com.infinova.authenticationservice.feiginservice.DeprService;
import com.infinova.authenticationservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName:DepartmentServiceImpl
 * @Description:
 * @author:ldw
 * @date:2019/4/20 11:02
 *
 *
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DeprService deprService;

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        Department department = departmentDao.selectByPrimaryKey(id);
        Department departmentParent = departmentDao.selectByPrimaryKey(Integer.valueOf(department.getParentId()) );
        deprService.syncDepartment(department.getId().toString(),departmentParent.getId().toString(),departmentParent.getDepartmentName());
        return departmentDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int insert(Department record) {

        List<Department> departments = record.getChildren();
        if(departments.size()>0){
            Department department = departments.get(0);

            if(department.getId()==null){
                //新增
                Department departmentFather = departmentDao.selectByPrimaryKey(record.getId());
                department.setParentId(departmentFather.getId().toString());
                department.setParentName(departmentFather.getDepartmentName());
                department.setLever(departmentFather.getLever()+1);
                department.setSort(departmentFather.getSort()+"001");
                department.setTitle(department.getDepartmentName());
                departmentDao.insert(department);
                department.setKey(department.getId().toString());
                department.setValue(department.getId().toString());
                return departmentDao.updateByPrimaryKeySelective(department);
            }else {
                //修改
                Department departmentFather = departmentDao.selectByPrimaryKey(record.getId());
                department.setParentId(departmentFather.getId().toString());
                department.setParentName(departmentFather.getDepartmentName());
                department.setLever(departmentFather.getLever()+1);
                department.setSort(departmentFather.getSort()+"001");
                department.setTitle(department.getDepartmentName());
                return departmentDao.updateByPrimaryKeySelective(department);
            }
        }else {
            return 0;
        }

    }

    @Override
    @Transactional
    public int insertSelective(Department department,Integer fatherId) {
        Department father = departmentDao.selectByPrimaryKey(fatherId);
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < father.getLever(); i++) {
            s.append("001");
        }
        department.setTitle(department.getDepartmentName());
        department.setSort(s.toString());
        departmentDao.insert(department);
        department.setKey(department.getId().toString());
        department.setValue(department.getId().toString());
        return departmentDao.updateByPrimaryKeySelective(department);
    }

    @Override
    public Department selectByPrimaryKey(Integer id) {
        return departmentDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> listDepartmentAll() {
        return departmentDao.listDepartmentAll();
    }

    @Override
    public int updateByPrimaryKeySelective(Department record) {
        return departmentDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Department record) {
        return departmentDao.updateByPrimaryKey(record);
    }
}
