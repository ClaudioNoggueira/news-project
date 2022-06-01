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
	private String error;
	private String message;
	private String path;

	private List<Field> fields = new ArrayList<>();

	public StandardError() {

	}

	public StandardError(OffsetDateTime timestamp, Integer status, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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

	public List<Field> getFields() {
		return fields;
	}

	public void addField(String name, String message) {
		this.getFields().add(new Field(name, message));
	}

	public void removeField(String name, String message) {
		this.getFields().remove(new Field(name, message));
	}

	public static class Field {
		private String name;
		private String message;

		public Field() {

		}

		public Field(String name, String message) {
			super();
			this.name = name;
			this.message = message;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
