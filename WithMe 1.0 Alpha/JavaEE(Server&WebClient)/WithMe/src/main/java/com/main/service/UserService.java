package com.main.service;

/*
 * Service�㣬��Ȼ����ֱ����Control��ֱ�ӵ���dao�㷽������ݿ���в������������������̫ǿ�����Ҳ����ں�����չ
 * �������service�����dao�İ���ķ����������ƺ����ظ���
 * ��Ȼ����ʱ����Ҳû���鵽����ֿ��ľ���ô�����������չʱӦ�ÿ���֪���������ı�Ҫ��.
 * 
 */
import java.util.List;

import com.main.entity.User;

public interface UserService {
		//���name��ȡ�û�
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
