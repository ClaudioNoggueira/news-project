package com.claudionogueira.news.views;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.claudionogueira.news.dto.NewsDTO;
import com.claudionogueira.news.services.NewsService;

@Controller
public class NewsView {

	private final NewsService service;

	public NewsView(NewsService service) {
		this.service = service;
	}

	@GetMapping(value = "/news")
	public ModelAndView getNews(Pageable pageable) {
		ModelAndView mv = new ModelAndView("news/all-news");
		Page<NewsDTO> news = service.findAll(pageable);
		mv.addObject("allNews", news);
		return mv;
	}
}
