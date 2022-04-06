package com.claudionogueira.news.services.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
	private static DateTimeFormatter dateFormatter;

	public DateValidator(DateTimeFormatter dateFormatter) {
		DateValidator.dateFormatter = dateFormatter;
	}

	public static boolean isValid(String dateStr) {
		try {
			LocalDate.parse(dateStr, dateFormatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
}
