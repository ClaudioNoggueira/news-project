package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.services.AuthorService;

@Controller
public class AuthorView {

	private final AuthorService service;

	public AuthorView(AuthorService service) {
		this.service = service;
	}
	
	@GetMapping(value = "/authors")
	public ModelAndView getAuthors(Pageable pageable){
		ModelAndView mv = new ModelAndView("author-index");
		Page<AuthorDTO> authors = service.findAll(pageable);
		mv.addObject("authors", authors);
		return mv;
	}
}
