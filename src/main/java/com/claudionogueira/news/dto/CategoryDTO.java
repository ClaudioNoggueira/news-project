package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	private Set<CategoryDTO.News> news = new HashSet<>();

	public CategoryDTO() {

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

	public Set<CategoryDTO.News> getNews() {
		return news;
	}

	public void addNews(String title, String content, LocalDate date, AuthorDTO author) {
		this.getNews().add(new News(title, content, date, author));
	}

	public void removeNews(String title, String content, LocalDate date, AuthorDTO author) {
		this.getNews().remove(new News(title, content, date, author));
	}

	public static class News {
		private String title;
		private String content;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
		private LocalDate date;
		private AuthorDTO author;

		public News() {

		}

		public News(String title, String content, LocalDate date, AuthorDTO author) {
			super();
			this.title = title;
			this.content = content;
			this.date = date;
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
	}
}
