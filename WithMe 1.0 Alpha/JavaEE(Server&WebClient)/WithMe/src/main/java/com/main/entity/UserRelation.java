package com.main.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/*
 * http://blog.csdn.net/robinpipi/article/details/7655388
 */

@Entity
@Table(name="user_relation")
@IdClass(value=UserRelationPriKey.class)
public class UserRelation{
	private int userIdA;
	private int userIdB;
	private int relationStatus;
	private Timestamp relationStart;
	@Id
	@Column(name="user_id_a")
	public int getUserIdA() {
		return userIdA;
	}
	public void setUserIdA(int userIdA) {
		this.userIdA = userIdA;
	}
	@Id
	@Column(name="user_id_b")
	public int getUserIdB() {
		return userIdB;
	}
	public void setUserIdB(int userIdB) {
		this.userIdB = userIdB;
	}
	@Column(name="relation_status")
	public int getRelationStatus() {
		return relationStatus;
	}
	public void setRelationStatus(int relationStatus) {
		this.relationStatus = relationStatus;
	}
	@Column(name="relation_start")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getRelationStart() {
		return relationStart;
	}
	public void setRelationStart(Timestamp relationStart) {
		this.relationStart = relationStart;
	}
	
}
