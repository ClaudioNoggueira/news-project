package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AuthorNewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String content;

	private Set<CategoryNoNewsDTO> categories = new HashSet<>();

	public AuthorNewsDTO() {

	}

	public AuthorNewsDTO(NewsDTO news) {
		id = news.getId();
		title = news.getTitle();
		content = news.getContent();
		categories.addAll(news.getCategories());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<CategoryNoNewsDTO> getCategories() {
		return categories;
	}
}
