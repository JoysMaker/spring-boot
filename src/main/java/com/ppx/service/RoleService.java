package com.ppx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppx.mapper.RoleMapper;
import com.ppx.pojo.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	public Role findByRoleId(String id) {
		
		return roleMapper.findRoleById(id);
	}

}
