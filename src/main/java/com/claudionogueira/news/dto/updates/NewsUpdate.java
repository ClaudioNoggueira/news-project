package com.claudionogueira.news.dto.updates;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewsUpdate {

	@NotBlank
	@Size(max = 60, min = 8)
	private String title;

	@NotBlank
	private String content;

	@Valid
	@NotNull
	private NewsUpdate.AuthorID author;

	@Valid
	@NotEmpty
	private List<NewsUpdate.Category> categories = new ArrayList<>();

	public NewsUpdate() {

	}

	public NewsUpdate(String title, String content, NewsUpdate.AuthorID author) {
		super();
		this.title = title;
		this.content = content;
		this.author = author;
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

	public NewsUpdate.AuthorID getAuthor() {
		return author;
	}

	public void setAuthor(NewsUpdate.AuthorID author) {
		this.author = author;
	}

	public List<NewsUpdate.Category> getCategories() {
		return categories;
	}

	public static class AuthorID {

		@NotNull
		private Long id;

		public AuthorID() {

		}

		public AuthorID(Long id) {
			super();
			this.id = id;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

	public static class Category {

		@NotBlank
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
