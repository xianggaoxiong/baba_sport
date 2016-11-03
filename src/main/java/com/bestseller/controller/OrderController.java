package com.bestseller.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bestseller.pojo.Addr;
import com.bestseller.pojo.Detail;
import com.bestseller.pojo.Order;
import com.bestseller.pojo.query.AddrQuery;
import com.bestseller.pojo.query.DetailQuery;
import com.bestseller.pojo.query.OrderQuery;
import com.bestseller.service.AddrService;
import com.bestseller.service.DetailService;
import com.bestseller.service.OrderService;

@Controller
@RequestMapping("/back")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private AddrService addrService;
	@Autowired
	private DetailService detailService;
	//订单列表
	@RequestMapping("/order/list.do")
	public String showOrderList(Integer isPaiy,Integer state,ModelMap model){
		OrderQuery orderQuery=new OrderQuery();
		if(isPaiy!=null){
			orderQuery.setIsPaiy(isPaiy);
		}
		if(state!=null){
			orderQuery.setState(state);
		}
		List<Order> orders= orderService.getOrderList(orderQuery);
		model.addAttribute("orders",orders);
		return "order/list";
	}
	
	//订单详情查看
	@RequestMapping("/order/view.do")
	public String showOrderDetail(Integer orderId,ModelMap model){
		//订单头信息
		Order order = orderService.getOrderByKey(orderId);
		model.addAttribute("order", order);
		
		//订单行信息
		DetailQuery detailQuery=new DetailQuery();
		detailQuery.setOrderId(orderId);
		List<Detail> details = detailService.getDetailList(detailQuery);
		model.addAttribute("details", details);
		
		//收货地址信息
		AddrQuery addrQuery=new AddrQuery();
		addrQuery.setBuyerId(order.getBuyerId());
		List<Addr> addrs = addrService.getAddrList(addrQuery);
		model.addAttribute("addr", addrs.get(0));
		
		return "order/view";
	}
}
