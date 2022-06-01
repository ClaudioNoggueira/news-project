package com.claudionogueira.news.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.inputs.NewsInput;
import com.claudionogueira.news.dto.updates.NewsUpdate;
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
import com.claudionogueira.news.services.utils.Check;
import com.claudionogueira.news.services.utils.NewsMapper;

@Service
public class NewsService implements INewsService {

	private final NewsMapper mapper;

	private final NewsRepo newsRepo;

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final CategoryNewsRepo categoryNewsRepo;

	public NewsService(NewsMapper mapper, NewsRepo newsRepo, AuthorRepo authorRepo, CategoryRepo categoryRepo,
			CategoryNewsRepo categoryNewsRepo) {
		super();
		this.mapper = mapper;
		this.newsRepo = newsRepo;
		this.authorRepo = authorRepo;
		this.categoryRepo = categoryRepo;
		this.categoryNewsRepo = categoryNewsRepo;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<NewsDTO> findAll(Pageable pageable) {
		Page<News> page = newsRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<NewsDTO> findByTitlePaginated(String title, Pageable pageable) {
		Page<News> page = newsRepo.findByTitleContainingIgnoreCase(title, pageable);
		return this.convertPageToDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public News findById(String id) {
		long news_id = Check.newsID(id);
		return newsRepo.findById(news_id)
				.orElseThrow(() -> new ObjectNotFoundException("News with ID: '" + news_id + "' not found."));
	}

	@Transactional(readOnly = true)
	@Override
	public NewsDTO findByIdDTO(String id) {
		News news = this.findById(id);
		return this.convertNewsToDTO(news);
	}

	@Transactional(readOnly = true)
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
		NewsDTO dto = mapper.fromEntityToDTO(news);

		for (CategoryNews categoryNews : news.getCategories()) {

			// Category id based on CategoryNews id
			long category_id = categoryNews.getId().getCategory().getId();

			// Find category by id or throw exception
			Category category = categoryRepo.findById(category_id).orElseThrow(
					() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));

			dto.addCategory(category.getName());
		}

		return dto;
	}

	@Override
	public Page<NewsDTO> convertPageToDTO(Page<News> page) {
		List<NewsDTO> list = page.stream().map(this::convertNewsToDTO).collect(Collectors.toList());
		return new PageImpl<NewsDTO>(list);
	}

	@Override
	public void add(NewsInput input) {

		// Check if author with the id exists or throw exception
		long author_id = input.getAuthor().getId();
		authorRepo.findById(author_id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));

		// Check if any of the categories exists
		for (NewsInput.Category nic : input.getCategories()) {

			// If the category doesn't exists, create a new one
			if (categoryRepo.findByNameIgnoreCase(nic.getName()) == null)
				categoryRepo.save(new Category(null, nic.getName()));
		}

		News news = newsRepo.saveAndFlush(mapper.fromInputToEntity(input));
		news.setDate(LocalDate.now());

		// Save relation between category and news (CategoryNews)
		for (NewsInput.Category nic : input.getCategories()) {

			// Required categoryID
			String categoryName = nic.getName();
			Category category = categoryRepo.findByNameIgnoreCase(categoryName).orElseThrow(
					() -> new ObjectNotFoundException("Category with name: '" + categoryName + "' not found."));

			categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, news)));
		}
	}

	@Override
	public void update(String id, NewsUpdate update) {
		// GET news to be updated by id or throw exception
		News newsToBeUpdated = this.findById(id);

		// Check if categories already exists or save a new one
		for (NewsUpdate.Category ndc : update.getCategories()) {
			// Required categoryID
			String categoryName = ndc.getName();
			Category category = categoryRepo.findByNameIgnoreCase(categoryName).orElseThrow(
					() -> new ObjectNotFoundException("Category with name: '" + categoryName + "' not found."));
			if (category == null) {
				category = new Category(null, ndc.getName());
				categoryRepo.save(category);
			}
		}

		// GET author by id or throw exception
		long author_id = Check.authorID(String.valueOf(update.getAuthor().getId()));
		Author author = authorRepo.findById(author_id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));
		newsToBeUpdated.setAuthor(author);

		if (update.getTitle() != null && !update.getTitle().isEmpty())
			newsToBeUpdated.setTitle(update.getTitle());

		if (update.getContent() != null && !update.getContent().isEmpty())
			newsToBeUpdated.setContent(update.getContent());

		newsToBeUpdated.setDate(LocalDate.now());

		if (!update.getCategories().isEmpty()) {
			// DELETE all related rows in TB_CATEGORY_NEWS
			for (CategoryNews entity : newsToBeUpdated.getCategories()) {
				categoryNewsRepo.delete(entity);
			}

			newsToBeUpdated.getCategories().clear();

			// Save all new categories of news
			for (NewsUpdate.Category ndc : update.getCategories()) {
				String categoryName = ndc.getName();
				Category category = categoryRepo.findByNameIgnoreCase(categoryName).orElseThrow(
						() -> new ObjectNotFoundException("Category with name: '" + categoryName + "' not found."));
				categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, newsToBeUpdated)));
			}
		}

		newsRepo.saveAndFlush(newsToBeUpdated);
	}

	@Override
	public void delete(String id) {
		News newsToBeDeleted = this.findById(id);

		// DELETE all related rows in TB_CATEGORY_NEWS
		for (CategoryNews categoryNews : newsToBeDeleted.getCategories()) {
			categoryNewsRepo.delete(categoryNews);
		}
		newsRepo.delete(newsToBeDeleted);
	}
}
