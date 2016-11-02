package com.bestseller.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestseller.mapper.OrderMapper;
import com.bestseller.pojo.BuyCart;
import com.bestseller.pojo.Detail;
import com.bestseller.pojo.ItemCart;
import com.bestseller.pojo.Order;
import com.bestseller.pojo.query.OrderQuery;
import com.bestseller.service.DetailService;
import com.bestseller.service.OrderService;

import cn.itcast.common.page.Pagination;

/**
 * 订单主
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Resource
	OrderMapper orderDao;
	@Autowired
	private DetailService detailService;
	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addOrder(Order order,BuyCart buyCart) {
		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String oid=df.format(new Date());
		order.setOid(oid);
		order.setDeliverFee(buyCart.getCarriage());
		order.setPayableFee(buyCart.getRealPayAmount());
		order.setTotalPrice(buyCart.getPayAmount());
		//支付状态
		if(order.getPaymentWay()==0){
			order.setIsPaiy(0);
		}else{
			order.setIsPaiy(1);
		}
		//订单状态
		order.setState(0);
		order.setCreateDate(new Date());
		Integer result = orderDao.addOrder(order);
		
		//订单详情
		List<ItemCart> itemCarts = buyCart.getItemCarts();
		for(ItemCart itemCart:itemCarts){
			Detail detail=new Detail();
			detail.setOrderId(order.getId());
			detail.setProductNo(itemCart.getSku().getProduct().getNo());
			detail.setProductName(itemCart.getSku().getProduct().getName());
			detail.setColor(itemCart.getSku().getColor().getName());
			detail.setSize(itemCart.getSku().getSize());
			detail.setSkuPrice(itemCart.getSku().getSkuPrice());
			detail.setAmount(itemCart.getAmount());
			detailService.addDetail(detail);
		}
		
		return result;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Order getOrderByKey(Integer id) {
		return orderDao.getOrderByKey(id);
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrdersByKeys(List<Integer> idList) {
		return orderDao.getOrdersByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return orderDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return orderDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateOrderByKey(Order order) {
		return orderDao.updateOrderByKey(order);
	}
	
	@Transactional(readOnly = true)
	public Pagination getOrderListWithPage(OrderQuery orderQuery) {
		Pagination p = new Pagination(orderQuery.getPageNo(),orderQuery.getPageSize(),orderDao.getOrderListCount(orderQuery));
		p.setList(orderDao.getOrderListWithPage(orderQuery));
		return p;
	}
	
	@Transactional(readOnly = true)
	public List<Order> getOrderList(OrderQuery orderQuery) {
		return orderDao.getOrderList(orderQuery);
	}
}
