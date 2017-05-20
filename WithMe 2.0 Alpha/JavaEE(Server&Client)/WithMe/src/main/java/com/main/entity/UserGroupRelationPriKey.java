package com.main.entity;

import java.io.Serializable;

public class UserGroupRelationPriKey implements Serializable{

	/**
	 * UserGroupRelation的联合主键
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private int groupId;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupRelationPriKey other = (UserGroupRelationPriKey) obj;
		if (groupId != other.groupId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	

}
