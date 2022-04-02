package com.claudionogueira.news.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CATEGORY_NEWS")
public class CategoryNews {

	@EmbeddedId
	private CategoryNewsPK id = new CategoryNewsPK();
	
	public CategoryNews() {
		
	}

	public CategoryNews(CategoryNewsPK id) {
		super();
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
