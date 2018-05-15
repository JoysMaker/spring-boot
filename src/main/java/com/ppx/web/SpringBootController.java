package com.ppx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppx.pojo.Role;
import com.ppx.service.RoleService;
import com.ppx.utils.DataResult;
import com.ppx.utils.RedisTemplateUtils;

@RestController
@RequestMapping("boot")
public class SpringBootController {
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private RedisTemplateUtils redisTemplateUtils;
	@GetMapping("helloWorld/{name}")
	public ResponseEntity<DataResult<String>>helloWorld(String name){
		
		try {
			DataResult<String> ds = new DataResult<String>();
			ds.setData("hello,"+name);
			return ResponseEntity.ok(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@GetMapping("findRole/{id}")
	public ResponseEntity<Role> findRole(@PathVariable("id")String id){
		
		try {
			Role role = roleService.findByRoleId(id);
			
			return ResponseEntity.ok(role);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@GetMapping("redis/test")
	public ResponseEntity<DataResult<Role>> testRedisTemplate(){
		
		DataResult<Role> ds = new DataResult<Role>();
		Role role = new Role();
		role.setId(2);
		role.setName("小红");
		role.setDescription("我是小红的描述");
		redisTemplateUtils.setCacheKeyVal("SAAS_PPX", "role"+"_"+role.getId(), role);
		Role role2 = redisTemplateUtils.get("SAAS_PPX", "role"+"_"+role.getId(), role.getClass());
		ds.setData(role2);
		return ResponseEntity.ok(ds);
	}
}
