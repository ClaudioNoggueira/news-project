package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.models.Author;

public interface IAuthorService {

	Page<AuthorDTO> convertPageToDTO(Page<Author> page);

	AuthorDTO convertAuthorToDTO(Author author);

	Page<AuthorDTO> findAll(Pageable pageable);

	Author findById(Long id);

	AuthorDTO findByIdDTO(Long id);

	Page<AuthorDTO> findByEmailPaginated(String email, Pageable pageable);

	Page<AuthorDTO> findByFullNamePageable(String fullName, Pageable pageable);

	Page<Author> findByFirstNamePaginated(String firstName, Pageable pageable);

	Page<Author> findByLastNamePaginated(String lastName, Pageable pageable);

	boolean doesTheEmailAlreadyExists(String email);

	void add(Author entity);

	void update(Long id, Author entity);
}
