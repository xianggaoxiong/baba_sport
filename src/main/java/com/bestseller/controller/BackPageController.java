package com.bestseller.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackPageController {
	
	@RequestMapping("/back/{page}")
	public String showBackPage(@PathVariable("page") String page){
		return page;
	}
	
	@RequestMapping("/back/frame/{page}")
	public String showBackPage2(@PathVariable("page") String page){
		return "frame/"+page;
	}
}
