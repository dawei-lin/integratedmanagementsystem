package com.infinova.authenticationservice.dao;

import com.infinova.authenticationservice.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    @Select("SELECT * FROM t_department order by sort")
    List<Department> listDepartmentAll();

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}