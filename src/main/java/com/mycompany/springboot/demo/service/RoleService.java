package com.mycompany.springboot.demo.service;


import java.util.List;

import com.mycompany.springboot.dto.RoleDto;

/**
 * A service interface for {@link RoleDto}.
 * @author Julia Tsukanova
 * @version 1.0
 */
public interface RoleService {
    void save(RoleDto roleDto);
    List<RoleDto> getAll();
    RoleDto getById(long id);
    void remove(RoleDto roleDto);
}
