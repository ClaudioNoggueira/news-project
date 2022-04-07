package com.claudionogueira.news.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.claudionogueira.news.models.Author;

public class AuthorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;

	private Set<NewsDTO> news = new HashSet<>();

	public AuthorDTO() {

	}

	public AuthorDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public AuthorDTO(Author author) {
		id = author.getId();
		firstName = author.getFirstName();
		lastName = author.getLastName();
		email = author.getEmail();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Set<NewsDTO> getNews() {
		return news;
	}
}
