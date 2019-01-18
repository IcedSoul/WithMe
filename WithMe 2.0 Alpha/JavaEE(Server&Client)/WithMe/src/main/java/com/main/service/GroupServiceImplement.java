package com.main.service;

import com.main.dao.UserGroupRelationDao;
import com.main.entity.UserGroupRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.GroupDao;
import com.main.entity.Group;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GroupServiceImplement implements GroupService {
	
	@Autowired private GroupDao groupDao;
	@Autowired private UserService userService;
	@Autowired
	UserGroupRelationDao userGroupRelationDao;

	@Override
	public Group getGroup(int id) {
		return groupDao.getGroup(id);
	}

	@Override
	public Group getGroup(String groupId) {
		return groupDao.getGroup(groupId);
	}

	@Override
	public Map<String,Object> addGroup(String groupName, String groupIntroduction, int groupCreaterId) {
		//群组的groupId在这里随机生成（五位数字）
		Group group = new Group();
		String groupId =String.valueOf((int) (Math.random()*100000));
		while(getGroup(groupId)!=null){
			groupId =String.valueOf((int) (Math.random()*100000));
		}
		group.setGroupId(groupId);
		group.setGroupCreaterId(groupCreaterId);
		group.setGroupIntroduction(groupIntroduction);
		group.setGroupName(groupName);
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		group.setGroupCreateTime(timestamp);
		group.setGroupUserCount(0);
		group.setGroupMembers("");
		groupDao.addGroup(group);
		UserGroupRelation userGroupRelation = new UserGroupRelation();
		//这里两个id不是一回事，一个是逻辑id，一个是业务id，要区分开
		Group groups = getGroup(groupId);
		userGroupRelation.setGroupId(groups.getId());
		userGroupRelation.setUserId(groupCreaterId);
		userGroupRelation.setEnterGroupTime(timestamp);
		userGroupRelation.setGroupUserNickName(userService.getUser(groupCreaterId).getUserNickName());
		userGroupRelation.setGroupLevel(10);
		userGroupRelationDao.addUserGroupRelation(userGroupRelation);
		groups.setGroupMembers(String.valueOf(groups.getId()+","+String.valueOf(groupCreaterId)));
		groups.setGroupUserCount(groups.getGroupUserCount()+1);
		updateGroup(groups);
		Map<String,Object> resoult = new HashMap<String,Object>();
		resoult.put("resoult", groupId);
		return resoult;

	}

	@Override
	public boolean deleteGroup(int id) {
		return groupDao.deleteGroup(id);
	}

	@Override
	public boolean updateGroup(Group group) {
		return groupDao.updateGroup(group);
	}

}
