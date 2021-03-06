package com.claudionogueira.news.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.utils.AuthorMapper;
import com.claudionogueira.news.services.utils.Check;

@Service
public class FindOneAuthor {

	private final AuthorRepo repo;
	private final AuthorMapper mapper;

	public FindOneAuthor(AuthorRepo repo, AuthorMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	@Transactional(readOnly = true)
	public Author byID(String id) {
		long author_id = Check.authorID(id);
		return repo.findById(author_id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));
	}

	@Transactional(readOnly = true)
	public AuthorDTO byEmail(String email) {
		Author author = repo.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Author with e-mail: '" + email + "' not found."));
		return mapper.fromEntityToDTO(author);
	}
}
