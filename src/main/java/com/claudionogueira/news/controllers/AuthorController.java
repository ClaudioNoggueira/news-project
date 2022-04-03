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
	public Page<Author> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Author> findById(@PathVariable Long id) {
		Author body = service.findById(id);
		return ResponseEntity.ok(body);
	}

	@GetMapping(value = "/search")
	public Page<Author> search(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "full-name", defaultValue = "") String fullName,
			@RequestParam(value = "first-name", defaultValue = "") String firstName,
			@RequestParam(value = "last-name", defaultValue = "") String lastName, Pageable pageable) {
		Page<Author> page = Page.empty();

		// Find authors by email
		if (!email.equals("")) {
			page = service.findByEmailPaginated(email, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		if (!fullName.equals("")) {
			page = service.findByFullNamePageable(fullName, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find authors by first name
		if (!firstName.equals("")) {
			page = service.findByFirstNamePaginated(firstName, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find authors by last name
		if (!lastName.equals("")) {
			page = service.findByLastNamePaginated(lastName, pageable);
			if (!page.isEmpty()) {
				return page;
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