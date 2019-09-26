package com.infinova.authenticationservice.dao;

import com.infinova.authenticationservice.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface RoleDao {

	@Insert("insert into sys_role(id, rolename, description, createTime, updateTime,departmentId,departmentName,isDel) values(uuid(), #{rolename}, #{description}, now(),now(),#{departmentId},#{departmentName},1)")
	int save(Role role);

	int count(@Param("params") Map<String, Object> params);

	List<Role> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                    @Param("limit") Integer limit);

	@Select("select * from sys_role t where t.id = #{id}")
	Role getById(String id);

	@Select("select * from sys_role t where t.rolename = #{rolename}")
	Role getRole(String rolename);

	@Update("update sys_role t set t.rolename = #{rolename}, t.description = #{description}, updateTime = now(),departmentId=#{departmentId}, departmentName = #{departmentName} where t.id = #{id}")
	int update(Role role);

	@Select("select * from sys_role r inner join sys_role_user ru on r.id = ru.roleId where ru.userId = #{userId}")
	List<Role> listByUserId(String userId);

	@Delete("delete from sys_role_permission where roleId = #{roleId}")
	int deleteRolePermission(String roleId);

	@Select("select permissionId from sys_role_permission where roleId = #{roleId}")
	List<String> selectRolePermission(String roleId);

	int saveRolePermission(@Param("roleId") String roleId, @Param("permissionIds") List<String> permissionIds);

	@Delete("delete from sys_role where id = #{id}")
	int delete(String id);

	@Delete("delete from sys_role_user where roleId = #{roleId}")
	int deleteRoleUser(String roleId);

}
