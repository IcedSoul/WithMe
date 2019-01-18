package com.main.service;

import java.util.List;
import java.util.Map;

import com.main.entity.UserGroupRelation;

public interface UserGroupRelationService {
		//根据用户id和群id确定一条唯一的群记录
		public UserGroupRelation getUserGroupRelation(int userId, int groupId);
		//添加一条记录
		public Map<String,Object> addUserGroupRelation(int id, int userId);
		//删除一条记录
		public boolean deleteUserGroupRelation(int userId, int groupId);
		//更新一条记录
		public boolean updateUserGroupRelation(UserGroupRelation userGroupRelation);
		//获取一个群的所有用户的id
		public List<UserGroupRelation> getAllUser(int groupId);
		//获取一个用户的所有群的记录
		public List<UserGroupRelation> getAllGroup(int userId);
}
