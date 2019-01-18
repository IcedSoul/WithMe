package com.main.service;

import com.main.entity.Group;

import java.util.Map;

public interface GroupService {
	public Group getGroup(int id);
	public Group getGroup(String groupId);
	public Map<String,Object> addGroup(String groupName, String groupIntroduction, int groupCreaterId);
	public boolean deleteGroup(int id);
	public boolean updateGroup(Group group);
}
