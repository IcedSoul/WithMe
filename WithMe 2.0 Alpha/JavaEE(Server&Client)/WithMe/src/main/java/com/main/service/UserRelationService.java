package com.main.service;

import java.util.List;
import java.util.Map;

import com.main.entity.UserRelation;

public interface UserRelationService {

		public UserRelation getUserRelation(int idA, int idB);

		public Map<String,Object> addUserRelation(int userIdA, int userIdB);

		public boolean deleteUserRelation(int idA, int idB);

		public boolean updateUser(UserRelation userRelation);

		public List getAllFriends(int id);
}
