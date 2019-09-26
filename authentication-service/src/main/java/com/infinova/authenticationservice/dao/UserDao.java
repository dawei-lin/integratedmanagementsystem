package com.infinova.authenticationservice.dao;

import com.infinova.authenticationservice.entity.User;
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
public interface UserDao {

	@Insert("insert into sys_user(id, username,workCode, password, realname, headImgUrl, phone, email,departmentId,departmentName,manager, status,myltiLogin, createTime, updateTime,validTime , description,isDeal) values(uuid(), #{username},#{workCode}, #{password}, #{realname}, #{headImgUrl}, #{phone}, #{email}, #{departmentId},#{departmentName},#{manager}, #{status},#{myltiLogin}, now(), now(),#{validTime},#{description},1)")
	int save(User user);

	@Select("select * from sys_user t where t.id = #{id}")
	User getById(String id);

	@Select("select * from sys_user t where t.username = #{username} limit 1")
	User getUser(String username);

	@Update("update sys_user t set t.password = #{password} where t.id = #{id}")
	int changePassword(@Param("id") String id, @Param("password") String password);

	/**
	 * 更新用户本次登录时间和IP
	 * @param id
	 * @return
	 */
	@Update("update sys_user t set t.lastLoginIp = #{ip} , t.lastLoginTime = now()  where t.id = #{id}")
	int changeIpAndLoginTime(@Param("id") String id, @Param("ip") String ip);


	/**
	 * 人员统计
	 * @param params
	 * @return
	 */
	Integer count(@Param("params") Map<String, Object> params);

	/**
	 * 用户分页
	 * @param params
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<User> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                    @Param("limit") Integer limit);

	/**
	 * 删除
	 * @param userId
	 * @return
	 */
	@Delete("delete from sys_role_user where userId = #{userId}")
	int deleteUserRole(String userId);

	/**
	 * 增加权限
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int saveUserRoles(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

	/**
	 * 修改基础信息
	 * @param user
	 * @return
	 */
	int update(User user);

	@Select("select roleId FROM sys_role_user WHERE userId = #{userId}")
	List<String> getUserRole(@Param("userId") String userId);

	@Select("select id,username,workCode, realname, headImgUrl, phone, email,departmentId,departmentName FROM  sys_user WHERE id in (select userId FROM sys_role_user WHERE roleId = (SELECT id from sys_role WHERE rolename = #{rolename}))")
	List<User> getRoleUser(@Param("rolename") String rolename);

}
