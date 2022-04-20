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

import com.claudionogueira.news.dto.AuthorDTO;
import com.claudionogueira.news.exceptions.BadRequestException;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.services.AuthorService;

@Controller
public class AuthorView {

	private final AuthorService service;

	public AuthorView(AuthorService service) {
		this.service = service;
	}

	@GetMapping(value = "/authors")
	public String getAllAuthors(Pageable pageable, Model model) {
		Page<AuthorDTO> authors = service.findAll(pageable);
		model.addAttribute("authors", authors);
		return "authors/all-authors";
	}

	@GetMapping(value = "/authors/add-author")
	public String getAddAuthor(Model model) {
		model.addAttribute("author", new AuthorDTO());
		return "authors/add-author";
	}

	@PostMapping(value = "/authors/add-author")
	public String addAuthor(@ModelAttribute("author") AuthorDTO dto, RedirectAttributes attributes) {
		try {
			service.add(dto);
		} catch (BadRequestException e) {
			attributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/authors/add-author";
		}
		return "redirect:/authors";
	}

	@GetMapping(value = "/authors/edit-author/{id}")
	public String getEditAuthor(@PathVariable String id, Model model) {
		try {
			service.findByIdDTO(id);
		} catch (ObjectNotFoundException e) {
			return "redirect:/authors";
		}
		model.addAttribute("author", service.findByIdDTO(id));
		return "authors/edit-author";
	}

	@PostMapping(value = "/authors/edit-author/{id}")
	public String editAuthor(@PathVariable String id, @ModelAttribute("author") AuthorDTO dto,
			RedirectAttributes attributes) {
		try {
			service.update(id, dto);
		} catch (BadRequestException e) {
			attributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/authors/edit-author/{id}";
		}
		return "redirect:/authors";
	}

	@GetMapping(value = "/authors/author-details/{id}")
	public String getAuthorDetails(@PathVariable String id, Model model) {
		try {
			service.findByIdDTO(id);
		} catch (ObjectNotFoundException e) {
			return "redirect:/authors";
		}
		model.addAttribute("author", service.findByIdDTO(id));
		return "authors/details-author";
	}
}
