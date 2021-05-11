package com.mycompany.springboot.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
/**
 * A simple JavaBean class that represents a role of a {@link User}.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Entity
@Table(name = "roles_tb")
public class Role implements Serializable {
    /**
	 * a generated serial version ID
	 */
	private static final long serialVersionUID = 3680755283834977754L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "name")
    @NotNull(message = "cannot be null")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
