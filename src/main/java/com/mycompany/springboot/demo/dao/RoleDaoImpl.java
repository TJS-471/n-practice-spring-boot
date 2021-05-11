package com.mycompany.springboot.demo.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.springboot.demo.entity.Role;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * A class that implements the interface {@link com.mycompany.springboot.demo.dao.RoleDao}.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
	private EntityManager entityManager;

    @Override
    public void create(Role role) {
    	Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(role);
    }

    @Override
    public void update(Role role) {
    	Session session = entityManager.unwrap(Session.class);
        session.update(role);
    }

    @Override
    public void remove(Role role) {
        long id = role.getId();
        Session session = entityManager.unwrap(Session.class);
        session.delete(role);
    }

    @Override
    public Role findByName(String name) {
        Role role = null;
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Role where name=:name");
        query.setParameter("name", name);
        List list = query.list();
        if (list != null && list.size() > 0) {
            role = (Role) list.get(0);
        }
        return role;
    }

    public List<Role> getAll() {
        List<Role> roles = null;
        Session session = entityManager.unwrap(Session.class);
        roles = session.createQuery("from Role", Role.class).getResultList();
        return roles;
    }
    public Role getById(long id) {
        Session session = entityManager.unwrap(Session.class);
        Role role = session.get(Role.class, id);
        return role;
    }
}
