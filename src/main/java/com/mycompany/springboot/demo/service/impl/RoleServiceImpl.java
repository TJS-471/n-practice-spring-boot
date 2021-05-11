package com.mycompany.springboot.demo.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.springboot.demo.dao.RoleDaoImpl;
import com.mycompany.springboot.demo.entity.Role;
import com.mycompany.springboot.demo.service.RoleService;
import com.mycompany.springboot.dto.RoleDto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * A service class that implements the interface {@link com.mycompany.springboot.demo.service.RoleService}.
 * @author Julia Tsukanova
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDaoImpl dao;
    private ModelMapper mapper = new ModelMapper();
    private static final String INITIAL_ROLE = "Admin";

    @Override
    @Transactional
    public void save(RoleDto roleDto) {
        String name = roleDto.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Name can not be empty");
        }
        Role daoByName = dao.findByName(name);
        if (daoByName != null) {
            throw new RuntimeException("Record already exists");
        }
        
        Role role = mapper.map(roleDto, Role.class);
        dao.create(role);
    }

    @Override
    @Transactional
    public List<RoleDto> getAll() {
        List<Role> all = dao.getAll();
        List<RoleDto> returnValue = new ArrayList<>();
        for(Role role : all) {
        	 returnValue.add(mapper.map(role, RoleDto.class));
        }
        return returnValue;
    }
    
    @Override
    @Transactional
    public RoleDto getById(long id) {
    	Role role = dao.getById(id);
    	RoleDto roleDto = mapper.map(role, RoleDto.class);
    	return roleDto;
    }

    @Override
    @Transactional
    public void remove(RoleDto roleDto) {
        String name = roleDto.getName().trim();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Name can not be empty");
        }
        Role roleByName = dao.findByName(name);
        if (roleByName == null) {
            throw new RuntimeException("Can not remove a role. The record does not exist");
        } else if (roleByName.getName().equalsIgnoreCase(INITIAL_ROLE)) {
            throw new RuntimeException("Can not remove the admin role.");
        }
        dao.remove(roleByName);
    }
}
