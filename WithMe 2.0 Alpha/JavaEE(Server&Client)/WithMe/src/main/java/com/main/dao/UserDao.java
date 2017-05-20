package com.main.dao;
import java.util.List;

/*
 * �ӿڣ�����dao
 * ��Ҫ����ʵ��
 */

import com.main.entity.*;
public interface UserDao {
	//���id��ȡ�û�
	public User getUser(int id);
	//���name��ȡ�û�
	public User getUser(String name);
	//����û�
	public void addUser(User user);
	//ɾ���û�
	public boolean deleteUser(int id);
	//�����û�
	public boolean updateUser(User user);
	//��ȡ�����û�
	public List<User> getAllUser(); 
}
