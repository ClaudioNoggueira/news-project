package com.claudionogueira.news.dto.updates;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryUpdate {

	@NotBlank
	@Size(max = 20, min = 2)
	private String name;

	public CategoryUpdate() {

	}

	public CategoryUpdate(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
