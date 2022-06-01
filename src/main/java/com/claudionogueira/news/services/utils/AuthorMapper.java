package com.claudionogueira.news.services.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

	private final ModelMapper mapper;

	public AuthorMapper(ModelMapper mapper) {
		super();
		this.mapper = mapper;
	}
}
