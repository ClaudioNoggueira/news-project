package com.claudionogueira.news.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.interfaces.INewsService;

@Service
public class NewsService implements INewsService {

	private final NewsRepo repo;

	public NewsService(NewsRepo repo) {
		this.repo = repo;
	}

	@Override
	public Page<News> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
