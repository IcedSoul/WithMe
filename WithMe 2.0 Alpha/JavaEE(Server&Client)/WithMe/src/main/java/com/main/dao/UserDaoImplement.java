package com.main.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;  
import org.hibernate.SessionFactory;  
import org.springframework.stereotype.Repository;

import com.main.entity.User;
@Repository
public class UserDaoImplement implements UserDao {

	@Resource private SessionFactory sessionFactory;
	@Override
	public User getUser(int id) {
		 String hql = "from User where userId=?";
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	     query.setParameter(0, id);
		return (User)query.uniqueResult();
	}
	@Override
	public User getUser(String name) {
		 String hql = "from User where userName=?";  
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	     query.setParameter(0, name);
		return (User)query.uniqueResult();
	}

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public boolean deleteUser(int id) {
		String hql = "delete User where userId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, id);
	    
	    return query.executeUpdate() > 0;
	}

	@Override
	public boolean updateUser(User user) {
		String hql = "update User set userName = ?,userNickName=?,userIsOnline=? where userId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, user.getUserName());
	    query.setParameter(1, user.getUserNickName());
	    query.setParameter(2, user.getUserIsOnline());
	    query.setParameter(3, user.getUserId());
	    
	    return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUser() {
		String hql = "from User";  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
          
        return query.list();
	}

}
