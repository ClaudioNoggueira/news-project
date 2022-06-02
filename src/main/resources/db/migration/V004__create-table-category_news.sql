CREATE TABLE category_news (
	category_id BIGINT NOT NULL,
	news_id BIGINT NOT NULL,
	
	FOREIGN KEY(category_id) REFERENCES category(id),

	FOREIGN KEY(news_id) REFERENCES news(id),

	CONSTRAINT pk_category_news PRIMARY KEY(category_id, news_id)
);