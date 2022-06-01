package com.claudionogueira.news.dto.updates;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.claudionogueira.news.common.ValidationGroups;

public class AuthorUpdate {

	@NotBlank(groups = ValidationGroups.authorFirstName.class)
	@Size(max = 60)
	private String firstName;

	@NotBlank(groups = ValidationGroups.authorLastName.class)
	@Size(max = 60)
	private String lastName;

	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	public AuthorUpdate() {

	}

	public AuthorUpdate(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
