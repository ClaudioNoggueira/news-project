package com.claudionogueira.news.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.services.NewsService;

@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsController {

	private final NewsService service;

	public NewsController(NewsService service) {
		this.service = service;
	}

	@GetMapping
	public Page<NewsDTO> findAll(Pageable pageable) {
		return service.findAll(pageable);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<News> findById(@PathVariable Long id) {
		News body = service.findById(id);
		return ResponseEntity.ok(body);
	}

	@GetMapping(value = "/search")
	public Page<NewsDTO> search(@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "name", defaultValue = "") String name, Pageable pageable) {
		Page<NewsDTO> page = Page.empty();

		// Find news by title
		if (!title.equals("")) {
			page = service.findByTitlePaginated(title, pageable).map(x -> new NewsDTO(x));
			if (!page.isEmpty()) {
				return page;
			}
		}

		// Find news by author name
		if (!name.equals("")) {
			page = service.findByAuthorName(name, pageable).map(x -> new NewsDTO(x));
			if (!page.isEmpty()) {
				return page;
			}
		}

		return service.findAll(pageable);
	}

	@PostMapping(value = "/add-news")
	public ResponseEntity<Void> add(@RequestBody NewsDTO dto) {
		service.add(dto);
		return ResponseEntity.ok().build();
	}
}
