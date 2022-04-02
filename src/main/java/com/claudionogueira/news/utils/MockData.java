package com.claudionogueira.news.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.repositories.AuthorRepo;

@Component
public class MockData implements CommandLineRunner{

	private final AuthorRepo authorRepo;

	public MockData(AuthorRepo authorRepo) {
		this.authorRepo = authorRepo;
	}
	
	@Override
	public void run(String... args) throws Exception {
		List<Author> authors = new ArrayList<>();
		authors.add(new Author(null, "Elliot", "Cassie", "ecassie0@cdbaby.com"));
		authors.add(new Author(null, "Mariellen", "Stanbridge", "mstanbridge1@mapy.cz"));
		authors.add(new Author(null, "Lonnie", "Charopen", "lcharopen2@skyrock.com"));
		authors.add(new Author(null, "Izzy", "McCorry", "imccorry3@cisco.com"));
		authors.add(new Author(null, "Meredith", "Lamcken", "mlamcken4@netscape.com"));
		
		for(Author author : authors) {
			authorRepo.save(author);
		}		
	}
}
