package com.bestseller.controller.front;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bestseller.common.Constant;
import com.bestseller.common.session.SessionProvider;
import com.bestseller.pojo.BuyCart;
import com.bestseller.pojo.Buyer;
import com.bestseller.pojo.ItemCart;
import com.bestseller.pojo.Order;
import com.bestseller.pojo.Sku;
import com.bestseller.service.OrderService;
import com.bestseller.service.SkuService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class FrontOrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private SessionProvider sessionProvider;
	@RequestMapping(value="/buyer/confirmOrder.shtml",method=RequestMethod.POST)
	public String addOrder(Order order,HttpServletRequest request,HttpServletResponse response){
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		BuyCart buyCart=null;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(Constant.COOKIE_CART_NAME)){
					try {
						buyCart=om.readValue(cookie.getValue(), BuyCart.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}else{
			return "redirect:/shopping/cart.shtml";
		}
		//补全sku数据
		List<ItemCart> itemCarts = buyCart.getItemCarts();
		for(ItemCart itemCart:itemCarts){
			Sku sku = skuService.getSkuByKey(itemCart.getSku().getId());
			itemCart.setSku(sku);
		}
		//填充订单用户
		Buyer buyer=(Buyer) sessionProvider.getAttribute(request, Constant.SESSION_USER_NAME);
		order.setBuyerId(buyer.getUsername());
		orderService.addOrder(order,buyCart);
		
		//情空购物车
		Cookie cookie=new Cookie(Constant.COOKIE_CART_NAME,null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "product/confirmOrder";
	}
}
