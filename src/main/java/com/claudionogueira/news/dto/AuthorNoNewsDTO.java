package com.claudionogueira.news.dto;

import java.io.Serializable;

public class AuthorNoNewsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String firstName;
	private String lastName;
	private String email;

	public AuthorNoNewsDTO() {

	}

	public AuthorNoNewsDTO(AuthorDTO dto) {
		id = dto.getId();
		firstName = dto.getFirstName();
		lastName = dto.getLastName();
		email = dto.getEmail();
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
}