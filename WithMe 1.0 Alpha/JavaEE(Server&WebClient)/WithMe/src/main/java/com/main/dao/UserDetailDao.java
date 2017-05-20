package com.main.dao;

import java.util.List;

import com.main.entity.UserDetail;

public interface UserDetailDao {
	//���id��ȡ�û�
	public UserDetail getUserDetail(int id);
	//���name��ȡ�û�
	public UserDetail getUserDetail(String name);
	//����û�
	public void addUserDetail(UserDetail userDetail);
	//ɾ���û�
	public boolean deleteUserDetail(int id);
	//�����û�
	public boolean updateUserDetail(UserDetail userDetail);
	//��ȡ�����û�
	public List<UserDetail> getAllUserDetail();
}
