package com.claudionogueira.news.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.inputs.CategoryInput;
import com.claudionogueira.news.dto.updates.CategoryUpdate;
import com.claudionogueira.news.exceptions.DomainException;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.services.interfaces.ICategoryService;
import com.claudionogueira.news.services.utils.CategoryMapper;

@Service
public class CategoryService implements ICategoryService {

	private final CategoryMapper mapper;

	private final CategoryRepo categoryRepo;

	private final FindOneCategory findCategory;

	public CategoryService(CategoryMapper mapper, CategoryRepo categoryRepo, FindOneCategory findCategory) {
		super();
		this.mapper = mapper;
		this.categoryRepo = categoryRepo;
		this.findCategory = findCategory;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> page = categoryRepo.findAll(pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CategoryDTO> findByNamePaginated(String name, Pageable pageable) {
		Page<Category> page = categoryRepo.findByNameContainingIgnoreCase(name, pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public CategoryDTO findByIdDTO(String id) {
		Category category = findCategory.byID(id);
		return mapper.fromEntityToDTO(category);
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
		Category category = mapper.fromInputToEntity(input);
		if (categoryNameIsAvailable(input.getName(), category)) {
			categoryRepo.save(category);
		}
	}

	@Override
	public void update(String id, CategoryUpdate update) {
		Category objToBeUpdated = findCategory.byID(id);
		if (categoryNameIsAvailable(update.getName(), objToBeUpdated)) {
			objToBeUpdated.setName(update.getName());
			categoryRepo.save(objToBeUpdated);
		}
	}
}
