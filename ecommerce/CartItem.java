package ecommerce;

public class CartItem {

	private Product product;
	private int quantity;

	public CartItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getItemTotal() {
		return product.getFinalPrice() * quantity;
	}

	public void displayItem() {

		System.out.println("----------------------------");
		System.out.println("Product  : " + product.getName());
		System.out.println("Price    : ₹" + product.getFinalPrice());
		System.out.println("Quantity : " + quantity);
		System.out.println("Total    : ₹" + getItemTotal());
	}
}