package com.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.UserDao;
import com.main.entity.User;
@Service
public class UserServiceImplement implements UserService {
	
	@Autowired private UserDao userDao;
	
	@Override
	public User getUser(int id) {
		return userDao.getUser(id);
	}
	
	@Override
	public User getUser(String name) {
		return userDao.getUser(name);
	}
	

	@Override
	public void addUser(User user) {
		userDao.addUser(user);
	}

	@Override
	public boolean deleteUser(int id) {
		return userDao.deleteUser(id);

	}

	@Override
	public boolean updateUser(User user) {
		return userDao.updateUser(user);

	}

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

}
