package ecommerce;

import java.io.*;
import java.util.Scanner;

public class Cart {

	private static final String FILE_NAME = "cart.txt";

	// ================= ADD TO CART =================

	public static void addToCart(Scanner sc, String customerEmail) {

		System.out.println("\n===== ADD TO CART =====");

		Product.viewProducts();

		System.out.print("Enter Product ID: ");
		int productId = sc.nextInt();

		System.out.print("Enter Quantity: ");
		int quantity = sc.nextInt();
		sc.nextLine();

		if (quantity <= 0) {

			System.out.println("Quantity must be greater than 0.");
			return;
		}

		Product product = Product.findProductById(productId);

		if (product == null) {

			System.out.println("Product Not Found!");
			return;
		}

		if (product.getStock() < quantity) {

			System.out.println("Only " + product.getStock() + " products available.");
			return;
		}

		FileDatabase.saveCartItem(customerEmail, productId, quantity);

		System.out.println(product.getName() + " added to cart!");
	}

	// ================= VIEW CART =================

	public static void viewCart(String customerEmail) {

		System.out.println("\n===== MY CART =====");

		File file = new File(FILE_NAME);

		if (!file.exists()) {

			System.out.println("Cart is empty.");
			return;
		}

		double total = 0;
		boolean found = false;

		try {

			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 3 && data[0].equalsIgnoreCase(customerEmail)) {

					int productId = Integer.parseInt(data[1]);

					int quantity = Integer.parseInt(data[2]);

					Product product = Product.findProductById(productId);

					if (product != null) {

						found = true;

						double itemTotal = product.getFinalPrice() * quantity;

						System.out.println("----------------------------");

						System.out.println("Product  : " + product.getName());

						System.out.println("Price    : ₹" + product.getFinalPrice());

						System.out.println("Quantity : " + quantity);

						System.out.println("Total    : ₹" + itemTotal);

						total += itemTotal;
					}
				}
			}

			br.close();

			if (!found) {

				System.out.println("Your cart is empty.");

			} else {

				System.out.println("----------------------------");

				System.out.println("Cart Total: ₹" + total);
			}

		} catch (IOException e) {

			System.out.println("Error while reading cart.");
		}
	}

	// ================= REMOVE FROM CART =================

	public static void removeFromCart(Scanner sc, String customerEmail) {

		System.out.println("\n===== REMOVE FROM CART =====");

		System.out.print("Enter Product ID: ");
		int productId = sc.nextInt();
		sc.nextLine();

		File file = new File(FILE_NAME);

		if (!file.exists()) {

			System.out.println("Cart is empty.");
			return;
		}

		File tempFile = new File("cart_temp.txt");

		boolean removed = false;

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 3 && data[0].equalsIgnoreCase(customerEmail)
						&& Integer.parseInt(data[1]) == productId) {

					removed = true;
					continue;
				}

				bw.write(line);
				bw.newLine();
			}

			br.close();
			bw.close();

			file.delete();
			tempFile.renameTo(file);

			if (removed) {

				System.out.println("Product removed from cart!");

			} else {

				System.out.println("Product not found in your cart.");
			}

		} catch (IOException e) {

			System.out.println("Error while removing product.");
		}
	}

	// ================= CART TOTAL =================

	public static double calculateTotal(String customerEmail) {

		File file = new File(FILE_NAME);

		if (!file.exists()) {
			return 0;
		}

		double total = 0;

		try {

			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 3 && data[0].equalsIgnoreCase(customerEmail)) {

					int productId = Integer.parseInt(data[1]);

					int quantity = Integer.parseInt(data[2]);

					Product product = Product.findProductById(productId);

					if (product != null) {

						total += product.getFinalPrice() * quantity;
					}
				}
			}

			br.close();

		} catch (IOException e) {

			System.out.println("Error calculating cart total.");
		}

		return total;
	}
}