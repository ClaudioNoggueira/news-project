package com.claudionogueira.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudionogueira.news.models.CategoryNews;
import com.claudionogueira.news.models.CategoryNewsPK;

@Repository
public interface CategoryNewsRepo extends JpaRepository<CategoryNews, CategoryNewsPK> {

}
