package com.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.UserDao;
import com.main.dao.UserRelationDao;
import com.main.entity.User;
import com.main.entity.UserRelation;
@Service
public class UserRelationServiceImplement implements UserRelationService {

	@Autowired private UserRelationDao userRelationDao;
	@Autowired private UserDao userDao;
	
	@Override
	public UserRelation getUserRelation(int idA, int idB) {
		return userRelationDao.getUserRelation(idA, idB);
	}

	@Override
	public void addUserRelation(UserRelation userRelation) {
		userRelationDao.addUserRelation(userRelation);
	}

	@Override
	public boolean deleteUserRelation(int idA, int idB) {
		return userRelationDao.deleteUserRelation(idA, idB);
	}

	@Override
	public boolean updateUser(UserRelation userRelation) {
		return userRelationDao.updateUser(userRelation);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllFriends(int id) {
		List list = userRelationDao.getAllFriends(id);
		ArrayList<User> lists = new ArrayList<User>();
		for(int i=0;i<list.size();i++){
			User user = userDao.getUser((Integer) list.get(i));
			lists.add(user);
		}
		return lists;
	}

}
