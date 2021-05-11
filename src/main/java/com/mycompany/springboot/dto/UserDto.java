package com.mycompany.springboot.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;

import com.mycompany.springboot.demo.validation.FieldMatch;
import com.mycompany.springboot.demo.validation.ValidEmail;

/**
 * A class that transfers the required information.
 * @author Julia Tsukanova
 * @version 1.0
 */
@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserDto {
    private long id;
    @NotNull(message="is required")
    @Size(min = 2, max = 16, message = "should be in the range from 2 to 16 characters")
    private String userName;
    @NotNull(message = "is required")
    @ValidEmail
    @Email(message = "Please, enter a valid email")
    private String email;
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?=\\S+$).{6,18}", message = "Please, enter a password that does not contain special characters")
    private String password;
    @Transient
    private String matchingPassword;
    @NotNull(message = "is required")
    @Size(min = 2, max = 20, message = "Please,  enter the first name that is greater than 2 and less than 20 characters")
    private String firstName;
    @NotNull(message = "is required")
    @Size(min = 2, max = 20, message = "Please,  enter the first name that is greater than 2 and less than 20 characters")
    private String lastName;
    @NotNull(message = "is required")
    @Past(message = "Please, enter the birth date that is in the past")
    private Date birthDate;
    private List<String> roles;

    public UserDto() {
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

    public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
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
	public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

}
