package com.bestseller.service;

import java.util.List;

import com.bestseller.pojo.Buyer;
import com.bestseller.pojo.query.BuyerQuery;

import cn.itcast.common.page.Pagination;


/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午01:50:28]
 */
public interface BuyerService {
	/**
	 * 基本插入
	 * 
	 * @return
	 */
	public Integer addBuyer(Buyer buyer);

	/**
	 * 根据主键查询
	 */
	public Buyer getBuyerByKey(String id);

	/**
	 * 根据主键批量查询
	 */
	public List<Buyer> getBuyersByKeys(List<String> idList);

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(String id);

	/**
	 * 根据主键批量删除
	 * 
	 * @return
	 */
	public Integer deleteByKeys(List<String> idList);

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateBuyerByKey(Buyer buyer);

	/**
	 * 根据条件查询分页查询
	 * 
	 * @param buyerQuery
	 *            查询条件
	 * @return
	 */
	public Pagination getBuyerListWithPage(BuyerQuery buyerQuery);

	/**
	 * 根据条件查询
	 * 
	 * @param buyerQuery
	 *            查询条件
	 * @return
	 */
	public List<Buyer> getBuyerList(BuyerQuery buyerQuery);
}