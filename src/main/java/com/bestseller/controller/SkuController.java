package com.bestseller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestseller.pojo.Sku;
import com.bestseller.pojo.query.SkuQuery;
import com.bestseller.service.SkuService;

@Controller
@RequestMapping("/back")
public class SkuController {
	@Autowired
	private SkuService skuService;
	@RequestMapping("/sku/list.do")
	public String skuList(Integer productId,String productNo,ModelMap model){
		model.put("productNo", productNo);
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.setProductId(productId);
		List<Sku> skus= skuService.getSkuList(skuQuery);
		model.put("skus", skus);
		return "sku/list";
	}
	
	@RequestMapping(value="/sku/add.do",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addSku(Sku sku,HttpServletResponse response){
		skuService.updateSkuByKey(sku);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("message", "保存成功!");
		return map;
	}
}
