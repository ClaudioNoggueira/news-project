package com.claudionogueira.news.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.CategoryNewsPK;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.repositories.CategoryNewsRepo;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.interfaces.INewsService;
import com.claudionogueira.news.services.utils.DateValidator;

@Service
public class NewsService implements INewsService {

	private final NewsRepo newsRepo;

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final CategoryNewsRepo categoryNewsRepo;

	public NewsService(NewsRepo newsRepo, AuthorRepo authorRepo, CategoryRepo categoryRepo,
			CategoryNewsRepo categoryNewsRepo) {
		this.newsRepo = newsRepo;
		this.authorRepo = authorRepo;
		this.categoryRepo = categoryRepo;
		this.categoryNewsRepo = categoryNewsRepo;
	}

	@Override
	public Page<NewsDTO> findAll(Pageable pageable) {
		Page<News> page = newsRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Override
	public Page<NewsDTO> findByTitlePaginated(String title, Pageable pageable) {
		Page<News> page = newsRepo.findByTitleContainingIgnoreCase(title, pageable);
		return this.convertPageToDTO(page);
	}

	@Override
	public News findById(Long id) {
		Optional<News> obj = newsRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + id + "' not found."));
	}

	@Override
	public NewsDTO findByIdDTO(Long id) {
		News news = this.findById(id);
		return this.convertNewsToDTO(news);
	}

	@Override
	public Page<NewsDTO> findByAuthorName(String name, Pageable pageable) {
		// All authors
		Set<News> set = new HashSet<>();

		// Same page for both queries
		Page<Author> page = authorRepo.findByFirstNameContainingIgnoreCase(name, pageable);
		for (Author author : page) {
			for (News authorNews : author.getAuthorNews()) {
				set.add(authorNews);
			}
		}
		// Clear page to use in the next query
		page = Page.empty();

		page = authorRepo.findByLastNameContainingIgnoreCase(name, pageable);
		for (Author author : page) {
			for (News authorNews : author.getAuthorNews()) {
				set.add(authorNews);
			}
		}

		// Convert set to list
		List<News> list = new ArrayList<>(set);

		// Create page using list<News> and setting as argument for convertToDTO
		return this.convertPageToDTO(new PageImpl<News>(list));
	}

	@Override
	public NewsDTO convertNewsToDTO(News news) {
		NewsDTO dto = new NewsDTO(news);

		for (CategoryNews categoryNews : news.getCategories()) {

			// Category id based on CategoryNews id
			long category_id = categoryNews.getId().getCategory().getId();

			// Find category by id or throw exception
			Category category = categoryRepo.findById(category_id).orElseThrow(
					() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));

			// Converting to CategoryDTO
			CategoryDTO categoryDTO = new CategoryDTO(category);

			// Add categoryDTO to NewsDTO' Set<CategoryDTO> categories
			dto.getCategories().add(categoryDTO);
		}

		return dto;
	}

	@Override
	public Page<NewsDTO> convertPageToDTO(Page<News> page) {
		List<NewsDTO> list = new ArrayList<>();

		for (News news : page) {
			list.add(this.convertNewsToDTO(news));
		}

		return new PageImpl<NewsDTO>(list);
	}

	@Override
	public boolean isDateValid(String dateStr) {
		return DateValidator.isValid(dateStr);
	}

	@Override
	public void add(NewsDTO dto) {
		// Check if any of the categories exists
		for (CategoryDTO obj : dto.getCategories()) {
			// If the category doesn't exists, create a new one
			Category category = categoryRepo.findByNameIgnoreCase((obj.getName()));
			if (category == null) {
				category = new Category(null, obj.getName());
				categoryRepo.save(category);
			}
		}

		// Check if author with the id exists or throw exception
		Long author_id = dto.getAuthor().getId();
		if (author_id != null) {
			Author author = authorRepo.findById(author_id)
					.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));

			if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
				if (dto.getContent() != null && !dto.getContent().isEmpty()) {
					if (dto.getDate() != null && this.isDateValid(dto.getDate().toString())) {
						News news = new News(null, dto.getTitle(), dto.getContent(), author, dto.getDate());
						news = newsRepo.saveAndFlush(news);

						// Save categories of the new news
						for (CategoryDTO obj : dto.getCategories()) {
							Category category = categoryRepo.findByNameIgnoreCase(obj.getName());
							categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, news)));
						}
					}
				}
			}
		}
	}

	@Override
	public void update(Long id, NewsDTO dto) {
		// GET news to be updated by id or throw exception
		News newsToBeUpdated = newsRepo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + id + "' not found."));

		// Check if categories already exists or save a new one
		for (CategoryDTO obj : dto.getCategories()) {
			Category category = categoryRepo.findByNameIgnoreCase(obj.getName());
			if (category == null) {
				category = new Category(null, obj.getName());
				categoryRepo.save(category);
			}
		}

		// GET author by id or throw exception
		Long author_id = dto.getAuthor().getId();
		if (author_id != null) {
			Author author = authorRepo.findById(author_id)
					.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));
			newsToBeUpdated.setAuthor(author);
		}

		if (dto.getTitle() != null && !dto.getTitle().isEmpty())
			newsToBeUpdated.setTitle(dto.getTitle());

		if (dto.getContent() != null && !dto.getContent().isEmpty())
			newsToBeUpdated.setContent(dto.getContent());

		if (dto.getDate() != null && this.isDateValid(dto.getDate().toString()))
			newsToBeUpdated.setDate(dto.getDate());

		if (!dto.getCategories().isEmpty()) {
			// DELETE all related rows in TB_CATEGORY_NEWS
			for (CategoryNews entity : newsToBeUpdated.getCategories()) {
				categoryNewsRepo.delete(entity);
			}
			newsToBeUpdated.getCategories().clear();

			// Save all new categories of news
			for (CategoryDTO obj : dto.getCategories()) {
				Category category = categoryRepo.findByNameIgnoreCase(obj.getName());
				categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, newsToBeUpdated)));
			}
		}

		newsToBeUpdated = newsRepo.saveAndFlush(newsToBeUpdated);
	}

	@Override
	public void delete(Long id) {
		News newsToBeDeleted = this.findById(id);

		// DELETE all related rows in TB_CATEGORY_NEWS
		for (CategoryNews categoryNews : newsToBeDeleted.getCategories()) {
			categoryNewsRepo.delete(categoryNews);
		}
		newsRepo.delete(newsToBeDeleted);
	}
}
