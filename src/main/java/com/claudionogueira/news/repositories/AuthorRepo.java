package com.claudionogueira.news.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.claudionogueira.news.models.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

	@Query(value = "SELECT DISTINCT * FROM TB_AUTHOR WHERE email LIKE %?1%", countQuery = "SELECT count(*) FROM TB_AUTHOR", nativeQuery = true)
	Page<Author> findByEmailPaginated(String email, Pageable pageable);

	Page<Author> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

	Page<Author> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

	Author findByEmail(String email);
}
