package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

	private final ModelMapper mapper;

	public CategoryMapper(ModelMapper mapper) {
		super();
		this.mapper = mapper;
	}
}
