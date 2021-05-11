package com.mycompany.springboot.demo.dao;

import java.util.List;

import com.mycompany.springboot.demo.entity.User;

/**
 * An interface that provides CRUD operations for {@link User} in the underlying storage mechanism.  
 * @author Julia Tsukanova
 * @version 1.0
 */
public interface UserDao {
    void createOrUpdate(User user);
    void remove(User user);
    List<User> findAll();
    User findByLogin(String login);
    User findByEmail(String email);
	User findByUserName(String userName);
	List<User> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String keyword);
}
