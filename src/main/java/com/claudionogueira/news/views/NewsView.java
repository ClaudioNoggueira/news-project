package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.exceptions.ObjectNotFoundException;
import com.claudionogueira.news.services.NewsService;

@Controller
public class NewsView {

	private final NewsService service;

	public NewsView(NewsService service) {
		this.service = service;
	}

	@GetMapping(value = "/")
	public String index() {
		return "redirect:/news";
	}

	// GET ALL NEWS
	@GetMapping(value = "/news")
	public String getNews(Pageable pageable, Model model) {
		Page<NewsDTO> news = service.findAll(pageable);
		model.addAttribute("allNews", news);
		return "news/all-news";
	}
	
	// GET NEWS DETAILS
	@GetMapping(value = "/news/news-details/{id}")
	public String getDetailsNews(@PathVariable String id, Model model) {
		try {
			service.findByIdDTO(id);
		}catch(ObjectNotFoundException e) {
			return "redirect:/news";
		}
		model.addAttribute("news", service.findByIdDTO(id));
		return "news/details-news";
	}
}
