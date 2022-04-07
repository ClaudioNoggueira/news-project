package com.claudionogueira.news.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

	private final CategoryRepo categoryRepo;

	private final NewsRepo newsRepo;

	public CategoryService(CategoryRepo categoryRepo, NewsRepo newsRepo) {
		this.categoryRepo = categoryRepo;
		this.newsRepo = newsRepo;
	}

	@Override
	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> page = categoryRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Override
	public Page<CategoryDTO> findByNamePaginated(String name, Pageable pageable) {
		Page<Category> page = categoryRepo.findByNameContainingIgnoreCase(name, pageable);
		return this.convertPageToDTO(page);
	}

	@Override
	public Category findById(String id) {
		if (id == null)
			throw new BadRequestException("Category ID must NOT be null.");

		char[] digits = id.toCharArray();
		for (char digit : digits) {
			if (!Character.isDigit(digit))
				throw new BadRequestException("Category ID must be a numeric value.");
		}

		long category_id = Long.parseLong(id);
		return categoryRepo.findById(category_id)
				.orElseThrow(() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));
	}

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

			NewsDTO newsDTO = new NewsDTO(news);

			dto.getNews().add(newsDTO);
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

		return true;
	}

	@Override
	public void add(Category entity) {
		if (entity.getName() == null || entity.getName().isEmpty() || entity.getName().isBlank())
			throw new BadRequestException("Category name is mandatory and cannot be null, empty or blank.");

		if (this.doesTheCategoryNameAlreadyExists(entity.getName()))
			throw new BadRequestException("Category '" + entity.getName() + "' already exists.");

		categoryRepo.save(entity);
	}

	@Override
	public void update(String id, Category entity) {
		Category objToBeUpdated = this.findById(id);

		if (entity.getName() == null || entity.getName().isEmpty() || entity.getName().isBlank())
			throw new BadRequestException("Category name is mandatory and cannot be null, empty or blank.");

		if (this.doesTheCategoryNameAlreadyExists(entity.getName()))
			throw new BadRequestException("Category '" + entity.getName() + "' already exists.");

		objToBeUpdated.setName(entity.getName());
		categoryRepo.save(objToBeUpdated);
	}
}
