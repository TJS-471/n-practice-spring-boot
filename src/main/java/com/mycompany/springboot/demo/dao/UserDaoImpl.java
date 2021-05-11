package com.mycompany.springboot.demo.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.springboot.demo.entity.User;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * A class that implements the interface {@link com.mycompany.springboot.demo.dao.UserDao}.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
	private EntityManager entityManager;

    
    @Override
    public void createOrUpdate(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(user);
    }

    @Override
    public void remove(User user) {
    	Session session = entityManager.unwrap(Session.class);
        session.delete(user);
    }

    @Override
    public List<User> findAll() {
    	Session session = entityManager.unwrap(Session.class);
        List<User> users = session.createQuery("from User ", User.class).getResultList();
        return users;
    }
    
    @Override
    public User findByLogin(String userName) {
        User user = null;
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from User where userName = :userName");
        query.setParameter("userName", userName);
        List userByLogin = query.list();
        if (userByLogin != null && userByLogin.size() > 0) {
            user = (User) userByLogin.get(0);
        }
        return user;
    }
    @Override
    public User findByEmail(String email) {
        User user = null;
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from User where email = :email");
        query.setParameter("email", email);
        List userByEmail = query.list();
        if (userByEmail != null && userByEmail.size() > 0) {
            user = (User) userByEmail.get(0);
        }
        return user;
    }
    
    public User getById(long id) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, id);
        return user;
    }

	@Override
	public User findByUserName(String userName) {
	    User user = null;
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from User where userName = :userName", User.class);
        query.setParameter("userName", userName);
        List userByUserName = query.getResultList();
        if (userByUserName != null && userByUserName.size() > 0) {
            user = (User) userByUserName.get(0);
        }
        return user;
	}
    @Override
	public List<User> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String keyword) {
    	 Session session = entityManager.unwrap(Session.class);
    	 Query query = session.createQuery("from User where lower(first_name) like lower(:keyword) OR lower(last_name) like lower(:keyword)", User.class);
    	 query.setParameter("keyword", "%" + keyword + "%");
		return query.getResultList();
	}
}
