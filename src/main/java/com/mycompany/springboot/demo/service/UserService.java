package com.mycompany.springboot.demo.service;

import java.util.List;

import com.mycompany.springboot.demo.dto.UserDto;

/**
 * A service interface for {@link UserDto}.
 * @author Julia Tsukanova
 * @version 1.0
 */
public interface UserService {
    void save(UserDto user);
    void update(UserDto user);
    List<UserDto> getAll();
    UserDto getByLogin(String login);
    UserDto getByEmail(String email);
    UserDto getById(long id);
    void remove(long id);
	List<UserDto> search(String keyword);
}
