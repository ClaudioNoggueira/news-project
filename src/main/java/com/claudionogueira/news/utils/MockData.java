package com.claudionogueira.news.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.CategoryNewsPK;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.repositories.CategoryNewsRepo;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.repositories.NewsRepo;

@Component
public class MockData implements CommandLineRunner {

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	private final NewsRepo newsRepo;

	private final CategoryNewsRepo categoryNewsRepo;

	public MockData(AuthorRepo authorRepo, CategoryRepo categoryRepo, NewsRepo newsRepo,
			CategoryNewsRepo categoryNewsRepo) {
		this.authorRepo = authorRepo;
		this.categoryRepo = categoryRepo;
		this.newsRepo = newsRepo;
		this.categoryNewsRepo = categoryNewsRepo;
	}

	@Override
	public void run(String... args) throws Exception {
		// Save authors
		List<Author> authors = saveAuthors();

		// Save categories
		List<Category> categories = saveCategories();

		// Save news
		List<News> newsList = saveNews(authors);

		// Save category news
		saveCategoryNews(categories, newsList);
	}

	private List<Author> saveAuthors() {
		List<Author> authors = new ArrayList<>();
		authors.add(new Author(null, "Elliot", "Cassie", "ecassie0@cdbaby.com"));
		authors.add(new Author(null, "Mariellen", "Stanbridge", "mstanbridge1@mapy.cz"));
		authors.add(new Author(null, "Lonnie", "Charopen", "lcharopen2@skyrock.com"));
		authors.add(new Author(null, "Izzy", "McCorry", "imccorry3@cisco.com"));
		authors.add(new Author(null, "Meredith", "Lamcken", "mlamcken4@netscape.com"));

		for (Author author : authors) {
			authorRepo.save(author);
		}

		return authors;
	}

	private List<Category> saveCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(null, "Sports"));
		categories.add(new Category(null, "Politics"));
		categories.add(new Category(null, "Cinema"));
		categories.add(new Category(null, "Economy"));
		categories.add(new Category(null, "Science"));

		for (Category category : categories) {
			categoryRepo.save(category);
		}

		return categories;
	}

	private List<News> saveNews(List<Author> authors) {
		List<News> newsList = new ArrayList<>();
		newsList.add(new News(null, "News title about SPORTS 1",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(0), LocalDate.now()));
		newsList.add(new News(null, "News title about POLITICS 1",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(0), LocalDate.now()));
		newsList.add(new News(null, "News title about SPORTS 2",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(1), LocalDate.now()));
		newsList.add(new News(null, "News title about CINEMA 1",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(1), LocalDate.now()));
		newsList.add(new News(null, "News title about POLITICS 2",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(2), LocalDate.now()));
		newsList.add(new News(null, "News title about CINEMA 2",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(2), LocalDate.now()));
		newsList.add(new News(null, "News title about ECONOMY 1",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(2), LocalDate.now()));
		newsList.add(new News(null, "News title about ECONOMY 2",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(3), LocalDate.now()));
		newsList.add(new News(null, "News title about POLITICS 3",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(3), LocalDate.now()));
		newsList.add(new News(null, "News title about SCIENCE 1",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
				authors.get(4), LocalDate.now()));

		for (News news : newsList) {
			newsRepo.save(news);
		}

		return newsList;
	}

	private void saveCategoryNews(List<Category> categories, List<News> newsList) {
		List<CategoryNews> list = new ArrayList<>();
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(0), newsList.get(0))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(1), newsList.get(1))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(0), newsList.get(2))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(2), newsList.get(3))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(1), newsList.get(4))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(2), newsList.get(5))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(3), newsList.get(6))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(3), newsList.get(7))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(1), newsList.get(8))));
		list.add(new CategoryNews(new CategoryNewsPK(categories.get(4), newsList.get(9))));

		for (CategoryNews obj : list) {
			categoryNewsRepo.save(obj);
		}
	}
}
