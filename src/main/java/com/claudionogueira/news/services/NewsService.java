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
		List<NewsDTO> list = new ArrayList<>();

		for (News news : page) {
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
			list.add(dto);
		}
		return new PageImpl<NewsDTO>(list);
	}

	@Override
	public Page<News> findByTitlePaginated(String title, Pageable pageable) {
		return newsRepo.findByTitleContainingIgnoreCase(title, pageable);
	}

	@Override
	public News findById(Long id) {
		Optional<News> obj = newsRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + id + "' not found."));
	}

	@Override
	public Page<News> findByAuthorName(String name, Pageable pageable) {

		Page<Author> authorsByFirstName = authorRepo.findByFirstNameContainingIgnoreCase(name, pageable);
		Page<Author> authorsByLastName = authorRepo.findByLastNameContainingIgnoreCase(name, pageable);

		Set<News> set = new HashSet<>();

		for (Author author : authorsByFirstName) {
			for (News authorNews : author.getAuthorNews()) {
				set.add(authorNews);
			}
		}

		for (Author author : authorsByLastName) {
			for (News authorNews : author.getAuthorNews()) {
				set.add(authorNews);
			}
		}

		List<News> list = new ArrayList<>(set);
		Page<News> page = new PageImpl<News>(list);
		return page;
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
			// DELETE previous categories the news had
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
}
