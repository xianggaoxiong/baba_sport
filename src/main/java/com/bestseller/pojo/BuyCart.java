package com.bestseller.pojo;

import java.util.ArrayList;
import java.util.List;

public class BuyCart {
	private List<ItemCart> itemCarts=new ArrayList<ItemCart>();
	
	
	//商品id
	private Integer productId;
	
	public void addItemCart(ItemCart itemCart){
		itemCarts.add(itemCart);
	}

	public List<ItemCart> getItemCarts() {
		return itemCarts;
	}

	public void setItemCarts(List<ItemCart> itemCarts) {
		this.itemCarts = itemCarts;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
