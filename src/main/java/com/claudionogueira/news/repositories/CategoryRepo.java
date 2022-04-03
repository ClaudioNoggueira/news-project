package com.claudionogueira.news.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claudionogueira.news.models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

	Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	Category findByNameIgnoreCase(String name);
}
