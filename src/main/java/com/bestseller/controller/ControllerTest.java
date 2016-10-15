package com.bestseller.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerTest{
	@RequestMapping(value="/test/index",method=RequestMethod.POST)
	public String test01(String name,Date birth){
		System.out.println(name);
		return "";
	}

	/*
	@InitBinder
	public void initBinder(HttpServletRequest request,ServletRequestDataBinder binder){
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}*/
}
