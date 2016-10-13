package com.bestseller.service;

import java.util.List;

import com.bestseller.pojo.Brand;
import com.bestseller.pojo.query.BrandQuery;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	public Pagination getBrandList(Brand brand);
	
	public void saveBrand(Brand brand);
	
	public void deleteBrand(Integer id);
	
	public void batchDeleteBrand(Integer[] ids);
	
	public void updateBrand(Brand brand);
	
	public Brand getBrandById(Integer id);
	
	public List<Brand>  getBrandListByQuery(BrandQuery brandQuery);
}
