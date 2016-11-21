package com.bestseller.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestseller.pojo.Buyer;
import com.bestseller.service.BuyerService;
import com.bestseller.service.staticPage.StaticPageService;

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
	@Autowired
	private StaticPageService staticPageService;
	@RequestMapping("/test/freemarker.do")
	public String test02(){
		Map<String,Object> map=new HashMap<>();
		map.put("hello", "世界你好!");
		staticPageService.productStaticized(map, 123456);
		System.out.println("生成完成！");
		return "";
	}
	
	@Autowired
	private BuyerService buyerService;
	
	@RequestMapping("/test/jsonp.do")
	@ResponseBody
	public Object testJsonp(String callback){
		List<Buyer> buyers = buyerService.getBuyerList(null);
		Buyer buyer=buyers.get(0);
		MappingJacksonValue mjv=new MappingJacksonValue(buyer);
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
