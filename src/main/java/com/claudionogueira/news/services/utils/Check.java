package com.claudionogueira.news.services.utils;

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
		if (id == null) {
			throw new BadRequestException("Author ID is mandatory and cannot be null");
		}

		char[] digits = id.toCharArray();
		for (char digit : digits) {
			if (!Character.isDigit(digit)) {
				throw new BadRequestException("Author ID has to be a numeric value.");
			}
		}

		return Long.parseLong(id);
	}

	public static long categoryID(String id) {
		if (id == null) {
			throw new BadRequestException("Category ID is mandatory and cannot be null");
		}

		char[] digits = id.toCharArray();
		for (char digit : digits) {
			if (!Character.isDigit(digit)) {
				throw new BadRequestException("Category ID has to be a numeric value.");
			}
		}

		return Long.parseLong(id);
	}

	public static NewsDTO newsDTO(NewsDTO dto) {
		Check.newsID(dto.getId().toString());

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
}
