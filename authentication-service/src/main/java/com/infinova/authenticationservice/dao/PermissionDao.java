package com.infinova.authenticationservice.dao;

import com.infinova.authenticationservice.entity.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface PermissionDao {

	@Insert("insert into sys_function(id, name, nameIntl, level, parentId, permission, sort, href, jumpType, iconUrl, description, extInfo, enable) values(uuid(), #{name}, #{nameIntl}, #{level}, #{parentId}, #{permission}, #{sort}, #{href}, #{jumpType}, #{iconUrl}, #{description}, #{extInfo}, #{enable})")
	int save(Permission permission);

	@Delete("delete from sys_function where id = #{id}")
	int delete(String id);

	@Delete("delete from sys_function where parentId = #{id}")
	int deleteByParentId(String id);

	@Delete("delete from sys_role_permission where permissionId = #{permissionId}")
	int deleteRolePermission(String permissionId);

	@Update("update sys_function t set name = #{name}, level = #{level}, parentId = #{parentId}, permission = #{permission}, sort = #{sort}, href = #{href}, jumpType = #{jumpType}, iconUrl = #{iconUrl}, description = #{description}, extInfo = #{extInfo}, enable = #{enable} where t.id = #{id}")
	int update(Permission permission);

	@Update("update sys_function set sort = concat(#{newPrefix}, substr(sort, length(#{newPrefix}) + 1)) where sort like concat(#{oldPrefix}, '%')")
	int updateSort(@Param("oldPrefix") String oldPrefix, @Param("newPrefix") String newPrefix);

	@Select("select * from sys_function t where t.id = #{id}")
	Permission getById(String id);

	@Select("select * from sys_function order by sort")
	List<Permission> listAll();

	@Select("select * from sys_function where level <= #{level} order by sort")
	List<Permission> listAllByLowestLevel(Integer level);

	@Select("select t.* from sys_function t inner join sys_role_permission rp on t.id = rp.permissionId where rp.roleId = #{roleId} order by t.sort")
	List<Permission> listByRoleId(String roleId);

	@Select("select distinct t.* from sys_function t inner join sys_role_permission rp on t.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} order by t.sort")
	List<Permission> listByUserId(String userId);

	@Select("select ru.userId from sys_role_permission rp inner join sys_role_user ru on ru.roleId = rp.roleId where rp.permissionId = #{permissionId}")
	Set<String> listUserIds(String permissionId);

	@Select("select * from sys_function where sort like concat(#{sortPrefix}, '%') order by sort")
	List<Permission> listBySortPrefix(String sortPrefix);

}
