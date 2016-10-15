package com.bestseller.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.bestseller.pojo.Brand;
import com.bestseller.service.BrandService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("/back")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/brand/list.do")
	public String showList(String name,Integer isDisplay,Integer pageNo,Map<String,Object> map){
		StringBuilder builder=new StringBuilder();
		Brand brand=new Brand();
		if(StringUtils.isNotBlank(name)){
			brand.setName(name);
			builder.append("name=").append(name);
		}
		
		if(isDisplay!=null){
			brand.setIsDisplay(isDisplay);
			builder.append("&isDisplay=").append(isDisplay);
		}else{
			brand.setIsDisplay(1);
			builder.append("&isDisplay=").append(1);
		}
		brand.setPageNo(Pagination.cpn(pageNo));
		brand.setPageSize(5);
		Pagination pagination=brandService.getBrandList(brand);
		pagination.pageView("/back/brand/list.do", builder.toString());
		map.put("pagination", pagination);
		map.put("name",name);
		map.put("isDisplay", isDisplay);
		
		return "brand/list";
	}
	
	@RequestMapping("/brand/add.do")
	public String toAddPage(){
		return "brand/add";
	}
	
	
	@RequestMapping("/brand/save.do")
	public String addBrand(Brand brand){
		brandService.saveBrand(brand);
		return "redirect:/back/brand/list.do";
	}
	
	//单条删除
	@RequestMapping("/brand/{id}/delete.do")
	public String deleteBrand(@PathVariable Integer id,String name,Integer isDisplay,ModelMap modelMap){
		if(StringUtils.isNotBlank(name)){
			modelMap.addAttribute("name", name);
		}
		if(isDisplay!=null){
			modelMap.addAttribute("isDisplay", isDisplay);
		}
		brandService.deleteBrand(id);
		return "redirect:/back/brand/list.do";
	}
	
	
	//批量删除
	@RequestMapping("/brand/deletes.do")
	public String deleteBatchBrand(Integer[] ids,String name,Integer isDisplay,ModelMap modelMap){
		if(StringUtils.isNotBlank(name)){
			modelMap.addAttribute("name", name);
		}
		if(isDisplay!=null){
			modelMap.addAttribute("isDisplay", isDisplay);
		}
		brandService.batchDeleteBrand(ids);
		return "redirect:/back/brand/list.do";
	}
	
	//修改页面
	@RequestMapping("/brand/toEdit.do")
	public String editPage(Integer id,Map<String,Object> map){
		Brand brand = brandService.getBrandById(id);
		map.put("brand", brand);
		return "brand/edit";
	}
	
	
	@RequestMapping("/brand/editBrand.do")
	public String editBrand(Brand brand){
		brandService.updateBrand(brand);
		return "redirect:/back/brand/list.do";
	}
	
	/*@ModelAttribute
	public void pre(String name,Integer isDisplay){
		System.out.println(name+":"+isDisplay);
	}*/
}
