package com.claudionogueira.news.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.services.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@Api(value = "News Project REST API")
@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsController {

	private final NewsService service;

	public NewsController(NewsService service) {
		this.service = service;
	}

	@ApiOperation(value = "Returns all news by pagination")
	@GetMapping
	public Page<NewsDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@ApiOperation(value = "Returns news by ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<NewsDTO> findById(@PathVariable String id) {
		NewsDTO body = service.findByIdDTO(id);
		return ResponseEntity.ok(body);
	}

	@ApiOperation(value = "Returns news by search(title, name)")
	@GetMapping(value = "/search")
	public Page<NewsDTO> search(@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
		Page<NewsDTO> page = Page.empty();

		// Find news by title
		if (!title.equals("")) {
			page = service.findByTitlePaginated(title, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find news by author name
		if (!name.equals("")) {
			page = service.findByAuthorName(name, pageable);
			if (!page.isEmpty()) {
				return page;
			}
		}

		return service.findAll(pageable);
	}

	@ApiOperation(value = "Add new news")
	@PostMapping(value = "/add-news")
	public ResponseEntity<Void> add(@RequestBody NewsDTO dto) {
		service.add(dto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Update news info based on it's ID")
	@PutMapping(value = "/update-news/{id}")
	public ResponseEntity<Void> update(@PathVariable String id, @RequestBody NewsDTO dto) {
		service.update(id, dto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Delete news by ID")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
