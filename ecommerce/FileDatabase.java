package ecommerce;

import java.io.*;

public class FileDatabase {

	private static final String CUSTOMER_FILE = "customers.txt";

	private static final String SELLER_FILE = "sellers.txt";

	private static final String PRODUCT_FILE = "products.txt";

	private static final String CART_FILE = "cart.txt";

	private static final String ORDER_FILE = "orders.txt";

	private static final String PAYMENT_FILE = "payment.txt";

	// ================= CUSTOMER =================

	public static void saveCustomer(User user) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true));

			bw.write(user.getName() + "|" + user.getEmail() + "|" + user.getPassword() + "|" + user.getPhone() + "|"
					+ user.getAddress());

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving customer data.");
		}
	}

	public static boolean emailExists(String email) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|", -1);

				if (data.length >= 5 && data[1].equalsIgnoreCase(email)) {

					br.close();
					return true;
				}
			}

			br.close();

		} catch (FileNotFoundException e) {

			return false;

		} catch (IOException e) {

			System.out.println("Error checking customer email.");
		}

		return false;
	}

	public static User findCustomer(String email, String password) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|", -1);

				if (data.length >= 5) {

					if (data[1].equalsIgnoreCase(email) && data[2].equals(password)) {

						br.close();

						return new User(data[0], data[1], data[2], data[3], data[4]);
					}
				}
			}

			br.close();

		} catch (IOException e) {

			return null;
		}

		return null;
	}

	// ================= SELLER =================

	public static void saveSeller(Seller seller) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(SELLER_FILE, true));

			bw.write(seller.getName() + "|" + seller.getEmail() + "|" + seller.getPassword() + "|" + seller.getPhone());

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving seller data.");
		}
	}

	public static boolean sellerEmailExists(String email) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(SELLER_FILE));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 4 && data[1].equalsIgnoreCase(email)) {

					br.close();
					return true;
				}
			}

			br.close();

		} catch (FileNotFoundException e) {

			return false;

		} catch (IOException e) {

			System.out.println("Error checking seller email.");
		}

		return false;
	}

	public static Seller findSeller(String email, String password) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(SELLER_FILE));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 4 && data[1].equalsIgnoreCase(email) && data[2].equals(password)) {

					br.close();

					return new Seller(data[0], data[1], data[2], data[3]);
				}
			}

			br.close();

		} catch (IOException e) {

			return null;
		}

		return null;
	}

	// ================= PRODUCT =================

	public static void saveProduct(Product product) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(PRODUCT_FILE, true));

			bw.write(product.getId() + "|" + product.getName() + "|" + product.getCategory() + "|" + product.getPrice()
					+ "|" + product.getDiscount() + "|" + product.getStock());

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving product.");
		}
	}

	// ================= CART =================

	public static void saveCartItem(String customerEmail, int productId, int quantity) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(CART_FILE, true));

			bw.write(customerEmail + "|" + productId + "|" + quantity);

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving cart.");
		}
	}

	// ================= ORDER =================

	public static void saveOrder(String orderId, String customerEmail, OrderItem item) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(ORDER_FILE, true));

			bw.write(orderId + "|" + customerEmail + "|" + item.getProductId() + "|" + item.getProductName() + "|"
					+ item.getPrice() + "|" + item.getQuantity() + "|" + item.getSubtotal());

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving order.");
		}
	}

	// ================= PAYMENT =================

	public static void savePayment(String orderId, String customerEmail, double amount, String paymentMethod,
			String paymentStatus) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(PAYMENT_FILE, true));

			bw.write(orderId + "|" + customerEmail + "|" + amount + "|" + paymentMethod + "|" + paymentStatus);

			bw.newLine();
			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving payment.");
		}
	}

	// ================= DATABASE FILES =================

	public static void initializeDatabase() {

		String[] files = {

				CUSTOMER_FILE, SELLER_FILE, PRODUCT_FILE, CART_FILE, ORDER_FILE, PAYMENT_FILE };

		for (String fileName : files) {

			try {

				File file = new File(fileName);

				if (file.createNewFile()) {

					System.out.println(fileName + " created successfully.");
				}

			} catch (IOException e) {

				System.out.println("Error creating " + fileName);
			}
		}
	}

	// ================= CUSTOMER BY EMAIL =================

	public static User findCustomerByEmail(String email) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|", -1);

				if (data.length >= 5 && data[1].equalsIgnoreCase(email)) {

					br.close();

					return new User(data[0], data[1], data[2], data[3], data[4]);
				}
			}

			br.close();

		} catch (IOException e) {

			System.out.println("Error while reading customer data.");
		}

		return null;
	}

	// ================= SAVE BILL =================

	public static void saveBill(String bill) {

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("bills.txt", true));

			bw.write(bill);
			bw.newLine();

			bw.close();

		} catch (IOException e) {

			System.out.println("Error while saving bill.");
		}
	}
}