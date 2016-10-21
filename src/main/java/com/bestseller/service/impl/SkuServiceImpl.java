package com.bestseller.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.mapper.SkuMapper;
import com.bestseller.pojo.Color;
import com.bestseller.pojo.Sku;
import com.bestseller.pojo.query.SkuQuery;
import com.bestseller.service.ColorService;
import com.bestseller.service.SkuService;

import cn.itcast.common.page.Pagination;

/**
 * 最小销售单元事务层
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService {

	@Resource
	SkuMapper skuDao;
	@Autowired
	private ColorService colorService;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addSku(Sku sku) {
		return skuDao.addSku(sku);
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Sku getSkuByKey(Integer id) {
		return skuDao.getSkuByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Sku> getSkusByKeys(List<Integer> idList) {
		return skuDao.getSkusByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return skuDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return skuDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateSkuByKey(Sku sku) {
		return skuDao.updateSkuByKey(sku);
	}
	
	@Transactional(readOnly = true)
	public Pagination getSkuListWithPage(SkuQuery skuQuery) {
		Pagination p = new Pagination(skuQuery.getPageNo(),skuQuery.getPageSize(),skuDao.getSkuListCount(skuQuery));
		p.setList(skuDao.getSkuListWithPage(skuQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Sku> getSkuList(SkuQuery skuQuery) {
		List<Sku> skuList = skuDao.getSkuList(skuQuery);
		for(Sku sku:skuList){
			Color color = colorService.getColorByKey(sku.getColorId());
			sku.setColor(color);
		}
		return skuList;
	}

	@Override
	public List<Sku> getStock(Integer productId) {
		List<Sku> skuList = skuDao.getStock(productId);
		for(Sku sku:skuList){
			Color color = colorService.getColorByKey(sku.getColorId());
			sku.setColor(color);
		}
		return skuList;
	}
}
