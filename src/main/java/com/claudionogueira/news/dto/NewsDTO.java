package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private AuthorDTO author;

	private Set<NewsDTO.Category> categories = new HashSet<>();

	public NewsDTO() {

	}

	public NewsDTO(Long id, String title, String content, LocalDate date, AuthorDTO author) {
		super();
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

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public Set<NewsDTO.Category> getCategories() {
		return categories;
	}

	public void addCategory(String name) {
		this.getCategories().add(new Category(name));
	}

	public void removeCategory(String name) {
		this.getCategories().remove(new Category(name));
	}

	public static class Category {
		private String name;

		public Category() {

		}

		public Category(String name) {
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
}
