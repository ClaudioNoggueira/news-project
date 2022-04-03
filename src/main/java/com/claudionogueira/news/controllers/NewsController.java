package com.claudionogueira.news.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claudionogueira.news.services.NewsService;

@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsController {

	private final NewsService service;

	public NewsController(NewsService service) {
		this.service = service;
	}
}
