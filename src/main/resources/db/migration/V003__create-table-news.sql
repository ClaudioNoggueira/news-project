CREATE TABLE news(
	id SERIAL,
	author_id BIGINT NOT NULL,
	title VARCHAR(60) NOT NULL,
	content VARCHAR NOT NULL,
	date DATE NOT NULL,
	
	PRIMARY KEY(id)
);

ALTER TABLE news ADD CONSTRAINT fk_news_author
	FOREIGN KEY(author_id) REFERENCES author(id);
	