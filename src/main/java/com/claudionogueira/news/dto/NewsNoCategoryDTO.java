package com.claudionogueira.news.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class NewsNoCategoryDTO {

	private String id;
	private String title;
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate date;
	private AuthorNoNewsDTO author;

	public NewsNoCategoryDTO() {

	}

	public NewsNoCategoryDTO(NewsDTO news) {
		id = news.getId().toString();
		title = news.getTitle();
		content = news.getContent();
		date = news.getDate();
		author = news.getAuthor();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
}
