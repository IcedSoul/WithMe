package com.main.entity;

/*
 * ע�⣺���ʵ������ɵ���Hibernate����ݿ���ӳ�䣬Hibernate�����ַ�ʽ��ʵ��
 * �ֱ���xml�ļ����ú�ע�����ã�xml���ü�Ϊ����ʵ�����Ӧ��xxx.hbm.xml�ļ����ļ�������
 * ��һ�־���������õĲ���ע��ķ�ʽ��ʵ����֮�ڽ������ã�User��UserDetail���ǲ����������÷�ʽ
 * �����������ӣ�
 * http://www.blogjava.net/yxhxj2006/archive/2012/06/30/381861.html
 * http://www.tuicool.com/articles/yemyAv
 * http://blog.csdn.net/jiuqiyuliang/article/details/39380465
 * 
 * Hibernate������ǳ־ò㣬��ע�ⲻͬ��ǩ��Ӧ�Ĳ�ͬ������
 */

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;  
import javax.persistence.Id;  
import javax.persistence.Table;  
  
import org.hibernate.annotations.GenericGenerator;  
  
@Entity  
@Table(name="user_main")  
public class User {  
	private int userId;
	private String userName;
	private String userNickName;
	private int userIsOnline;
	private int userRole;
	
	@Id   //��ʾ����
	@GenericGenerator(name = "generator", strategy = "assigned")    //����
	@GeneratedValue(generator = "generator")
	
	@Column(name="user_id")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Column(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="user_nickname")
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	@Column(name="user_is_online")
	public int getUserIsOnline() {
		return userIsOnline;
	}
	public void setUserIsOnline(int userIsOnline) {
		this.userIsOnline = userIsOnline;
	}
	
	@Column(name="user_role")
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	//���췽��
	public User(){};
	public User(UserDetail userDetail){
		this.userId = userDetail.getUserDetailId();
		this.userName = userDetail.getUserDetailName();
		this.userNickName = userDetail.getUserDetailNickName();
		this.userIsOnline = 0;
		this.userRole = userDetail.getUserDetailRole();
	}
	

}  