package com.claudionogueira.news.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.dto.updates.AuthorUpdate;
import com.claudionogueira.news.exceptions.DomainException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.interfaces.IAuthorService;
import com.claudionogueira.news.services.utils.AuthorMapper;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorMapper mapper;

	private final AuthorRepo authorRepo;

	private final FindAuthor findAuthor;

	public AuthorService(AuthorMapper mapper, AuthorRepo authorRepo, FindAuthor findAuthor) {
		super();
		this.mapper = mapper;
		this.authorRepo = authorRepo;
		this.findAuthor = findAuthor;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AuthorDTO> findAll(Pageable pageable) {
		Page<Author> page = authorRepo.findAll(pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public AuthorDTO findByIdDTO(String id) {
		Author author = findAuthor.byID(id);
		return mapper.fromEntityToDTO(author);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AuthorDTO> findByEmailPaginated(String email, Pageable pageable) {
		Page<Author> page = authorRepo.findByEmailPaginated(email, pageable);
		return mapper.fromPageEntityToPageDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AuthorDTO> findByFullNamePageable(String fullName, Pageable pageable) {
		Set<Author> set = new HashSet<>();

		// Page for both queries
		Page<Author> page = Page.empty();

		page = authorRepo.findByFirstNameContainingIgnoreCase(fullName, pageable);
		for (Author author : page) {
			set.add(author);
		}

		page = Page.empty();

		page = authorRepo.findByLastNameContainingIgnoreCase(fullName, pageable);
		for (Author author : page) {
			set.add(author);
		}

		// Convert set to list while converting Author to AuthorDTO
		List<AuthorDTO> list = new ArrayList<>(
				set.stream().map(author -> mapper.fromEntityToDTO(author)).collect(Collectors.toList()));

		return new PageImpl<AuthorDTO>(list);
	}

	@Override
	public boolean emailIsAvailable(String email, Author entity) {
		// Check if the email is already taken by a author and then check if the owner
		// is NOT the same as the author to be added/updated
		boolean emailIsTaken = authorRepo.findByEmail(email).stream()
				.anyMatch(existingAuthor -> !existingAuthor.equals(entity));

		if (emailIsTaken) {
			throw new DomainException("Email is already in use by someone else.");
		}

		return true;
	}

	@Override
	public void add(AuthorInput input) {
		Author author = mapper.fromInputToEntity(input);
		if (this.emailIsAvailable(input.getEmail(), author)) {
			authorRepo.save(author);
		}
	}

	@Override
	public void update(String id, AuthorUpdate update) {
		Author objToBeUpdated = findAuthor.byID(id);

		if (!update.getEmail().equals(objToBeUpdated.getEmail())) {
			if (this.emailIsAvailable(update.getEmail(), objToBeUpdated)) {
				objToBeUpdated.setEmail(update.getEmail());
			}
		}

		if (update.getFirstName() != null && !update.getFirstName().equals(""))
			objToBeUpdated.setFirstName(update.getFirstName());

		if (update.getLastName() != null && !update.getLastName().equals(""))
			objToBeUpdated.setLastName(update.getLastName());

		authorRepo.save(objToBeUpdated);
	}
}
