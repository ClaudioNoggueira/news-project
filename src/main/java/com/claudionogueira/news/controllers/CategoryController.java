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

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.inputs.CategoryInput;
import com.claudionogueira.news.services.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "News Project REST API")
@RestController
@RequestMapping(value = "/api/v1/categories")
public class CategoryController {

	private final CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@ApiOperation(value = "Returns all categories by pagination")
	@GetMapping
	public Page<CategoryDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation(value = "Returns one category by ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable String id) {
		CategoryDTO dto = service.findByIdDTO(id);
		return ResponseEntity.ok(dto);
	}

	@ApiOperation(value = "Returns categories by search(name)")
	@GetMapping(value = "/search")
	public Page<CategoryDTO> search(@RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
		if (!name.equals("") && !name.isBlank()) {
			Page<CategoryDTO> page = service.findByNamePaginated(name, pageable);
			if (!page.isEmpty())
				return page;
		}

		return service.findAll(pageable);
	}

	@ApiOperation(value = "Add new category")
	@PostMapping(value = "/add-category")
	@ResponseStatus(value = HttpStatus.CREATED)
	public void add(@Valid @RequestBody CategoryInput input) {
		service.add(input);
	}

	@ApiOperation(value = "Update category info based on it's ID")
	@PutMapping(value = "/update-category/{id}")
	public ResponseEntity<Void> update(@PathVariable String id, @RequestBody CategoryDTO dto) {
		service.update(id, dto);
		return ResponseEntity.ok().build();
	}
}
