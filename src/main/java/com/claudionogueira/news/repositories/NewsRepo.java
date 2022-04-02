package com.claudionogueira.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudionogueira.news.models.News;

@Repository
public interface NewsRepo extends JpaRepository<News, Long>{

}
