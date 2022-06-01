package com.claudionogueira.news.services.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.inputs.NewsInput;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.CategoryRepo;

@Component
public class NewsMapper {

	private final ModelMapper mapper;
	private final CategoryRepo categoryRepo;

	public NewsMapper(ModelMapper mapper, CategoryRepo categoryRepo) {
		super();
		this.mapper = mapper;
		this.categoryRepo = categoryRepo;
	}

	public NewsDTO fromEntityToDTO(News entity) {
		NewsDTO dto = mapper.map(entity, NewsDTO.class);

		entity.getCategories().forEach(categoryNews -> {
			// Category id based on CategoryNews id
			long category_id = categoryNews.getId().getCategory().getId();

			// Find category by id or throw exception
			Category category = categoryRepo.findById(category_id).orElseThrow(
					() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));

			dto.addCategory(category.getName());
		});

		return dto;
	}

	public News fromInputToEntity(NewsInput input) {
		return mapper.map(input, News.class);
	}

	public Page<NewsDTO> fromPageEntityToPageDTO(Page<News> page) {
		List<NewsDTO> list = page.stream().map(this::fromEntityToDTO).collect(Collectors.toList());
		return new PageImpl<NewsDTO>(list);
	}
}
