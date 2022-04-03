package com.claudionogueira.news.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.interfaces.IAuthorService;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorRepo repo;

	public AuthorService(AuthorRepo repo) {
		this.repo = repo;
	}

	@Override
	public Page<Author> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public Author findById(Long id) {
		Optional<Author> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + id + "' not found."));
	}

	@Override
	public Page<Author> findByEmailPaginated(String email, Pageable pageable) {
		return repo.findByEmailPaginated(email, pageable);
	}

}
