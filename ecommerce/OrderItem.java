package ecommerce;

public class OrderItem {

	private int productId;
	private String productName;
	private double price;
	private int quantity;

	public OrderItem(int productId, String productName, double price, int quantity) {

		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getSubtotal() {
		return price * quantity;
	}

	public void displayItem() {

		System.out.println("----------------------------");

		System.out.println("Product ID : " + productId);

		System.out.println("Product    : " + productName);

		System.out.println("Price      : ₹" + price);

		System.out.println("Quantity   : " + quantity);

		System.out.println("Subtotal   : ₹" + getSubtotal());
	}
}