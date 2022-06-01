package com.claudionogueira.news.services.utils;

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
}
