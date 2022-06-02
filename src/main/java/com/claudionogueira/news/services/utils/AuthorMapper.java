package com.claudionogueira.news.services.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.dto.inputs.AuthorInput;
import com.claudionogueira.news.models.Author;
import com.claudionogueira.news.models.Category;
import com.claudionogueira.news.services.FindCategory;

@Component
public class AuthorMapper {

	private final ModelMapper mapper;
	private final FindCategory findCategory;

	public AuthorMapper(ModelMapper mapper, FindCategory findCategory) {
		super();
		this.mapper = mapper;
		this.findCategory = findCategory;
	}

	public AuthorDTO fromEntityToDTO(Author entity) {
		AuthorDTO dto = mapper.map(entity, AuthorDTO.class);

		entity.getAuthorNews().forEach(news -> {
			NewsDTO newsDTO = new NewsDTO(null, news.getTitle(), news.getContent(), news.getDate(), null);

			news.getCategories().forEach(categoryNews -> {
				String category_id = categoryNews.getId().getCategory().getId().toString();
				Category category = findCategory.byID(category_id);
				newsDTO.addCategory(category.getName());
			});

			dto.addNews(newsDTO.getTitle(), newsDTO.getContent(), newsDTO.getDate());

			// Map NewsDTO.Category with Author.News.categories
			newsDTO.getCategories().forEach(category -> {
				dto.getNews().forEach(authorNews -> {
					authorNews.addCategory(category.getName());
				});
			});
		});

		return dto;
	}

	public Author fromInputToEntity(AuthorInput input) {
		return mapper.map(input, Author.class);
	}

	public Page<AuthorDTO> fromPageEntityToPageDTO(Page<Author> page) {
		List<AuthorDTO> list = page.stream().map(this::fromEntityToDTO).collect(Collectors.toList());
		return new PageImpl<AuthorDTO>(list);
	}
}
