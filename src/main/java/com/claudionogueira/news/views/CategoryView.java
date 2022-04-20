package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.claudionogueira.news.dto.CategoryDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.services.CategoryService;

@Controller
public class CategoryView {

	private final CategoryService service;

	public CategoryView(CategoryService service) {
		this.service = service;
	}

	@GetMapping(value = "/categories")
	public String getCategories(Model model, Pageable pageable) {
		Page<CategoryDTO> categories = service.findAll(pageable);
		model.addAttribute("categories", categories);
		return "categories/all-categories";
	}

	@GetMapping(value = "/categories/add-category")
	public String getAddCategory(Model model) {
		model.addAttribute("category", new CategoryDTO());
		return "categories/add-category";
	}

	@PostMapping(value = "/categories/add-category")
	public String addCategory(@ModelAttribute("category") CategoryDTO dto, RedirectAttributes attributes) {
		try {
			service.add(dto);
		} catch (BadRequestException e) {
			attributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/categories/add-category";
		}
		return "redirect:/categories";
	}

	@GetMapping(value = "/categories/category-details/{id}")
	public String getDetailsCategory(@PathVariable String id, Model model) {
		try {
			service.findByIdDTO(id);
		} catch (ObjectNotFoundException e) {
			return "redirect:/categories";
		}
		model.addAttribute("category", service.findByIdDTO(id));
		return "categories/details-category";
	}
}
