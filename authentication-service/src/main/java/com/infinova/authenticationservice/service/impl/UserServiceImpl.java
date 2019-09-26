package com.infinova.authenticationservice.service.impl;


import com.infinova.authenticationservice.dao.UserDao;
import com.infinova.authenticationservice.entity.User;
import com.infinova.authenticationservice.service.UserService;
import com.infinova.authenticationservice.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User saveUser(UserVO userVO) {
		User user = userVO;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatus(User.Status.VALID);
		userDao.save(user);
		user = userDao.getUser(user.getUsername());
		saveUserRoles(user.getId(), userVO.getRoleIds());

		log.debug("新增用户{}", user.getUsername());
		return user;
	}

	private void saveUserRoles(String userId, List<String> roleIds) {
		if (roleIds != null) {
			userDao.deleteUserRole(userId);
			if (!CollectionUtils.isEmpty(roleIds)) {
				userDao.saveUserRoles(userId, roleIds);
			}
		}
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		User u = userDao.getUser(username);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}

		if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
			throw new IllegalArgumentException("旧密码错误");
		}

		userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));

		log.debug("修改{}的密码", username);
	}

	@Override
	@Transactional
	public User updateUser(UserVO userVO) {
		userDao.update(userVO);
		saveUserRoles(userVO.getId(), userVO.getRoleIds());

		return userVO;
	}


	@Override
	public int changeIpAndLoginTime(String id, String ip) {
		return userDao.changeIpAndLoginTime(id,ip);
	}
}
