package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.services.CategoryService;

@Controller
public class CategoryView {

	private final CategoryService service;

	public CategoryView(CategoryService service) {
		this.service = service;
	}
	
	@GetMapping(value = "/categories")
	public ModelAndView getCategories(Pageable pageable) {
		ModelAndView mv = new ModelAndView("category-index");
		Page<CategoryDTO> categories = service.findAll(pageable);
		mv.addObject("categories", categories);
		return mv;
	}
}
