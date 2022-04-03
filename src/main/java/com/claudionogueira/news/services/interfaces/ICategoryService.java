package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.models.Category;

public interface ICategoryService {

	Page<Category> findAll(Pageable pageable);

	Page<Category> findByName(String name, Pageable pageable);
}
