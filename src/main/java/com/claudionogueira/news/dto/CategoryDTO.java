package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.claudionogueira.news.models.Category;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private Set<NewsDTO> news = new HashSet<>();

	public CategoryDTO() {

	}

	public CategoryDTO(Category category) {
		id = category.getId();
		name = category.getName();
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<NewsDTO> getNews() {
		return news;
	}
}
