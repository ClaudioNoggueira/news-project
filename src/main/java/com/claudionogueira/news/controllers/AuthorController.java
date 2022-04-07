package com.claudionogueira.news.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.services.AuthorService;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController {

	private final AuthorService service;

	public AuthorController(AuthorService service) {
		this.service = service;
	}

	@GetMapping
	public Page<AuthorDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
		if (id == null)
			throw new BadRequestException("Author ID must NOT be null.");

		char[] digits = id.toCharArray();
		for (char digit : digits) {
			if (!Character.isDigit(digit))
				throw new BadRequestException("Author ID must be a numeric value.");
		}

		AuthorDTO body = service.findByIdDTO(Long.parseLong(id));
		return ResponseEntity.ok(body);
	}

	@GetMapping(value = "/search")
	public Page<AuthorDTO> search(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "full-name", defaultValue = "") String fullName,
			@RequestParam(value = "first-name", defaultValue = "") String firstName,
			@RequestParam(value = "last-name", defaultValue = "") String lastName, Pageable pageable) {
		Page<Author> page = Page.empty();

		// Find authors by email
		if (!email.equals("") && !email.isBlank()) {
			page = service.findByEmailPaginated(email, pageable);
			if (!page.isEmpty()) {
				return page.map(author -> new AuthorDTO(author));
			}
		}

		if (!fullName.equals("") && !email.isBlank()) {
			page = service.findByFullNamePageable(fullName, pageable);
			if (!page.isEmpty()) {
				return page.map(author -> new AuthorDTO(author));
			}
		}

		// Find authors by first name
		if (!firstName.equals("") && !email.isBlank()) {
			page = service.findByFirstNamePaginated(firstName, pageable);
			if (!page.isEmpty()) {
				return page.map(author -> new AuthorDTO(author));
			}
		}

		// Find authors by last name
		if (!lastName.equals("") && !email.isBlank()) {
			page = service.findByLastNamePaginated(lastName, pageable);
			if (!page.isEmpty()) {
				return page.map(author -> new AuthorDTO(author));
			}
		}

		return service.findAll(pageable);
	}

	@PostMapping(value = "/add-author")
	public ResponseEntity<Void> add(@RequestBody Author entity) {
		service.add(entity);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/update-author/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Author entity) {
		service.update(id, entity);
		return ResponseEntity.ok().build();
	}
}