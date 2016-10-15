package com.test.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestseller.pojo.Brand;
import com.bestseller.pojo.TestPojo;
import com.bestseller.pojo.query.BrandQuery;
import com.bestseller.service.BrandService;
import com.bestseller.service.TestPojoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext*.xml"})
public class ServiceTest {
	@Autowired
	private TestPojoService testPojoService;
	
	@Autowired
	private BrandService brandService;
	
	@Test
	public void test01(){
		TestPojo testPojo=new TestPojo();
		testPojo.setName("周雨涛");
		testPojo.setBirthDay(new Date());
		testPojoService.savePojo(testPojo);
		
	}
	
	@Test
	public void test02(){
		BrandQuery brandQuery=new BrandQuery();
		brandQuery.setFields("id,name");
		brandQuery.orderById(false);
		brandQuery.setName("金");
		brandQuery.setNameLike(true);
		List<Brand> brands = brandService.getBrandListByQuery(brandQuery);
		for (Brand brand : brands) {
			System.out.println(brand);
		}
		
	}
}
