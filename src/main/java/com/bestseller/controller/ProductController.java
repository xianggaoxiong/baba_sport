package com.bestseller.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bestseller.pojo.Brand;
import com.bestseller.pojo.Color;
import com.bestseller.pojo.Feature;
import com.bestseller.pojo.Img;
import com.bestseller.pojo.Product;
import com.bestseller.pojo.Type;
import com.bestseller.pojo.query.BrandQuery;
import com.bestseller.pojo.query.ColorQuery;
import com.bestseller.pojo.query.FeatureQuery;
import com.bestseller.pojo.query.ProductQuery;
import com.bestseller.pojo.query.TypeQuery;
import com.bestseller.service.BrandService;
import com.bestseller.service.ColorService;
import com.bestseller.service.FeatureService;
import com.bestseller.service.ProductService;
import com.bestseller.service.TypeService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("/back")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ColorService colorService;
	
	@RequestMapping("/product/list.do")
	public String showList(Integer pageNo,String name,Integer brandId,Integer isShow,Map<String,Object> map){
		BrandQuery bq=new BrandQuery();
		bq.setFields("id,name");
		bq.setIsDisplay(1);
		List<Brand> brands = brandService.getBrandListByQuery(bq);
		map.put("brands", brands);
		
		//查询条件的拼接及回显
		StringBuilder params=new StringBuilder();
		ProductQuery productQuery=new ProductQuery();
		if(StringUtils.isNotBlank(name)){
			productQuery.setName(name);
			productQuery.setNameLike(true);
			params.append("&name=").append(name);
			map.put("name",name);
		}
		
		if(brandId!=null){
			productQuery.setBrandId(brandId);
			params.append("&brandId=").append(brandId);
			map.put("brandId",brandId);
		}
		
		if(isShow!=null){
			productQuery.setIsShow(isShow);
			params.append("&isShow=").append(isShow);
			map.put("isShow",isShow);
		}else{
			productQuery.setIsShow(0);
			params.append("&isShow=").append(0);
			map.put("isShow",0);
		}
		
		//分页查询
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(5);
		productQuery.orderbyBrandId(false);
		
		Pagination pagination = productService.getProductListWithPage(productQuery);
		String url="/back/product/list.do";
		pagination.pageView(url, params.toString());
		map.put("pagination", pagination);
		return "product/list";
	}
	
	//去添加页面
	@RequestMapping("/product/toAdd.do")
	public String toAdd(Map<String,Object> map){
		//加载商品类型
		TypeQuery typeQuery=new TypeQuery();
		typeQuery.setFields("id,name");
		typeQuery.setIsDisplay(1);
		typeQuery.setParentId(0);
		List<Type> types= typeService.getTypeList(typeQuery);
		map.put("types", types);
		//加载商品品牌
		BrandQuery bq=new BrandQuery();
		bq.setFields("id,name");
		bq.setIsDisplay(1);
		List<Brand> brands = brandService.getBrandListByQuery(bq);
		map.put("brands", brands);
		//加载商品属性
		FeatureQuery featureQuery=new FeatureQuery();
		List<Feature> features = featureService.getFeatureList(featureQuery);
		map.put("features",features);
		//加载颜色
		ColorQuery colorQuery=new ColorQuery();
		colorQuery.setParentId(0);
		List<Color> colors = colorService.getColorList(colorQuery);
		map.put("colors",colors);
		return "product/add";
	}
	
	//添加商品
	@RequestMapping("/product/save.do")
	public String addProduct(Product product,Img img){
		product.setImg(img);
		productService.addProduct(product);
		return "redirect:/back/product/list.do";
	}
	
	//上架
	@RequestMapping("/product/isShow.do")
	public String isShow(Integer[] ids,Integer pageNo,Integer brandId,String name,Integer isShow,ModelMap model){
		//实例化商品
		Product product=new Product();
		product.setIsShow(1);
		if(ids!=null&&ids.length>0){
			for(Integer id:ids){
				product.setId(id);
				productService.updateProductByKey(product);
			}
		}
		//TODO 静态化
		
		
		//判断
		if(pageNo!=null){
			model.put("pageNo", pageNo);
		}
		if(brandId!=null){
			model.put("brandId", brandId);
		}
		if(StringUtils.isNotBlank(name)){
			model.put("name", name);
		}
		if(null!=isShow){
			model.put("isShow", isShow);
		}
		
		return "redirect:/back/product/list.do";
	}
}
