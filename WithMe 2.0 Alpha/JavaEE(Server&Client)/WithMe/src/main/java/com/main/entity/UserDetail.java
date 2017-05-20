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
@Table(name="user_detail")  
public class UserDetail{
	private int userDetailId;
	private String userDetailName;
	private String userDetailNickName;
	private String userDetailPassword;
	private int userDetailRole;
	private String userMailNumber;
	private String userPhoneNumber;
	private Timestamp userRegisterTime;
	
	@Id   //��ʾ����
	@GenericGenerator(name = "generator", strategy = "increment")    //����
	@GeneratedValue(generator = "generator")
	
	@Column(name="user_detail_id")
	public int getUserDetailId() {
		return userDetailId;
	}
	public void setUserDetailId(int userDetailId) {
		this.userDetailId = userDetailId;
	}
	
	@Column(name="user_detail_name")
	public String getUserDetailName() {
		return userDetailName;
	}
	public void setUserDetailName(String userDetailName) {
		this.userDetailName = userDetailName;
	}
	
	@Column(name="user_detail_nickname")
	public String getUserDetailNickName() {
		return userDetailNickName;
	}
	public void setUserDetailNickName(String userDetailNickName) {
		this.userDetailNickName = userDetailNickName;
	}
	@Column(name="user_detail_password")
	public String getUserDetailPassword() {
		return userDetailPassword;
	}
	public void setUserDetailPassword(String userDetailPassword) {
		this.userDetailPassword = userDetailPassword;
	}
	
	@Column(name="user_detail_role")
	public int getUserDetailRole() {
		return userDetailRole;
	}
	public void setUserDetailRole(int userDetailRole) {
		this.userDetailRole = userDetailRole;
	}
	
	@Column(name="user_mail_number")
	public String getUserMailNumber() {
		return userMailNumber;
	}
	public void setUserMailNumber(String userMailNumber) {
		this.userMailNumber = userMailNumber;
	}
	@Column(name="user_phone_number")
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	@Column(name="user_register_time")
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getUserRegisterTime() {
		return userRegisterTime;
	}
	public void setUserRegisterTime(Timestamp userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}
	
	
}
