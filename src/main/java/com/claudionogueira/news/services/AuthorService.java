package com.claudionogueira.news.services;

import org.springframework.stereotype.Service;

import com.claudionogueira.news.repositories.AuthorRepo;
import com.claudionogueira.news.services.interfaces.IAuthorService;

@Service
public class AuthorService implements IAuthorService {

	private final AuthorRepo repo;

	public AuthorService(AuthorRepo repo) {
		this.repo = repo;
	}

}
