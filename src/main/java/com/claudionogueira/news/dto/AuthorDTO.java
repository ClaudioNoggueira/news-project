package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class AuthorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String firstName;
	private String lastName;
	private String email;

	private Set<AuthorDTO.News> news = new HashSet<>();

	public AuthorDTO() {

	}

	public AuthorDTO(String id, String firstName, String lastName, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<AuthorDTO.News> getNews() {
		return news;
	}

	public void addNews(String title, String content, LocalDate date) {
		this.getNews().add(new News(title, content, date));
	}

	public void removeNews(String title, String content, LocalDate date) {
		this.getNews().remove(new News(title, content, date));
	}

	public static class News {
		private String title;
		private String content;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
		private LocalDate date;

		private List<NewsDTO.Category> categories = new ArrayList<>();

		public News() {

		}

		public News(String title, String content, LocalDate date) {
			super();
			this.title = title;
			this.content = content;
			this.date = date;
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

		public List<NewsDTO.Category> getCategories() {
			return categories;
		}

		public void addCategory(String name) {
			this.getCategories().add(new NewsDTO.Category(name));
		}

		public void removeCategory(String name) {
			this.getCategories().remove(new NewsDTO.Category(name));
		}
	}
}
