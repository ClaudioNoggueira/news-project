package com.claudionogueira.news.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.repositories.CategoryRepo;

@Component
public class MockData implements CommandLineRunner {

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	public MockData(AuthorRepo authorRepo, CategoryRepo categoryRepo) {
		this.authorRepo = authorRepo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		// Save authors
		List<Author> authors = new ArrayList<>();
		authors.add(new Author(null, "Elliot", "Cassie", "ecassie0@cdbaby.com"));
		authors.add(new Author(null, "Mariellen", "Stanbridge", "mstanbridge1@mapy.cz"));
		authors.add(new Author(null, "Lonnie", "Charopen", "lcharopen2@skyrock.com"));
		authors.add(new Author(null, "Izzy", "McCorry", "imccorry3@cisco.com"));
		authors.add(new Author(null, "Meredith", "Lamcken", "mlamcken4@netscape.com"));

		for (Author author : authors) {
			authorRepo.save(author);
		}

		// Save categories
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(null, "Sports"));
		categories.add(new Category(null, "Politics"));
		categories.add(new Category(null, "Cinema"));
		categories.add(new Category(null, "Economy"));
		categories.add(new Category(null, "Science"));

		for (Category category : categories) {
			categoryRepo.save(category);
		}

	}
}
