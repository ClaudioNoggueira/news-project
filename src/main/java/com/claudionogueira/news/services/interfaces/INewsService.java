package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.models.News;

public interface INewsService {

	Page<NewsDTO> findAll(Pageable pageable);
	
	Page<News> findByTitlePaginated(String title, Pageable pageable);
	
	News findById(Long id);
	
	Page<News> findByAuthorName(String name, Pageable pageable);
	
	void add(NewsDTO dto);
	
	void update(Long id, NewsDTO dto);
}
