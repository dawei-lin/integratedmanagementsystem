package com.infinova.authenticationservice.service;


import com.infinova.authenticationservice.entity.User;
import com.infinova.authenticationservice.vo.UserVO;

public interface UserService {

	User saveUser(UserVO userVO);

	User updateUser(UserVO userVO);

	User getUser(String username);

	void changePassword(String username, String oldPassword, String newPassword);

	int changeIpAndLoginTime(String id, String ip);

}
