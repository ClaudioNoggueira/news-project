package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.claudionogueira.news.models.News;
import com.fasterxml.jackson.annotation.JsonFormat;

public class NewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private AuthorNoNewsDTO author;

	private Set<CategoryNoNewsDTO> categories = new HashSet<>();

	public NewsDTO() {

	}

	public NewsDTO(News news) {
		id = news.getId();
		title = news.getTitle();
		content = news.getContent();
		date = news.getDate();
		author = new AuthorNoNewsDTO(new AuthorDTO(news.getAuthor()));
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AuthorNoNewsDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorNoNewsDTO author) {
		this.author = author;
	}

	public Set<CategoryNoNewsDTO> getCategories() {
		return categories;
	}
}
