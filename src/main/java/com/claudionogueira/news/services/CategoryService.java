package com.claudionogueira.news.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
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

	@Override
	public Page<Category> findByNamePaginated(String name, Pageable pageable) {
		return repo.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Category findById(Long id) {
		Optional<Category> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Category with ID: '" + id + "' not found."));
	}

	@Override
	public boolean doesTheCategoryNameAlreadyExists(String name) {
		Category obj = repo.findByNameIgnoreCase(name);
		if (obj == null) {
			return false;
		}
		return true;
	}

	@Override
	public void add(Category entity) {
		if (this.doesTheCategoryNameAlreadyExists(entity.getName())) {
			throw new BadRequestException("Category '" + entity.getName() + "' already exists.");
		}
		repo.save(entity);
	}

}
