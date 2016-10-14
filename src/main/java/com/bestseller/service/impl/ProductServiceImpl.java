package com.bestseller.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.mapper.ProductMapper;
import com.bestseller.pojo.Img;
import com.bestseller.pojo.Product;
import com.bestseller.pojo.Sku;
import com.bestseller.pojo.query.ImgQuery;
import com.bestseller.pojo.query.ProductQuery;
import com.bestseller.service.ImgService;
import com.bestseller.service.ProductService;
import com.bestseller.service.SkuService;

import cn.itcast.common.page.Pagination;

/**
 * 商品事务层
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Resource
	ProductMapper productDao;
	@Autowired
	ImgService imgService;
	@Autowired
	SkuService skuService;
	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addProduct(Product product) {
		//商品信息
		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String no=df.format(new Date());
		product.setNo(no);
		product.setCreateTime(new Date());
		Integer i = productDao.addProduct(product);
		
		//图片信息
		Img img = product.getImg();
		img.setProductId(product.getId());
		img.setIsDef(1);
		imgService.addImg(img);
		
		
		//sku信息
		Sku sku =new Sku();
		sku.setProductId(product.getId());
		sku.setDeliveFee(10.00);
		sku.setSkuPrice(0.00);
		sku.setMarketPrice(0.00);
		sku.setStockInventory(0);
		sku.setSkuUpperLimit(0);
		sku.setCreateTime(new Date());
		sku.setSkuType(1);
		sku.setSales(0);
		sku.setLastStatus(1);
		
		for(String color:product.getColor().split(",")){
			sku.setColorId(Integer.parseInt(color));
			for(String size:product.getSize().split(",")){
				sku.setSize(size);
				skuService.addSku(sku);
			}
		}
		return i ;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Product getProductByKey(Integer id) {
		return productDao.getProductByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductsByKeys(List<Integer> idList) {
		return productDao.getProductsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return productDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return productDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateProductByKey(Product product) {
		return productDao.updateProductByKey(product);
	}
	
	@Transactional(readOnly = true)
	public Pagination getProductListWithPage(ProductQuery productQuery) {
		Pagination p = new Pagination(productQuery.getPageNo(),productQuery.getPageSize(),productDao.getProductListCount(productQuery));
		List<Product> products = productDao.getProductListWithPage(productQuery);
		for (Product product : products) {
			ImgQuery imgQuery=new ImgQuery();
			imgQuery.setProductId(product.getId());
			imgQuery.setIsDef(1);
			List<Img> imgList = imgService.getImgList(imgQuery);
			product.setImg(imgList.get(0));
		}
		p.setList(products);
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Product> getProductList(ProductQuery productQuery) {
		return productDao.getProductList(productQuery);
	}
}
