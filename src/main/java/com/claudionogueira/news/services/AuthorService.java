package com.claudionogueira.news.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.exceptions.BadRequestException;
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

	@Override
	public Page<Author> findByFirstNamePaginated(String firstName, Pageable pageable) {
		return repo.findByFirstNameContainingIgnoreCase(firstName, pageable);
	}

	@Override
	public Page<Author> findByLastNamePaginated(String lastName, Pageable pageable) {
		return repo.findByLastNameContainingIgnoreCase(lastName, pageable);
	}

	@Override
	public boolean doesTheEmailAlreadyExists(String email) {
		Author obj = repo.findByEmail(email);
		if (obj == null) {
			return false;
		}
		return true;
	}

	@Override
	public void add(Author entity) {
		if (this.doesTheEmailAlreadyExists(entity.getEmail())) {
			throw new BadRequestException("Email '" + entity.getEmail() + "' already in use.");
		}
		repo.save(entity);
	}

	@Override
	public void update(Long id, Author entity) {
		if (this.doesTheEmailAlreadyExists(entity.getEmail())) {
			throw new BadRequestException("Email '" + entity.getEmail() + "' already in use.");
		}

		Author objToBeUpdated = this.findById(id);
		if (!entity.getFirstName().equals("") && entity.getFirstName() != null)
			objToBeUpdated.setFirstName(entity.getFirstName());

		if (!entity.getLastName().equals("") && entity.getLastName() != null)
			objToBeUpdated.setLastName(entity.getLastName());

		if (!entity.getEmail().equals("") && entity.getEmail() != null)
			objToBeUpdated.setEmail(entity.getEmail());

		repo.save(objToBeUpdated);
	}
}
