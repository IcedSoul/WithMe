package com.main.service;

import java.sql.Timestamp;
import java.util.*;

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
	public Map<String,Object> addUserRelation(int userIdA, int userIdB) {
		UserRelation userRelation = new UserRelation();
		userRelation.setUserIdA(userIdA);
		userRelation.setUserIdB(userIdB);
		userRelation.setRelationStatus(1);
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		userRelation.setRelationStart(timestamp);
		userRelationDao.addUserRelation(userRelation);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("resoult", "success");
		return result;

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
