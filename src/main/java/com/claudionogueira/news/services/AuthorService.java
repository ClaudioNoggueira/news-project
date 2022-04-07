package com.claudionogueira.news.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.AuthorNewsDTO;
import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.News;
import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.repositories.CategoryRepo;
import com.claudionogueira.news.services.interfaces.IAuthorService;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorRepo authorRepo;

	private final CategoryRepo categoryRepo;

	public AuthorService(AuthorRepo repo, CategoryRepo categoryRepo) {
		this.authorRepo = repo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Page<AuthorDTO> findAll(Pageable pageable) {
		Page<Author> page = authorRepo.findAll(pageable);
		return this.convertPageToDTO(page);
	}

	@Override
	public Author findById(Long id) {
		return authorRepo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Author with ID: '" + id + "' not found."));
	}

	@Override
	public AuthorDTO findByIdDTO(Long id) {
		Author author = this.findById(id);
		return this.convertAuthorToDTO(author);
	}

	@Override
	public Page<AuthorDTO> findByEmailPaginated(String email, Pageable pageable) {
		Page<Author> page = authorRepo.findByEmailPaginated(email, pageable);
		return this.convertPageToDTO(page);
	}

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
	public Page<Author> findByFirstNamePaginated(String firstName, Pageable pageable) {
		return authorRepo.findByFirstNameContainingIgnoreCase(firstName, pageable);
	}

	@Override
	public Page<Author> findByLastNamePaginated(String lastName, Pageable pageable) {
		return authorRepo.findByLastNameContainingIgnoreCase(lastName, pageable);
	}

	@Override
	public boolean doesTheEmailAlreadyExists(String email) {
		Author obj = authorRepo.findByEmail(email);
		if (obj == null)
			return false;

		return true;
	}

	@Override
	public void add(Author entity) {
		if (this.doesTheEmailAlreadyExists(entity.getEmail()))
			throw new BadRequestException("Email '" + entity.getEmail() + "' already in use.");

		authorRepo.save(entity);
	}

	@Override
	public void update(Long id, Author entity) {
		if (this.doesTheEmailAlreadyExists(entity.getEmail()))
			throw new BadRequestException("Email '" + entity.getEmail() + "' already in use.");

		Author objToBeUpdated = this.findById(id);
		if (!entity.getFirstName().equals("") && entity.getFirstName() != null)
			objToBeUpdated.setFirstName(entity.getFirstName());

		if (!entity.getLastName().equals("") && entity.getLastName() != null)
			objToBeUpdated.setLastName(entity.getLastName());

		if (!entity.getEmail().equals("") && entity.getEmail() != null)
			objToBeUpdated.setEmail(entity.getEmail());

		authorRepo.save(objToBeUpdated);
	}

	@Override
	public Page<AuthorDTO> convertPageToDTO(Page<Author> page) {
		List<AuthorDTO> list = new ArrayList<>();

		for (Author author : page) {
			list.add(this.convertAuthorToDTO(author));
		}

		return new PageImpl<AuthorDTO>(list);
	}

	@Override
	public AuthorDTO convertAuthorToDTO(Author author) {
		AuthorDTO authorDTO = new AuthorDTO(author);

		for (News news : author.getAuthorNews()) {
			NewsDTO newsDTO = new NewsDTO(news);

			for (CategoryNews categoryNews : news.getCategories()) {
				long category_id = categoryNews.getId().getCategory().getId();

				Category category = categoryRepo.findById(category_id).orElseThrow(
						() -> new ObjectNotFoundException("Category with ID: '" + category_id + "' not found."));

				newsDTO.getCategories().add(new CategoryDTO(category));
			}
			authorDTO.getNews().add(new AuthorNewsDTO(newsDTO));
		}
		return authorDTO;
	}
}
