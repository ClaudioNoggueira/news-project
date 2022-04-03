package com.claudionogueira.news.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudionogueira.news.models.News;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {

	Page<News> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
