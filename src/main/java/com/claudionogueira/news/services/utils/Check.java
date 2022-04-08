package com.claudionogueira.news.services.utils;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.exceptions.BadRequestException;

public class Check {

	// IDs
	public static long newsID(String id) {
		if (id == null) {
			throw new BadRequestException("News ID is mandatory and cannot be null");
		}

		char[] digits = id.toCharArray();
		for (char digit : digits) {
			if (!Character.isDigit(digit)) {
				throw new BadRequestException("News ID has to be a numeric value.");
			}
		}

		return Long.parseLong(id);
	}

	public static long authorID(String id) {
		try {
			char[] digits = id.toCharArray();
			for (char digit : digits) {
				if (!Character.isDigit(digit)) {
					throw new BadRequestException("Author ID has to be a numeric value.");
				}
			}

			return Long.parseLong(id);

		} catch (NullPointerException e) {
			throw new BadRequestException("Author ID is mandatory and cannot be null, empty or blank.");

		} catch (NumberFormatException e) {
			throw new BadRequestException("Author ID has to be a numeric value and cannot be null, empty or blank.");
		}
	}

	public static long categoryID(String id) {
		try {
			char[] digits = id.toCharArray();
			for (char digit : digits) {
				if (!Character.isDigit(digit)) {
					throw new BadRequestException("Category ID has to be a numeric value.");
				}
			}

			return Long.parseLong(id);

		} catch (NullPointerException e) {
			throw new BadRequestException("Category ID is mandatory and cannot be null, empty or blank.");

		} catch (NumberFormatException e) {
			throw new BadRequestException("Category ID has to be a numeric value and cannot be null, empty or blank.");
		}
	}

	public static NewsDTO newsDTO(NewsDTO dto) {

		Check.authorID(dto.getAuthor().getId().toString());

		if (dto.getTitle() == null || dto.getTitle().isEmpty() || dto.getTitle().isBlank()) {
			throw new BadRequestException("Title is mandatory and cannot be null, empty or blank.");
		}

		if (dto.getContent() == null || dto.getContent().isEmpty() || dto.getContent().isBlank()) {
			throw new BadRequestException("Content is mandatory and cannot be null, empty or blank.");
		}

		if (dto.getDate() == null || dto.getDate().toString().isEmpty() || dto.getDate().toString().isBlank()) {
			throw new BadRequestException("Date is mandatory and cannot be null, empty or blank.");
		}

		return dto;
	}

	public static AuthorDTO authorDTO(AuthorDTO dto) {
		if (dto.getEmail() == null || dto.getEmail().equals(""))
			throw new BadRequestException("Email is mandatory and cannot be null, empty or blank.");

		if (dto.getFirstName() == null || dto.getFirstName().equals(""))
			throw new BadRequestException("First name is mandatory and cannot be null, empty or blank.");

		if (dto.getLastName() == null || dto.getLastName().equals(""))
			throw new BadRequestException("Last name is mandatory and cannot be null, empty or blank.");

		return dto;
	}

	public static CategoryDTO categoryDTO(CategoryDTO dto) {
		if (dto.getName() == null || dto.getName().isEmpty() || dto.getName().isBlank())
			throw new BadRequestException("Category name is mandatory and cannot be null, empty or blank.");

		return dto;
	}
}
