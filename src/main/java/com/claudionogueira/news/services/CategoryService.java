package com.claudionogueira.news.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.inputs.CategoryInput;
import com.claudionogueira.news.dto.updates.CategoryUpdate;
import com.claudionogueira.news.exceptions.DomainException;
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

			dto.addNews(news.getTitle(), news.getContent(), news.getDate(), new AuthorDTO(news.getAuthor()));
		}

		return dto;
	}

	@Override
	public Page<CategoryDTO> convertPageToDTO(Page<Category> page) {
		List<CategoryDTO> list = page.stream().map(this::convertCategoryToDTO).collect(Collectors.toList());
		return new PageImpl<CategoryDTO>(list);
	}

	@Override
	public boolean categoryNameIsAvailable(String name, Category entity) {
		boolean categoryIsNotAvailable = categoryRepo.findByNameIgnoreCase(name).stream()
				.anyMatch(existingCategory -> !existingCategory.equals(entity));

		if (categoryIsNotAvailable) {
			throw new DomainException("Category '" + name + "' already exists.");
		}

		return true;
	}

	@Override
	public void add(CategoryInput input) {
		Category category = new Category(null, input.getName());
		if (categoryNameIsAvailable(input.getName(), category)) {
			categoryRepo.save(category);
		}
	}

	@Override
	public void update(String id, CategoryUpdate update) {
		Category objToBeUpdated = this.findById(id);
		if (categoryNameIsAvailable(update.getName(), objToBeUpdated)) {
			objToBeUpdated.setName(update.getName());
			categoryRepo.save(objToBeUpdated);
		}
	}
}
