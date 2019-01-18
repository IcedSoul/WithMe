package com.main.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.main.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.UserGroupRelationDao;
import com.main.entity.UserGroupRelation;
@Service
public class UserGroupRelationServiceImplement implements
		UserGroupRelationService {

	@Autowired private UserGroupRelationDao userGroupRelationDao;
	@Autowired private GroupService groupService;
	@Autowired private UserService userService;

	@Override
	public UserGroupRelation getUserGroupRelation(int userId, int groupId) {
		return userGroupRelationDao.getUserGroupRelation(userId, groupId);
	}

	@Override
	public Map<String,Object> addUserGroupRelation(int id, int userId) {
		UserGroupRelation userGroupRelation = new UserGroupRelation();
		userGroupRelation.setGroupId(id);
		userGroupRelation.setGroupLevel(0);
		userGroupRelation.setUserId(userId);
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		userGroupRelation.setEnterGroupTime(timestamp);
		userGroupRelation.setGroupUserNickName(userService.getUser(userId).getUserNickName());
		userGroupRelationDao.addUserGroupRelation(userGroupRelation);
		Group group = groupService.getGroup(id);
		group.setGroupMembers(group.getGroupMembers()+","+String.valueOf(userId));
		group.setGroupUserCount(group.getGroupUserCount()+1);
		groupService.updateGroup(group);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("resoult", "success");
		return result;

	}

	@Override
	public boolean deleteUserGroupRelation(int userId, int groupId) {
		return userGroupRelationDao.deleteUserGroupRelation(userId, groupId);
	}

	@Override
	public boolean updateUserGroupRelation(UserGroupRelation userGroupRelation) {
		return userGroupRelationDao.updateUserGroupRelation(userGroupRelation);
	}

	@Override
	public List<UserGroupRelation> getAllUser(int groupId) {
		return userGroupRelationDao.getAllUser(groupId);
	}

	@Override
	public List<UserGroupRelation> getAllGroup(int userId) {
		return userGroupRelationDao.getAllGroup(userId);
	}

}
