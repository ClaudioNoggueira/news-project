package com.claudionogueira.news.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.services.utils.Check;

@Service
public class FindCategory {

	private final CategoryRepo repo;

	public FindCategory(CategoryRepo repo) {
		super();
		this.repo = repo;
	}

	@Transactional(readOnly = true)
	public Category byID(String id) {
		long category_id = Check.categoryID(id);
		return repo.findById(category_id)
				.orElseThrow(() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));
	}
}
