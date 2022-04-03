package com.claudionogueira.news.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public Page<Category> findAll(Pageable pageable){
		return service.findAll(pageable);
	}
}
