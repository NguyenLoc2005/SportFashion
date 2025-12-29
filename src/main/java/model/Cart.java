package model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<Integer, CartItem> cart = new LinkedHashMap<>();
	
	public Map<Integer, CartItem> getCart(){
		return cart;
	}
	
	public void add(Product product) {
		int id = product.getId();
		cart.put(id, new CartItem(product,1));
	}
	
	 public void remove(int productId) {
	        cart.remove(productId);
	  }
}
