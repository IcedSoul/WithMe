package com.main.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.main.entity.UserDetail;
@Repository
public class UserDetailDaoImplement  implements UserDetailDao{

	@Resource private SessionFactory sessionFactory;
	
	@Override
	public UserDetail getUserDetail(int id) {
		String hql = "from UserDetail where userDetailId=?";  
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	     query.setParameter(0, id);
		return (UserDetail)query.uniqueResult();
	}
	
	@Override
	public UserDetail getUserDetail(String name) {
		String hql = "from UserDetail where userDetailName=?";  
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	     query.setParameter(0, name);
		return (UserDetail)query.uniqueResult();
	}

	@Override
	public void addUserDetail(UserDetail userDetail) {
		sessionFactory.getCurrentSession().save(userDetail);
	}

	@Override
	public boolean deleteUserDetail(int id) {
		String hql = "delete UserDetail where userDetailId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, id);
		return query.executeUpdate()>0;
	}

	@Override
	public boolean updateUserDetail(UserDetail userDetail) {
		String hql = "update UserDetail set userDetailName = ?,userDetailNickName = ?,userDetailPassword=?,userMailNumber=?,userPhoneNumber=? where userDetailId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, userDetail.getUserDetailName());
	    query.setParameter(1, userDetail.getUserDetailNickName());
	    query.setParameter(2, userDetail.getUserDetailPassword());
	    query.setParameter(3, userDetail.getUserMailNumber());
	    query.setParameter(4, userDetail.getUserPhoneNumber());
	    query.setParameter(5, userDetail.getUserDetailId());
		return query.executeUpdate()>0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetail> getAllUserDetail() {
		String hql = "from UserDetail";  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        return query.list();
	}
}
