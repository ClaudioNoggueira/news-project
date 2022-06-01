package com.claudionogueira.news.exceptions;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private OffsetDateTime timestamp;
	private Integer status;
	private String title;
	private String message;
	private String path;

	private List<Error> errors = new ArrayList<>();

	public StandardError() {

	}

	public StandardError(OffsetDateTime timestamp, Integer status, String title, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.title = title;
		this.message = message;
		this.path = path;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void addField(String name, String message) {
		this.getErrors().add(new Error(name, message));
	}

	public void removeField(String name, String message) {
		this.getErrors().remove(new Error(name, message));
	}

	public static class Error {
		private String field;
		private String message;

		public Error() {

		}

		public Error(String field, String message) {
			super();
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
