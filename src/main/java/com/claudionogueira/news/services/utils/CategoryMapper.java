package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.NewsRepo;

@Component
public class CategoryMapper {

	private final ModelMapper categoryMapper;
	private final NewsRepo newsRepo;

	public CategoryMapper(ModelMapper categoryMapper, NewsRepo newsRepo) {
		super();
		this.categoryMapper = categoryMapper;
		this.newsRepo = newsRepo;
	}

	public CategoryDTO fromEntityToDTO(Category entity) {
		CategoryDTO dto = categoryMapper.map(entity, CategoryDTO.class);

		for (CategoryNews categoryNews : entity.getNews()) {
			long news_id = categoryNews.getId().getNews().getId();

			News news = newsRepo.findById(news_id)
					.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + news_id + "' not found."));

			dto.addNews(news.getTitle(), news.getContent(), news.getDate(), new AuthorDTO(news.getAuthor()));
		}

		return dto;
	}
}
