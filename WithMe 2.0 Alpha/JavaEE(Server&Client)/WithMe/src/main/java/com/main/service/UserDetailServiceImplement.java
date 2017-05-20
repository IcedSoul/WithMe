package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.UserDetailDao;
import com.main.entity.UserDetail;
@Service
public class UserDetailServiceImplement implements UserDetailService {

	@Autowired private UserDetailDao userDetailDao;
	
	@Override
	public UserDetail getUserDetail(int id) {
		return userDetailDao.getUserDetail(id);
	}
	
	@Override
	public UserDetail getUserDetail(String name) {
		return userDetailDao.getUserDetail(name);
	}

	@Override
	public void addUserDetail(UserDetail userDetail) {
		userDetailDao.addUserDetail(userDetail);
	}

	@Override
	public boolean deleteUserDetail(int id) {
		return userDetailDao.deleteUserDetail(id);
	}

	@Override
	public boolean updateUserDetail(UserDetail userDetail) {
		return userDetailDao.updateUserDetail(userDetail);
	}

	@Override
	public List<UserDetail> getAllUserDetail() {
		return userDetailDao.getAllUserDetail();
	}

}
