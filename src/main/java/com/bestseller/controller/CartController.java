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
import com.bestseller.pojo.BuyCart;
import com.bestseller.pojo.ItemCart;
import com.bestseller.pojo.Sku;
import com.bestseller.service.SkuService;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class CartController {
	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/shopping/cart.shtml")
	public String cart(Integer productId,Integer amount,Integer skuId,Integer skuLimit,
			HttpServletRequest request,HttpServletResponse response,ModelMap model){
		//购物车
		BuyCart buycart=new BuyCart();
		
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
			ObjectMapper om=new ObjectMapper();
			om.setSerializationInclusion(Include.NON_NULL);
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
}
