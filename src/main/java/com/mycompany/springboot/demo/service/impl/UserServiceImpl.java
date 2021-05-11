package com.mycompany.springboot.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.springboot.demo.dao.RoleDaoImpl;
import com.mycompany.springboot.demo.dao.UserDaoImpl;
import com.mycompany.springboot.demo.entity.Role;
import com.mycompany.springboot.demo.entity.User;
import com.mycompany.springboot.demo.service.UserService;
import com.mycompany.springboot.demo.dto.RoleDto;
import com.mycompany.springboot.demo.dto.UserDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class that implements the interface
 * {@link com.mycompany.springboot.demo.service.UserService}.
 * 
 * @author Julia Tsukanova
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl dao;
	@Autowired
	private RoleDaoImpl daoRole;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private ModelMapper mapper = new ModelMapper();
	private static final long INITIAL_USER_ID = 1L;
	private static final String DEFAULT_ROLE = "ROLE_USER";

	@Override
	@Transactional
	public void save(UserDto userDto) {
		String login = userDto.getUserName().trim();
		User user = null;

		user = dao.findByLogin(login);
		if (user != null) {
			throw new RuntimeException(
					"Can not create auser. The user with the username " + login + " already exists.");
		}

		user = new User();
		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(encodedPassword);
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setBirthDate(userDto.getBirthDate());
		user.setRoles(Arrays.asList(daoRole.findByName(DEFAULT_ROLE)));

		dao.createOrUpdate(user);
	}

	@Override
	@Transactional
	public void update(UserDto userDto) {
		String login = userDto.getUserName().trim();
		User user = null;

		user = dao.findByLogin(login);
		if (user != null) {
			user.setEmail(userDto.getEmail());
			if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
				String encodedPassword = passwordEncoder.encode(userDto.getPassword());
				user.setPassword(encodedPassword);
			}
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setBirthDate(userDto.getBirthDate());
			if (userDto.getRoles() != null) {
				List<Role> roles = new ArrayList<>();
				for (String roleName : userDto.getRoles()) {
					Role roleByName = daoRole.findByName(roleName);
					if (roleByName != null) {
						roles.add(roleByName);
					}
					user.setRoles(roles);
				}
			}
		}
		dao.createOrUpdate(user);
	}

	@Override
	@Transactional
	public List<UserDto> getAll() {
		List<User> all = dao.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (User user : all) {
			userDtoList.add(mapper.map(user, UserDto.class));
		}
		for (int i = 0; i < all.size(); i++) {
			List<RoleDto> roles = new ArrayList<>();
			if (all.get(i).getRoles() != null) {
				roles.add(mapper.map(all.get(i).getRoles().get(0), RoleDto.class));
			}
			if (roles != null) {
				userDtoList.get(i).setRoles(roles.stream().map(RoleDto::getName).collect(Collectors.toList()));
			}
			Date birthDate = new Date(all.get(i).getBirthDate().getTime());
			userDtoList.get(i).setBirthDate(birthDate);
		}
		return userDtoList;
	}

	@Override
	@Transactional
	public UserDto getByLogin(String login) {
		UserDto userDto = null;
		if (login == null || login.isEmpty()) {
			throw new RuntimeException("Login can not be empty");
		}
		User daoByLogin = dao.findByLogin(login);
		if (daoByLogin != null) {
			List<RoleDto> roles = new ArrayList<>();
			roles.add(mapper.map(daoByLogin.getRoles(), RoleDto.class));
			userDto = mapper.map(daoByLogin, UserDto.class);
			if (roles != null) {
				userDto.setRoles(roles.stream().map(RoleDto::getName).collect(Collectors.toList()));
			}
		}
		return userDto;
	}

	@Override
	@Transactional
	public UserDto getByEmail(String email) {
		UserDto userDto = null;
		if (email == null || email.isEmpty()) {
			throw new RuntimeException("Email can not be empty");
		}
		User daoByEmail = dao.findByEmail(email);
		if (daoByEmail != null) {
			List<RoleDto> roles = new ArrayList<>();
			roles.add(mapper.map(daoByEmail.getRoles(), RoleDto.class));
			userDto = mapper.map(daoByEmail, UserDto.class);
			if (roles != null) {
				userDto.setRoles(roles.stream().map(RoleDto::getName).collect(Collectors.toList()));
			}
		}
		return userDto;
	}

	@Override
	@Transactional
	public UserDto getById(long id) {
		User user = dao.getById(id);
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	@Transactional
	public void remove(long id) {
		User user = dao.getById(id);
		if (user == null) {
			throw new RuntimeException("Can not remove the user. The record does not exist");
		} else if (user.getId() == INITIAL_USER_ID) {
			throw new RuntimeException("Can not remove the initial user.");
		}
		dao.remove(user);
	}

	@Override
	public List<UserDto> search(String keyword) {
		List<User> result = null;
		List<UserDto> userDtoList = new ArrayList<>();
		if (keyword != null && (keyword.trim().length() > 0)) {
			result = dao.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(keyword);
		} else {
			result = dao.findAll();
		}
		if (result != null) {
			for (User user : result) {
				userDtoList.add(mapper.map(user, UserDto.class));
			}
			for (int i = 0; i < result.size(); i++) {
				List<RoleDto> roles = new ArrayList<>();
				if (result.get(i).getRoles() != null) {
					roles.add(mapper.map(result.get(i).getRoles().get(0), RoleDto.class));
				}
				if (roles != null) {
					userDtoList.get(i).setRoles(roles.stream().map(RoleDto::getName).collect(Collectors.toList()));
				}
				Date birthDate = new Date(result.get(i).getBirthDate().getTime());
				userDtoList.get(i).setBirthDate(birthDate);
			}
		}
		return userDtoList;
	}
}
