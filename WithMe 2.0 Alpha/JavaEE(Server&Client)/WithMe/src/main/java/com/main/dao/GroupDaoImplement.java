package com.main.dao;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.main.entity.Group;
@Repository
public class GroupDaoImplement implements GroupDao {

	@Resource private SessionFactory sessionFactory;
	
	@Override
	public Group getGroup(int id) {
		String hql = "from Group where id=?";
	    Query query = sessionFactory.getCurrentSession().createQuery(hql);
	    query.setParameter(0, id);
		return (Group)query.uniqueResult();
	}

	@Override
	public Group getGroup(String groupId) {
		String hql = "from Group where groupId=?";  
	    Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, groupId);
		return (Group)query.uniqueResult();
	}

	@Override
	public void addGroup(Group group) {
		sessionFactory.getCurrentSession().save(group);
	}

	@Override
	public boolean deleteGroup(int id) {
		String hql = "delete Group where id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, id);
	    
	    return query.executeUpdate() > 0;
	}

	@Override
	public boolean updateGroup(Group group) {
		String hql = "update Group set groupName=?,groupCreaterId=?,groupIntroduction=?,groupUserCount=?,groupMembers=? where id=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);  
	    query.setParameter(0, group.getGroupName());
	    query.setParameter(1, group.getGroupCreaterId());
	    query.setParameter(2, group.getGroupIntroduction());
	    query.setParameter(3, group.getGroupUserCount());
	    query.setParameter(4, group.getGroupMembers());
	    query.setParameter(5, group.getId());
	    
	    return query.executeUpdate() > 0;
	}
}
