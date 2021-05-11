package com.mycompany.springboot.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A simple JavaBean domain class that represents a User.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Entity
@Table(name = "users_tb")
public class User implements Serializable {

    /**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = 832845201291284947L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "username")
    @NotNull(message = "UserName cannot be empty")
    private String userName;

    @Column(name = "email")
    @NotNull(message = "Email cannot be empty")
    @Email(message = "Please, enter a valid email")
    private String email;

    @Column(name = "password", nullable = false)
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\\\S+$).{6,18}", message = "Please, enter a strong password")
    private String password;

    @Column(name = "first_name")
    @Size(min = 2, max = 20, message = "Please,  enter the first name that is greater than 2 and less than 20 characters")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 45, message = "Please, enter the last name that is greater than 2 and less than 45 characters")
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    @Past(message = "Please, enter the birth date that is in the past")
    private Date birthDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
