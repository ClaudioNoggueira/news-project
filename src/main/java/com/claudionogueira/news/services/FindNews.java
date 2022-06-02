package com.claudionogueira.news.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.utils.Check;

@Service
public class FindNews {

	private final NewsRepo repo;

	public FindNews(NewsRepo repo) {
		super();
		this.repo = repo;
	}

	@Transactional(readOnly = true)
	public News byID(String id) {
		long news_id = Check.newsID(id);
		return repo.findById(news_id)
				.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + news_id + "' not found."));
	}
}
