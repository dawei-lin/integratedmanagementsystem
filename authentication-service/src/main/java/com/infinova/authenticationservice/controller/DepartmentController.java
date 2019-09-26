package com.infinova.authenticationservice.controller;


import com.infinova.authenticationservice.dao.DepartmentDao;
import com.infinova.authenticationservice.entity.Department;
import com.infinova.authenticationservice.service.DepartmentService;
import com.infinova.authenticationservice.utils.DepartmentUtil;
import com.infinova.authenticationservice.vo.res.AjaxExceptionResult;
import com.infinova.authenticationservice.vo.res.AjaxMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * @ClassName:DepartmentController
 * @Description:
 * @author:ldw
 * @date:2019/4/20 11:04
 */

//@Api(tags = "部门管理")
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/tree")
    //@ApiOperation(value = "获取部门树", notes = "所有部门的树")
    public AjaxMessageResult<Object> permissionsTree() {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            List<Department> departments = departmentService.listDepartmentAll();
            if (departments.size()>0){
                data.setMsg(DepartmentUtil.listGetTree(departments));
            }else{
                data.setMsg(new HashMap<>());
            }
        } catch (Exception e) {
            return new AjaxExceptionResult(e);
        }
        return data;
    }

    //@HttpReqAnnotaion
    @PostMapping
    //@ApiOperation(value = "增加部门")
    //@PreAuthorize("hasAuthority('sys:menu:add')")@ApiParam(name = "department", value = "部门对象", required = true)
    public AjaxMessageResult<Object> save(
             @RequestBody Department department) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();

        if (department.getId()==null){

        }
        //不存在父级则是修改顶点(父级不可删除)
        if(department.getId()==null){
            data.setMsg("0","请选择父节点！");
            return data;
        }else{
            if (department.getId()==0){
                //修改顶点
                department.getChildren().get(0).setTitle(department.getChildren().get(0).getDepartmentName());
                departmentDao.updateByPrimaryKeySelective(department.getChildren().get(0));
                return data;
            }

            try {
                departmentService.insert(department);
            } catch (Exception e) {
                data.setMsg("0","部门名称不可重复");
                return data;
            }
            return data;
        }
    }

    @DeleteMapping("/{departmentId}")
    //@ApiOperation(value = "删除部门")@ApiParam(name = "department", value = "部门对象", required = true)
    //@PreAuthorize("hasAuthority('sys:menu:add')")
    public AjaxMessageResult<Object> remove(
             @PathVariable Integer departmentId) {
        AjaxMessageResult<Object> data = new AjaxMessageResult<Object>();
        try {
            departmentService.deleteByPrimaryKey(departmentId);
        } catch (Exception e) {
            return new AjaxExceptionResult(e);
        }
        return data;
    }

}