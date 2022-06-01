package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.inputs.CategoryInput;
import com.claudionogueira.news.dto.updates.CategoryUpdate;
import com.claudionogueira.news.models.Category;

public interface ICategoryService {

	Page<CategoryDTO> convertPageToDTO(Page<Category> page);

	CategoryDTO convertCategoryToDTO(Category category);

	boolean categoryNameIsAvailable(String name, Category entity);

	// GET
	Page<CategoryDTO> findAll(Pageable pageable);

	Page<CategoryDTO> findByNamePaginated(String name, Pageable pageable);

	Category findById(String id);

	CategoryDTO findByIdDTO(String id);

	// POST
	void add(CategoryInput input);

	// PUT
	void update(String id, CategoryUpdate update);
}
