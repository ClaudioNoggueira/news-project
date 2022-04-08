package com.claudionogueira.news.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.NewsNoCategoryDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.interfaces.ICategoryService;
import com.claudionogueira.news.services.utils.Check;

@Service
public class CategoryService implements ICategoryService {

	private final CategoryRepo categoryRepo;

	private final NewsRepo newsRepo;

	public CategoryService(CategoryRepo categoryRepo, NewsRepo newsRepo) {
		this.categoryRepo = categoryRepo;
		this.newsRepo = newsRepo;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> page = categoryRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDTO> findByNamePaginated(String name, Pageable pageable) {
		Page<Category> page = categoryRepo.findByNameContainingIgnoreCase(name, pageable);
		return this.convertPageToDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Category findById(String id) {
		long category_id = Check.categoryID(id);
		return categoryRepo.findById(category_id)
				.orElseThrow(() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));
	}

	@Transactional(readOnly = true)
	@Override
	public CategoryDTO findByIdDTO(String id) {
		Category category = this.findById(id);
		return this.convertCategoryToDTO(category);
	}

	@Override
	public CategoryDTO convertCategoryToDTO(Category category) {
		CategoryDTO dto = new CategoryDTO(category);

		for (CategoryNews categoryNews : category.getNews()) {
			long news_id = categoryNews.getId().getNews().getId();

			News news = newsRepo.findById(news_id)
					.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + news_id + "' not found."));

			dto.getNews().add(new NewsNoCategoryDTO(new NewsDTO(news)));
		}

		return dto;
	}

	@Override
	public Page<CategoryDTO> convertPageToDTO(Page<Category> page) {
		List<CategoryDTO> list = new ArrayList<>();

		for (Category category : page) {
			list.add(this.convertCategoryToDTO(category));
		}

		return new PageImpl<CategoryDTO>(list);
	}

	@Override
	public boolean doesTheCategoryNameAlreadyExists(String name) {
		Category obj = categoryRepo.findByNameIgnoreCase(name);
		if (obj == null)
			return false;

		throw new BadRequestException("Category '" + name + "' already exists.");
	}

	@Override
	public void add(CategoryDTO dto) {
		dto = Check.categoryDTO(dto);
		if (!this.doesTheCategoryNameAlreadyExists(dto.getName()))
			categoryRepo.save(new Category(null, dto.getName()));
	}

	@Override
	public void update(String id, CategoryDTO dto) {
		Category objToBeUpdated = this.findById(id);

		if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().isBlank())
			throw new BadRequestException("Category name is mandatory and cannot be null, empty or blank.");

		if (!this.doesTheCategoryNameAlreadyExists(dto.getName())) {
			objToBeUpdated.setName(dto.getName());
			categoryRepo.save(objToBeUpdated);
		}
	}
}
