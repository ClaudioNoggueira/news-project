package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.models.News;

@Component
public class NewsMapper {

	private final ModelMapper mapper;

	public NewsMapper(ModelMapper mapper) {
		super();
		this.mapper = mapper;
	}

	public NewsDTO fromEntityToDTO(News newsEntity) {
		return mapper.map(newsEntity, NewsDTO.class);
	}
}
