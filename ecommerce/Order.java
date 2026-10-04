package ecommerce;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {

	private static final String ORDER_FILE = "orders.txt";

	private static int orderCounter = 1000;

	// ================= CHECKOUT =================

	public static void checkout(Scanner sc, String customerEmail) {

		System.out.println("\n===== CHECKOUT =====");

		File cartFile = new File("cart.txt");

		if (!cartFile.exists()) {

			System.out.println("Cart is empty.");
			return;
		}

		ArrayList<OrderItem> orderItems = new ArrayList<>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(cartFile));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 3 && data[0].equalsIgnoreCase(customerEmail)) {

					int productId = Integer.parseInt(data[1]);

					int quantity = Integer.parseInt(data[2]);

					Product product = Product.findProductById(productId);

					if (product != null) {

						OrderItem item = new OrderItem(product.getId(), product.getName(), product.getFinalPrice(),
								quantity);

						orderItems.add(item);
					}
				}
			}

			br.close();

		} catch (IOException e) {

			System.out.println("Error while reading cart.");
			return;
		}

		if (orderItems.isEmpty()) {

			System.out.println("Your cart is empty.");
			return;
		}

		// ================= STOCK CHECK =================

		for (OrderItem item : orderItems) {

			Product product = Product.findProductById(item.getProductId());

			if (product == null) {

				System.out.println("Product not found.");
				return;
			}

			if (product.getStock() < item.getQuantity()) {

				System.out.println("Insufficient stock for " + item.getProductName());

				return;
			}
		}

		// ================= TOTAL =================

		double total = orderItems.stream().mapToDouble(OrderItem::getSubtotal).sum();

		String orderId = "ORD" + (++orderCounter);

		// ================= PAYMENT FIRST =================

		boolean paymentSuccess = Payment.makePayment(sc, customerEmail, orderId, total);

		if (!paymentSuccess) {

			System.out.println("Order cancelled because payment failed.");

			return;
		}

		// ================= REDUCE STOCK =================

		for (OrderItem item : orderItems) {

			Product product = Product.findProductById(item.getProductId());

			int newStock = product.getStock() - item.getQuantity();

			Product.updateStock(item.getProductId(), newStock);
		}

		// ================= SAVE ORDER =================

		for (OrderItem item : orderItems) {

			FileDatabase.saveOrder(orderId, customerEmail, item);
		}

		// ================= BILL =================

		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		String dateTime = now.format(formatter);

		StringBuilder bill = new StringBuilder();

		bill.append("\n========================================\n");
		bill.append("              ORDER BILL\n");
		bill.append("========================================\n");

		bill.append("Order ID  : ").append(orderId).append("\n");

		bill.append("Customer  : ").append(customerEmail).append("\n");

		User customer = FileDatabase.findCustomerByEmail(customerEmail);

		if (customer != null) {

			bill.append("Phone     : ").append(customer.getPhone()).append("\n");

			bill.append("Address   : ").append(customer.getAddress()).append("\n");
		}

		bill.append("Date/Time : ").append(dateTime).append("\n");

		bill.append("----------------------------------------\n");

		for (OrderItem item : orderItems) {

			bill.append("Product ID : ").append(item.getProductId()).append("\n");

			bill.append("Product    : ").append(item.getProductName()).append("\n");

			bill.append("Price      : ₹").append(String.format("%.2f", item.getPrice())).append("\n");

			bill.append("Quantity   : ").append(item.getQuantity()).append("\n");

			bill.append("Subtotal   : ₹").append(String.format("%.2f", item.getSubtotal())).append("\n");

			bill.append("----------------------------------------\n");
		}

		bill.append("Grand Total : ₹").append(String.format("%.2f", total)).append("\n");

		bill.append("========================================\n");
		bill.append("       ORDER PLACED SUCCESSFULLY\n");
		bill.append("========================================\n");
		bill.append("       THANK YOU FOR SHOPPING!\n");
		bill.append("========================================\n");

		System.out.println(bill);

		FileDatabase.saveBill(bill.toString());

		clearCart(customerEmail);

		System.out.println("\nBill saved in bills.txt");
	}

	// ================= CLEAR CART =================

	private static void clearCart(String customerEmail) {

		File file = new File("cart.txt");

		if (!file.exists()) {
			return;
		}

		File temp = new File("cart_temp.txt");

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 3 && data[0].equalsIgnoreCase(customerEmail)) {

					continue;
				}

				bw.write(line);
				bw.newLine();
			}

			br.close();
			bw.close();

			file.delete();
			temp.renameTo(file);

		} catch (IOException e) {

			System.out.println("Error while clearing cart.");
		}
	}

	// ================= CUSTOMER ORDER HISTORY =================

	public static void viewOrderHistory(String customerEmail) {

		System.out.println("\n===== ORDER HISTORY =====");

		File file = new File(ORDER_FILE);

		if (!file.exists()) {

			System.out.println("No orders found.");
			return;
		}

		boolean found = false;
		String previousOrderId = "";

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 7 && data[1].equalsIgnoreCase(customerEmail)) {

					found = true;

					String orderId = data[0];

					if (!orderId.equals(previousOrderId)) {

						System.out.println("\n----------------------------");

						System.out.println("Order ID : " + orderId);
					}

					System.out.println("Product  : " + data[3]);

					System.out.println("Price    : ₹" + data[4]);

					System.out.println("Quantity : " + data[5]);

					System.out.println("Subtotal : ₹" + data[6]);

					previousOrderId = orderId;
				}
			}

			br.close();

			if (!found) {

				System.out.println("No orders found.");
			}

		} catch (IOException e) {

			System.out.println("Error while reading order history.");
		}
	}

	// ================= SELLER VIEW ORDERS =================

	public static void viewAllOrders() {

		System.out.println("\n===== ALL CUSTOMER ORDERS =====");

		File file = new File(ORDER_FILE);

		if (!file.exists()) {

			System.out.println("No orders placed yet.");
			return;
		}

		boolean found = false;
		int totalItems = 0;

		HashSet<String> orderIds = new HashSet<>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 7) {

					found = true;

					orderIds.add(data[0]);
					totalItems++;

					System.out.println("\n----------------------------");

					System.out.println("Order ID : " + data[0]);

					System.out.println("Customer : " + data[1]);

					System.out.println("Product  : " + data[3]);

					System.out.println("Price    : ₹" + data[4]);

					System.out.println("Quantity : " + data[5]);

					System.out.println("Subtotal : ₹" + data[6]);
				}
			}

			br.close();

			if (!found) {

				System.out.println("No orders placed yet.");

			} else {

				System.out.println("\n============================");

				System.out.println("TOTAL ORDERS : " + orderIds.size());

				System.out.println("TOTAL ITEMS  : " + totalItems);

				System.out.println("============================");
			}

		} catch (IOException e) {

			System.out.println("Error while reading orders.");
		}
	}
}