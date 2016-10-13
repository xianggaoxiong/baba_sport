package com.bestseller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.mapper.BrandMapper;
import com.bestseller.pojo.Brand;
import com.bestseller.pojo.query.BrandQuery;
import com.bestseller.service.BrandService;

import cn.itcast.common.page.Pagination;
@Service
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private BrandMapper brandMapper;
	@Override
	public Pagination getBrandList(Brand brand) {
		Pagination page=new Pagination(brand.getPageNo(), brand.getPageSize(), brandMapper.getBrandCount(brand));
		page.setList(brandMapper.getBrandList(brand));
		return page;
	}
	
	@Transactional
	@Override
	public void saveBrand(Brand brand) {
		brandMapper.addBrand(brand);
	}
	
	@Transactional
	@Override
	public void deleteBrand(Integer id) {
		brandMapper.deleteBrand(id);
	}

	@Transactional
	@Override
	public void batchDeleteBrand(Integer[] ids) {
		brandMapper.batchDeleteBrand(ids);
		
	}

	@Transactional
	@Override
	public void updateBrand(Brand brand) {
		brandMapper.updateBrand(brand);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Brand getBrandById(Integer id) {
		return brandMapper.getBrandById(id);
	}

	@Override
	public List<Brand> getBrandListByQuery(BrandQuery brandQuery) {
		List<Brand> brands = brandMapper.getBrandListByQuery(brandQuery);
		return brands;
	}
	
	
}
