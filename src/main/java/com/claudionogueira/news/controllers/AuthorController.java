package com.claudionogueira.news.controllers;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.dto.updates.AuthorUpdate;
import com.claudionogueira.news.services.AuthorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "News Project REST API")
@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorController {

	private final AuthorService service;

	public AuthorController(AuthorService service) {
		this.service = service;
	}

	@ApiOperation(value = "Returns all authors by pagination")
	@GetMapping
	public Page<AuthorDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation(value = "Returns one author by id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> findById(@PathVariable String id) {
		AuthorDTO body = service.findByIdDTO(id);
		return ResponseEntity.ok(body);
	}

	@ApiOperation(value = "Returns authors by search (email, full-name, first-name, last-name)")
	@GetMapping(value = "/search")
	public Page<AuthorDTO> search(@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "full-name", defaultValue = "") String fullName,
			@RequestParam(value = "first-name", defaultValue = "") String firstName,
			@RequestParam(value = "last-name", defaultValue = "") String lastName, Pageable pageable) {

		// Find authors by email
		if (!email.equals("")) {
			Page<AuthorDTO> page = service.findByEmailPaginated(email, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find authors by both first and last name
		if (!fullName.equals("")) {
			Page<AuthorDTO> page = service.findByFullNamePageable(fullName, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find authors by first name
		if (!firstName.equals("")) {
			Page<AuthorDTO> page = service.findByFirstNamePaginated(firstName, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find authors by last name
		if (!lastName.equals("")) {
			Page<AuthorDTO> page = service.findByLastNamePaginated(lastName, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		return service.findAll(pageable);
	}

	@ApiOperation(value = "Add new author")
	@PostMapping(value = "/add-author")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void add(@Valid @RequestBody AuthorInput input) {
		service.add(input);
	}

	@ApiOperation(value = "Update author info based on it's ID")
	@PutMapping(value = "/update-author/{id}")
	public ResponseEntity<Void> update(@PathVariable String id, @Valid @RequestBody AuthorUpdate update) {
		service.update(id, update);
		return ResponseEntity.ok().build();
	}
}