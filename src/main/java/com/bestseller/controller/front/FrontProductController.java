package com.bestseller.controller.front;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import com.bestseller.pojo.Product;
import com.bestseller.pojo.Sku;
import com.bestseller.pojo.Type;
import com.bestseller.pojo.query.BrandQuery;
import com.bestseller.pojo.query.FeatureQuery;
import com.bestseller.pojo.query.ProductQuery;
import com.bestseller.pojo.query.TypeQuery;
import com.bestseller.service.BrandService;
import com.bestseller.service.FeatureService;
import com.bestseller.service.ProductService;
import com.bestseller.service.SkuService;
import com.bestseller.service.TypeService;

import cn.itcast.common.page.Pagination;

@Controller
@RequestMapping("/front")
public class FrontProductController {
	@Autowired
	private BrandService brandService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/product/list.shtml")
	public String showIndex(Integer pageNo,Integer brandId,String brandName,Integer typeId,String typeName,ModelMap model){
		//分页查询
		StringBuilder params=new StringBuilder();
		ProductQuery productQuery=new ProductQuery();
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		productQuery.orderbyId(false);
		
		//页面显示查询条件
		Map<String,Object> queryShow=new LinkedHashMap<String,Object>();
		boolean flag=false;
		//加载品牌
		if(StringUtils.isBlank(brandName)){
			BrandQuery brandQuery=new BrandQuery();
			brandQuery.setFields("id,name");
			brandQuery.setIsDisplay(1);
			List<Brand> brands = brandService.getBrandListByQuery(brandQuery);
			model.put("brands", brands);
		}else{
			productQuery.setBrandId(brandId);
			flag=true;
			model.put("brandName", brandName);
			model.put("brandId", brandId);
			queryShow.put("品牌:", brandName);
			params.append("&brandName=").append(brandName).append("&brandId=").append(brandId);
		}
		
		//加载类型
		if(StringUtils.isBlank(typeName)){
			TypeQuery typeQuery=new TypeQuery();
			typeQuery.setIsDisplay(1);
			typeQuery.setParentId(1);
			List<Type> types = typeService.getTypeList(typeQuery);
			model.put("types", types);
		}else{
			productQuery.setTypeId(typeId);
			flag=true;
			model.put("typeName", typeName);
			model.put("typeId", typeId);
			queryShow.put("类型:", typeName);
			params.append("&typeName=").append(typeName).append("&typeId=").append(typeId);
		}
		
		//加载属性
		FeatureQuery featureQuery=new FeatureQuery();
		featureQuery.setIsDel(1);
		List<Feature> features = featureService.getFeatureList(featureQuery);
		model.put("features", features);
		model.put("flag", flag);
		model.put("queryShow", queryShow);
		
		Pagination pagination = productService.getProductListWithPage(productQuery);
		String url="/front/product/list.shtml";
		pagination.pageView(url, params.toString());
		model.put("pagination", pagination);
		
		return "product/product";
	}
	
	//商品详情页
	@RequestMapping("/product/detail.shtml")
	public String showProductDetail(Integer productId,ModelMap model){
		Product product = productService.getProductByKey(productId);
		model.put("product", product);
		List<Sku> skus = skuService.getStock(productId);
		model.put("skus", skus);
		List<Color> colors=new ArrayList<>();
		for(Sku sku:skus){
			if(!colors.contains(sku.getColor())){
				colors.add(sku.getColor());
			}
		}
		model.put("colors", colors);
		
		return "product/productDetail";
	}
}
