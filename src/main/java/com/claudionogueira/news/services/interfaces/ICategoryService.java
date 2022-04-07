package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.models.Category;

public interface ICategoryService {

	Page<CategoryDTO> convertPageToDTO(Page<Category> page);
	
	CategoryDTO convertCategoryToDTO(Category category);

	boolean doesTheCategoryNameAlreadyExists(String name);

	// GET
	Page<CategoryDTO> findAll(Pageable pageable);

	Page<CategoryDTO> findByNamePaginated(String name, Pageable pageable);

	Category findById(Long id);
	
	CategoryDTO findByIdDTO(Long id);

	// POST
	void add(Category entity);

	// PUT
	void update(Long id, Category entity);
}
