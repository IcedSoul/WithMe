package com.main.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity  
@Table(name="user_group_relation")
@IdClass(value=UserGroupRelationPriKey.class)
public class UserGroupRelation {
	private int userId;
	private int groupId;
	private int groupLevel;
	private String groupUserNickName;
	private Timestamp enterGroupTime;
	
	@Id
	@Column(name="user_id")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Id
	@Column(name="group_id")
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name="group_level")
	public int getGroupLevel() {
		return groupLevel;
	}
	public void setGroupLevel(int groupLevel) {
		this.groupLevel = groupLevel;
	}
	
	@Column(name="group_user_nickname")
	public String getGroupUserNickName() {
		return groupUserNickName;
	}
	public void setGroupUserNickName(String groupUserNickName) {
		this.groupUserNickName = groupUserNickName;
	}
	
	@Column(name="enter_group_time")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getEnterGroupTime() {
		return enterGroupTime;
	}
	public void setEnterGroupTime(Timestamp enterGroupTime) {
		this.enterGroupTime = enterGroupTime;
	}
}
