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
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.dto.updates.AuthorUpdate;
import com.claudionogueira.news.exceptions.DomainException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.interfaces.IAuthorService;
import com.claudionogueira.news.services.utils.Check;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorRepo authorRepo;

	private final CategoryService categoryService;

	public AuthorService(AuthorRepo authorRepo, CategoryService categoryService) {
		super();
		this.authorRepo = authorRepo;
		this.categoryService = categoryService;
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AuthorDTO> findAll(Pageable pageable) {
		Page<Author> page = authorRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Transactional(readOnly = true)
	@Override
	public Author findById(String id) {
		long author_id = Check.authorID(id);
		return authorRepo.findById(author_id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + author_id + "' not found."));
	}

	@Transactional(readOnly = true)
	@Override
	public AuthorDTO findByIdDTO(String id) {
		Author author = this.findById(id);
		return this.convertAuthorToDTO(author);
	}

	@Transactional(readOnly = true)
	@Override
	public AuthorDTO findByEmail(String email) {
		Author author = authorRepo.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Author with e-mail: '" + email + "' not found."));
		return this.convertAuthorToDTO(author);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<AuthorDTO> findByEmailPaginated(String email, Pageable pageable) {
		Page<Author> page = authorRepo.findByEmailPaginated(email, pageable);
		return this.convertPageToDTO(page);
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

		// Convert set to list
		List<Author> list = new ArrayList<>(set);

		// Create page out of list and convert to page of AuthorDTO
		return this.convertPageToDTO(new PageImpl<>(list));
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
		Author author = new Author(null, input.getFirstName(), input.getLastName(), input.getEmail());

		if (this.emailIsAvailable(input.getEmail(), author)) {
			authorRepo.save(author);
		}
	}

	@Override
	public void update(String id, AuthorUpdate update) {
		Author objToBeUpdated = this.findById(id);

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

	@Override
	public Page<AuthorDTO> convertPageToDTO(Page<Author> page) {
		List<AuthorDTO> list = page.stream().map(this::convertAuthorToDTO).collect(Collectors.toList());
		return new PageImpl<AuthorDTO>(list);
	}

	@Override
	public AuthorDTO convertAuthorToDTO(Author author) {
		AuthorDTO authorDTO = new AuthorDTO(author);

		for (News news : author.getAuthorNews()) {
			NewsDTO newsDTO = new NewsDTO(news);

			for (CategoryNews categoryNews : news.getCategories()) {
				long category_id = categoryNews.getId().getCategory().getId();

				Category category = categoryService.findById(String.valueOf(category_id));

				newsDTO.addCategory(category.getName());
			}

			authorDTO.addNews(newsDTO.getTitle(), newsDTO.getContent(), newsDTO.getDate());

			// Map NewsDTO.Category with Author.News.categories
			for (NewsDTO.Category ndc : newsDTO.getCategories()) {
				authorDTO.getNews().forEach(authorNews -> authorNews.addCategory(ndc.getName()));
			}
		}
		return authorDTO;
	}
}
