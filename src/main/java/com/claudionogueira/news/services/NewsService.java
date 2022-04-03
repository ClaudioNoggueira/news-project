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

import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.repositories.NewsRepo;
import com.claudionogueira.news.services.interfaces.INewsService;

@Service
public class NewsService implements INewsService {

	private final NewsRepo newsRepo;

	private final AuthorRepo authorRepo;

	public NewsService(NewsRepo newsRepo, AuthorRepo authorRepo) {
		this.newsRepo = newsRepo;
		this.authorRepo = authorRepo;
	}

	@Override
	public Page<News> findAll(Pageable pageable) {
		return newsRepo.findAll(pageable);
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
}
