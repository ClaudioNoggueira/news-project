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

import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.services.CategoryService;

@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	private final CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@GetMapping
	public Page<Category> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok(obj);
	}

	@GetMapping(value = "/search")
	public Page<Category> search(@RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
		if (!name.equals("")) {
			Page<Category> page = service.findByNamePaginated(name, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		return service.findAll(pageable);
	}

	@PostMapping(value = "/add-category")
	public ResponseEntity<Void> add(@RequestBody Category entity) {
		service.add(entity);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value = "/update-category/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Category entity) {
		service.update(id, entity);
		return ResponseEntity.ok().build();
	}
}