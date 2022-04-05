package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.News;
import com.fasterxml.jackson.annotation.JsonFormat;

public class NewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private Author author;

	private Set<CategoryDTO> categories = new HashSet<>();

	public NewsDTO() {

	}

	public NewsDTO(News news) {
		id = news.getId();
		title = news.getTitle();
		content = news.getContent();
		date = news.getDate();
		author = news.getAuthor();
	}

	public NewsDTO(Long id, String title, String content, LocalDate date, Author author) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.author = author;
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Set<CategoryDTO> getCategories() {
		return categories;
	}
}
