package com.bestseller.mapper;

import java.util.List;

import com.bestseller.pojo.Brand;
import com.bestseller.pojo.query.BrandQuery;

public interface BrandMapper {
	public List<Brand> getBrandList(Brand brand);
	//获取总记录数
	public int getBrandCount(Brand brand);
	
	public void addBrand(Brand brand);
	
	public void deleteBrand(Integer id);
	
	public void batchDeleteBrand(Integer[] ids);
	
	public void updateBrand(Brand brand);
	
	public Brand getBrandById(Integer id);
	
	public List<Brand>  getBrandListByQuery(BrandQuery brandQuery);
}
