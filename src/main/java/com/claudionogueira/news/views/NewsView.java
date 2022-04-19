package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.claudionogueira.news.dto.NewsDTO;
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

	@GetMapping(value = "/news")
	public String getNews(Pageable pageable, Model model) {
		Page<NewsDTO> news = service.findAll(pageable);
		model.addAttribute("allNews", news);
		return "news/all-news";
	}
}
