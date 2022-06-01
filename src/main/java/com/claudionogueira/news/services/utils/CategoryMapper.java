package com.claudionogueira.news.services.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.inputs.CategoryInput;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.NewsRepo;

@Component
public class CategoryMapper {

	private final ModelMapper mapper;
	private final NewsRepo newsRepo;

	public CategoryMapper(ModelMapper mapper, NewsRepo newsRepo) {
		super();
		this.mapper = mapper;
		this.newsRepo = newsRepo;
	}

	public CategoryDTO fromEntityToDTO(Category entity) {
		CategoryDTO dto = mapper.map(entity, CategoryDTO.class);

		entity.getNews().forEach(categoryNews -> {
			long news_id = categoryNews.getId().getNews().getId();

			News news = newsRepo.findById(news_id)
					.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + news_id + "' not found."));

			dto.addNews(news.getTitle(), news.getContent(), news.getDate(), new AuthorDTO(news.getAuthor()));

		});

		return dto;
	}

	public Category fromInputToEntity(CategoryInput input) {
		return mapper.map(input, Category.class);
	}

	public Page<CategoryDTO> fromPageEntityToPageDTO(Page<Category> page) {
		List<CategoryDTO> list = page.stream().map(this::fromEntityToDTO).collect(Collectors.toList());
		return new PageImpl<CategoryDTO>(list);
	}
}
