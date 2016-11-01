package com.bestseller.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BuyCart {
	private List<ItemCart> itemCarts=new ArrayList<ItemCart>();
	
	
	//商品id
	private Integer productId;
	
	public void addItemCart(ItemCart itemCart){
		if(itemCarts.contains(itemCart)){
			for(ItemCart ic:itemCarts){
				if(itemCart.equals(ic)){
					Integer totalAmount=ic.getAmount()+itemCart.getAmount();
					if(totalAmount<=ic.getSku().getSkuUpperLimit()){
						ic.setAmount(totalAmount);
					}else{
						ic.setAmount(ic.getSku().getSkuUpperLimit());
					}
				}
			}
		}else{
			itemCarts.add(itemCart);
		}
		
	}
	
	//删除一个
	public void deleteItem(ItemCart cartItem){
		itemCarts.remove(cartItem);
	}
	
	//小计
	@JsonIgnore
	public int getProductAmount(){
		int result=0;
		for(ItemCart itemCart:itemCarts){
			result+=itemCart.getAmount();
		}
		return result;
	}
	
	//商品金额
	@JsonIgnore
	public Double getPayAmount(){
		Double payAmount=0.00;
		for(ItemCart itemCart:itemCarts){
			payAmount+=itemCart.getSku().getSkuPrice()*itemCart.getAmount();
		}
		return payAmount;
	}
	
	//运费
	@JsonIgnore
	public Double getCarriage(){
		Double carriage=0.00;
		if(getPayAmount()<100){
			carriage=10.00;
		}
		return carriage;
	}
	
	//应付金额
	@JsonIgnore
	public Double getRealPayAmount(){
		return getPayAmount()+getCarriage();
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
