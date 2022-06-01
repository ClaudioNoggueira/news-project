package com.claudionogueira.news.dto.inputs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CategoryInput {

	@NotBlank
	@Size(max = 20, min = 2)
	private String name;

	public CategoryInput() {

	}

	public CategoryInput(String name) {
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
