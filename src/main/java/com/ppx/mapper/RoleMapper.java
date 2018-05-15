package com.ppx.mapper;

import org.apache.ibatis.annotations.Param;

import com.ppx.pojo.Role;

public interface RoleMapper {
	
	Role findRoleById(@Param("id")String id);

}
