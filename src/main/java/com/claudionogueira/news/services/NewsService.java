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
import com.claudionogueira.news.services.utils.NewsMapper;

@Service
public class NewsService implements INewsService {

	private final NewsMapper mapper;

	private final NewsRepo newsRepo;

	private final FindOneNews findNews;

	private final FindOneAuthor findAuthor;

	private final FindOneCategory findCategory;

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final CategoryNewsRepo categoryNewsRepo;

	public NewsService(NewsMapper mapper, NewsRepo newsRepo, FindOneNews findNews, FindOneAuthor findAuthor,
			FindOneCategory findCategory, AuthorRepo authorRepo, CategoryRepo categoryRepo,
			CategoryNewsRepo categoryNewsRepo) {
		super();
		this.mapper = mapper;
		this.newsRepo = newsRepo;
		this.findNews = findNews;
		this.findAuthor = findAuthor;
		this.findCategory = findCategory;
		this.authorRepo = authorRepo;
		this.categoryRepo = categoryRepo;
		this.categoryNewsRepo = categoryNewsRepo;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<NewsDTO> findAll(Pageable pageable) {
		Page<News> page = newsRepo.findAll(pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<NewsDTO> findByTitlePaginated(String title, Pageable pageable) {
		Page<News> page = newsRepo.findByTitleContainingIgnoreCase(title, pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public NewsDTO findByIdDTO(String id) {
		return mapper.fromEntityToDTO(findNews.byID(id));
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

		// Convert set to list while converting News to NewsDTO
		List<NewsDTO> list = new ArrayList<>(
				set.stream().map(news -> mapper.fromEntityToDTO(news)).collect(Collectors.toList()));

		return new PageImpl<NewsDTO>(list);
	}

	@Override
	public void add(NewsInput input) {

		// Check if author with the id exists or throw exception
		String author_id = input.getAuthor().getId().toString();
		findAuthor.byID(author_id);

		// Check if any of the categories exists
		for (NewsInput.Category nic : input.getCategories()) {

			// If the category doesn't exists, create a new one
			if (findCategory.byName(nic.getName()) == null)
				categoryRepo.save(new Category(null, nic.getName()));
		}

		News news = mapper.fromInputToEntity(input);
		news.setDate(LocalDate.now());
		newsRepo.saveAndFlush(news);

		// Save relation between category and news (CategoryNews)
		for (NewsInput.Category nic : input.getCategories()) {

			// Required categoryID
			Category category = findCategory.byName(nic.getName());

			categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, news)));
		}
	}

	@Override
	public void update(String id, NewsUpdate update) {
		// GET news to be updated by id or throw exception
		News newsToBeUpdated = findNews.byID(id);

		// Check if categories already exists or save a new one
		for (NewsUpdate.Category ndc : update.getCategories()) {

			// Required categoryID
			Category category = findCategory.byName(ndc.getName());
			if (category == null) {
				categoryRepo.save(new Category(null, ndc.getName()));
			}
		}

		// GET author by id or throw exception
		String author_id = update.getAuthor().getId().toString();
		Author author = findAuthor.byID(author_id);
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
				Category category = findCategory.byName(ndc.getName());
				categoryNewsRepo.save(new CategoryNews(new CategoryNewsPK(category, newsToBeUpdated)));
			}
		}

		newsRepo.saveAndFlush(newsToBeUpdated);
	}

	@Override
	public void delete(String id) {
		News newsToBeDeleted = findNews.byID(id);

		// DELETE all related rows in TB_CATEGORY_NEWS
		for (CategoryNews categoryNews : newsToBeDeleted.getCategories()) {
			categoryNewsRepo.delete(categoryNews);
		}
		newsRepo.delete(newsToBeDeleted);
	}
}
