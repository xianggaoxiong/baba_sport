package com.bestseller.mapper;

import java.util.List;

import com.bestseller.pojo.Detail;
import com.bestseller.pojo.query.DetailQuery;


public interface DetailMapper {

	/**
	 * 添加
	 * @param detail
	 */
	public Integer addDetail(Detail detail);

	/**
	 * 根据主键查找
	 * @param detailQuery
	 */
	public Detail getDetailByKey(Integer id);

	/**
	 * 根据主键批量查找
	 * @param detailQuery
	 */
	public List<Detail> getDetailsByKeys(List<Integer> idList);

	/**
	 * 根据主键删除
	 * @param detailQuery
	 */
	public Integer deleteByKey(Integer id);

	/**
	 * 根据主键批量删除
	 * @param detailQuery
	 */
	public Integer deleteByKeys(List<Integer> idList);

	/**
	 * 根据主键更新
	 * @param detailQuery
	 */
	public Integer updateDetailByKey(Detail detail);

	/**
	 * 分页查询
	 * @param detailQuery
	 */
	public List<Detail> getDetailListWithPage(DetailQuery detailQuery);

	/**
	 * 集合查询
	 * @param detailQuery
	 */
	public List<Detail> getDetailList(DetailQuery detailQuery);
	
	/**
	 * 总条数
	 * @param detailQuery
	 */
	public int getDetailListCount(DetailQuery detailQuery);
}
