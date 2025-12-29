package model;

public class OrderItem {
	private int id;
	private int orderId;
	private int productId;
	private int price;
	private int quantity;
	private int subtotal;

	public OrderItem() {
	}

	public OrderItem(int orderId, int productId, int price, int quantity) {
		this.orderId = orderId;
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = price * quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}
}
