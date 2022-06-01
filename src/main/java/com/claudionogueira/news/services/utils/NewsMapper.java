package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

	private final ModelMapper mapper;

	public NewsMapper(ModelMapper mapper) {
		super();
		this.mapper = mapper;
	}
}
