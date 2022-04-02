package com.claudionogueira.news.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public Page<Author> findAll(Pageable pageable){
		return service.findAll(pageable);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Author> findById(@PathVariable Long id){
		Author body = service.findById(id);
		return ResponseEntity.ok(body);
	}
}
