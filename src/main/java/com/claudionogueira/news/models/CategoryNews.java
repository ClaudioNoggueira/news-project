package com.claudionogueira.news.models;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CATEGORY_NEWS")
public class CategoryNews implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CategoryNewsPK id = new CategoryNewsPK();

	public CategoryNews() {

	}

	public CategoryNews(CategoryNewsPK id) {
		this.id = id;
	}

	public CategoryNewsPK getId() {
		return id;
	}

	public void setId(CategoryNewsPK id) {
		this.id = id;
	}

	public void setCategory(Category category) {
		id.setCategory(category);
	}

	public void setNews(News news) {
		id.setNews(news);
	}
}
