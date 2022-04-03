package com.claudionogueira.news.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	private final CategoryRepo repo;

	public CategoryService(CategoryRepo repo) {
		this.repo = repo;
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
