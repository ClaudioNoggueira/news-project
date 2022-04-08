package com.claudionogueira.news.dto;

import java.io.Serializable;

public class CategoryNoNewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;

	public CategoryNoNewsDTO() {

	}

	public CategoryNoNewsDTO(CategoryDTO categoryDTO) {
		id = categoryDTO.getId().toString();
		name = categoryDTO.getName();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
