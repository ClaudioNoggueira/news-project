package com.claudionogueira.news.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.dto.updates.AuthorUpdate;
import com.claudionogueira.news.models.Author;

public interface IAuthorService {

	Page<AuthorDTO> convertPageToDTO(Page<Author> page);

	AuthorDTO convertAuthorToDTO(Author author);

	// GET
	Page<AuthorDTO> findAll(Pageable pageable);

	Author findById(String id);

	AuthorDTO findByIdDTO(String id);

	AuthorDTO findByEmail(String email);

	Page<AuthorDTO> findByEmailPaginated(String email, Pageable pageable);

	Page<AuthorDTO> findByFullNamePageable(String fullName, Pageable pageable);

	boolean emailIsAvailable(String email, Author entity);

	// POST
	void add(AuthorInput input);

	// PUT
	void update(String id, AuthorUpdate update);
}
