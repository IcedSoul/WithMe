package com.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.main.entity.UserRelation;
@Repository
public class UserRelationDaoImplemet implements UserRelationDao {
	@Resource private SessionFactory sessionFactory;
	
	@Override
	public UserRelation getUserRelation(int idA, int idB) {
		 String hql = "from UserRelation where userIdA=? and userIdB=?";  
	     Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	     query.setParameter(0, idA);
	     query.setParameter(1, idB);
	     return (UserRelation)query.uniqueResult();
	}

	@Override
	public void addUserRelation(UserRelation userRelation) {
		sessionFactory.getCurrentSession().save(userRelation);
	}

	@Override
	public boolean deleteUserRelation(int idA, int idB) {
		String hql = "delete UserRelation where userIdA=? and userIdB=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, idA);
	    query.setParameter(1, idB);
	    return query.executeUpdate() > 0;
	}

	@Override
	public boolean updateUser(UserRelation userRelation) {
		String hql = "update UserRelation set relationStatus = ?,relationStart=? where userIdA=? and userIdB=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, userRelation.getRelationStatus());
	    query.setParameter(1, userRelation.getRelationStart());
	    query.setParameter(2, userRelation.getUserIdA());
	    query.setParameter(3, userRelation.getUserIdB());
	    
	    return query.executeUpdate() > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getAllFriends(int id) {
		String hql = "from UserRelation where userIdA=? or userIdB=?";  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        query.setParameter(0, id);
        query.setParameter(1, id);
        List<?> list=query.list();
        ArrayList<Integer> allFriends = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			UserRelation userRelation = (UserRelation)list.get(i);
			int idA=userRelation.getUserIdA();
			int idB=userRelation.getUserIdB();
			allFriends.add(idA==id ? idB:idA); 
		}
        return allFriends;
	}

}
