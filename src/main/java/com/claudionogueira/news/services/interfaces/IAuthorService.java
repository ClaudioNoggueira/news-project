package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.models.Author;

public interface IAuthorService {

	Page<Author> findAll(Pageable pageable);

	Author findById(Long id);

	Page<Author> findByEmailPaginated(String email, Pageable pageable);

	Page<Author> findByFullNamePageable(String fullName, Pageable pageable);
	
	Page<Author> findByFirstNamePaginated(String firstName, Pageable pageable);

	Page<Author> findByLastNamePaginated(String lastName, Pageable pageable);

	boolean doesTheEmailAlreadyExists(String email);

	void add(Author entity);

	void update(Long id, Author entity);
}
