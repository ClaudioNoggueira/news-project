package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.models.News;

public interface INewsService {

	Page<News> findAll(Pageable pageable);
	
	Page<News> findByTitlePaginated(String title, Pageable pageable);
}
