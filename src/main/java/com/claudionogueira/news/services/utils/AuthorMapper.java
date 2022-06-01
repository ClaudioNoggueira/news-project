package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.repositories.CategoryRepo;

@Component
public class AuthorMapper {

	private final ModelMapper authorMapper;
	private final NewsMapper newsMapper;
	private final CategoryRepo categoryRepo;

	public AuthorMapper(ModelMapper authorMapper, NewsMapper newsMapper, CategoryRepo categoryRepo) {
		super();
		this.authorMapper = authorMapper;
		this.newsMapper = newsMapper;
		this.categoryRepo = categoryRepo;
	}

	public AuthorDTO fromEntityToDTO(Author entity) {
		AuthorDTO dto = authorMapper.map(entity, AuthorDTO.class);

		entity.getAuthorNews().forEach(news -> {
			NewsDTO newsDTO = newsMapper.fromEntityToDTO(news);

			news.getCategories().forEach(categoryNews -> {
				long category_id = categoryNews.getId().getCategory().getId();
				Category category = categoryRepo.findById(category_id).orElseThrow(
						() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));
				newsDTO.addCategory(category.getName());
			});

			dto.addNews(newsDTO.getTitle(), newsDTO.getContent(), newsDTO.getDate());

			// Map NewsDTO.Category with Author.News.categories
			newsDTO.getCategories().forEach(category -> {
				dto.getNews().forEach(authorNews -> {
					authorNews.addCategory(category.getName());
				});
			});
		});

		return dto;
	}

	public Author fromInputToEntity(AuthorInput input) {
		return authorMapper.map(input, Author.class);
	}
}
