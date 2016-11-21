package com.bestseller.controller;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bestseller.common.Constant;
import com.bestseller.common.session.SessionProvider;
import com.bestseller.pojo.Addr;
import com.bestseller.pojo.BuyCart;
import com.bestseller.pojo.Buyer;
import com.bestseller.pojo.ItemCart;
import com.bestseller.pojo.Sku;
import com.bestseller.pojo.query.AddrQuery;
import com.bestseller.service.AddrService;
import com.bestseller.service.SkuService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class CartController {
	@Autowired
	private SkuService skuService;
	@Autowired
	private SessionProvider sessionProvider;
	@Autowired
	private AddrService addrService;
	@RequestMapping("/shopping/cart.shtml")
	public String cart(Integer productId,Integer amount,Integer skuId,Integer skuLimit,
			HttpServletRequest request,HttpServletResponse response,ModelMap model){
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		//购物车
		BuyCart buycart=null;
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(Constant.COOKIE_CART_NAME)){
					try {
						buycart=om.readValue(cookie.getValue(), BuyCart.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		
		
		if(buycart==null){
			buycart=new BuyCart();
		}
		
	
		
		//购物项
		if(null!=skuId){
			Sku sku=new Sku();
			sku.setId(skuId);
			if(null!=skuLimit){
				sku.setSkuUpperLimit(skuLimit);
			}
			ItemCart itemCart=new ItemCart();
			itemCart.setSku(sku);
			itemCart.setAmount(amount);
			buycart.addItemCart(itemCart);
			if(null!=productId){
				buycart.setProductId(productId);
			}
			
			StringWriter str=new StringWriter();
			try {
				om.writeValue(str, buycart);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Cookie cookie=new Cookie(Constant.COOKIE_CART_NAME, str.toString());
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
	
		List<ItemCart> itemCarts = buycart.getItemCarts();
		for (ItemCart itemCart : itemCarts) {
			Sku sku= skuService.getSkuByKey(itemCart.getSku().getId());
			itemCart.setSku(sku);
		}
		
	
		model.addAttribute("buycart", buycart);
		
		
		
		return "product/cart";
	}
	
	//清空购物车
	@RequestMapping("/shopping/deleteAll.shtml")
	public String deleteAll(HttpServletRequest request,HttpServletResponse response){
		Cookie cookie=new Cookie(Constant.COOKIE_CART_NAME, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "redirect:/shopping/cart.shtml";
	}
	
	//删除一个
	@RequestMapping("/shopping/deleteItem.shtml")
	public String deleteItem(Integer skuId,HttpServletRequest request,HttpServletResponse response){
		ObjectMapper om=new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL);
		//购物车
		BuyCart buycart=null;
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null&&cookies.length>0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(Constant.COOKIE_CART_NAME)){
					try {
						buycart=om.readValue(cookie.getValue(), BuyCart.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		
		
		//购物项
		if(null!=skuId){
			Sku sku=new Sku();
			sku.setId(skuId);
			ItemCart itemCart=new ItemCart();
			itemCart.setSku(sku);
			buycart.deleteItem(itemCart);
			
			
			StringWriter str=new StringWriter();
			try {
				om.writeValue(str, buycart);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Cookie cookie=new Cookie(Constant.COOKIE_CART_NAME, str.toString());
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		return "redirect:/shopping/cart.shtml";
	}
	
	//订单结算页面
	@RequestMapping("/buyer/productOrder.shtml")
	public String productOrder(HttpServletRequest request,HttpServletResponse response,ModelMap model){
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
		}
		
		if(buyCart!=null){
			List<ItemCart> itemCarts = buyCart.getItemCarts();
			if(itemCarts!=null&&itemCarts.size()>0){
				int i=itemCarts.size();
				for(ItemCart itemCart:itemCarts){
					Sku sku=skuService.getSkuByKey(itemCart.getSku().getId());
					if(itemCart.getAmount()>sku.getStockInventory()){
						buyCart.deleteItem(itemCart);
					}
				}
				
				int j=itemCarts.size();
				if(i>j){
					StringWriter sw=new StringWriter();
					try {
						om.writeValue(sw, buyCart);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Cookie cookie=new Cookie(Constant.COOKIE_CART_NAME,sw.toString());
					response.addCookie(cookie);
					cookie.setMaxAge(60*60*24);
					cookie.setPath("/");
					return "redirect:/shopping/cart.shtml";
				}else{
					Buyer buyer=(Buyer) sessionProvider.getAttribute(response,request, Constant.SESSION_USER_NAME);
					AddrQuery addrQuery=new AddrQuery();
					addrQuery.setBuyerId(buyer.getUsername());
					addrQuery.setIsDef(1);
					List<Addr> addrs = addrService.getAddrList(addrQuery);
					model.addAttribute("addr", addrs.get(0));
					
					List<ItemCart> itemCarts2 = buyCart.getItemCarts();
					for(ItemCart itemCart:itemCarts2){
						Sku skuByKey = skuService.getSkuByKey(itemCart.getSku().getId());
						itemCart.setSku(skuByKey);
					}
					
					model.addAttribute("buyCart", buyCart);
					return "product/productOrder";
				}
				
				
			}else{
				return "redirect:/shopping/cart.shtml";
			}
		}else{
			return "redirect:/shopping/cart.shtml";
		}
	}
	
}
