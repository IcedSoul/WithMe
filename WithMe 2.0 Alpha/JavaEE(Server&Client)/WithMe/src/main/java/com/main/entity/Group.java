package com.main.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;

@Entity  
@Table(name="group_main") 
public class Group {
	private int id;
	private String groupId;
	private String groupName;
	private int groupCreaterId;
	private Timestamp groupCreateTime;
	private String groupIntroduction;
	private int groupUserCount;
	private String groupMembers;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="group_id")
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Column(name="group_name")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	@Column(name="group_creater_id")
	public int getGroupCreaterId() {
		return groupCreaterId;
	}
	public void setGroupCreaterId(int groupCreaterId) {
		this.groupCreaterId = groupCreaterId;
	}
	
	@Column(name="group_create_time")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getGroupCreateTime() {
		return groupCreateTime;
	}
	public void setGroupCreateTime(Timestamp groupCreateTime) {
		this.groupCreateTime = groupCreateTime;
	}
	
	@Column(name="group_introduction")
	public String getGroupIntroduction() {
		return groupIntroduction;
	}
	public void setGroupIntroduction(String groupIntroduction) {
		this.groupIntroduction = groupIntroduction;
	}
	
	@Column(name="group_user_count")
	public int getGroupUserCount() {
		return groupUserCount;
	}
	public void setGroupUserCount(int groupUserCount) {
		this.groupUserCount = groupUserCount;
	}
	
	@Column(name="group_members")
	public String getGroupMembers() {
		return groupMembers;
	}
	public void setGroupMembers(String groupMembers) {
		this.groupMembers = groupMembers;
	}
}
