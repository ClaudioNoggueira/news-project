package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.models.News;

public interface INewsService {

	NewsDTO convertNewsToDTO(News news);

	Page<NewsDTO> convertPageToDTO(Page<News> page);

	// GET
	News findById(String id);

	NewsDTO findByIdDTO(String id);

	Page<NewsDTO> findAll(Pageable pageable);

	Page<NewsDTO> findByTitlePaginated(String title, Pageable pageable);

	Page<NewsDTO> findByAuthorName(String name, Pageable pageable);

	// POST
	void add(NewsDTO dto);

	// PUT
	void update(String id, NewsDTO dto);

	// DELETE
	void delete(String id);
}
