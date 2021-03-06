package com.ppx.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="di_b_role")
public class Role {
	
	@Id
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String description;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		
		this.description = description;
	}
}
