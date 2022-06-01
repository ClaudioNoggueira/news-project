package com.claudionogueira.news.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.utils.Check;

@Service
public class FindAuthor {

	private final AuthorRepo repo;

	public FindAuthor(AuthorRepo repo) {
		super();
		this.repo = repo;
	}

	@Transactional(readOnly = true)
	public Author byID(String id) {
		long author_id = Check.authorID(id);
		return repo.findById(author_id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));
	}
}
