package com.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dao.GroupDao;
import com.main.entity.Group;
@Service
public class GroupServiceImplement implements GroupService {
	
	@Autowired private GroupDao groupDao;

	@Override
	public Group getGroup(int id) {
		return groupDao.getGroup(id);
	}

	@Override
	public Group getGroup(String groupId) {
		return groupDao.getGroup(groupId);
	}

	@Override
	public void addGroup(Group group) {
		groupDao.addGroup(group);
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
