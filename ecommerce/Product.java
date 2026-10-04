package ecommerce;

import java.io.*;
import java.util.Scanner;

public class Product {

	private int id;
	private String name;
	private String category;
	private double price;
	private double discount;
	private int stock;

	private static final String FILE_NAME = "products.txt";

	public Product(int id, String name, String category, double price, double discount, int stock) {

		this.id = id;
		this.name = name;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public double getPrice() {
		return price;
	}

	public double getDiscount() {
		return discount;
	}

	public int getStock() {
		return stock;
	}

	public double getFinalPrice() {

		return price - (price * discount / 100);
	}

	public void setStock(int stock) {

		this.stock = stock;
	}

	// ================= ADD PRODUCT =================

	public static void addProduct(Scanner sc) {

		System.out.println("\n===== ADD PRODUCT =====");

		System.out.print("Enter Product ID: ");
		int id = sc.nextInt();
		sc.nextLine();

		if (findProductById(id) != null) {

			System.out.println("Product ID already exists!");
			return;
		}

		System.out.print("Enter Product Name: ");
		String name = sc.nextLine();

		System.out.print("Enter Category: ");
		String category = sc.nextLine();

		System.out.print("Enter Price: ");
		double price = sc.nextDouble();

		System.out.print("Enter Discount (%): ");
		double discount = sc.nextDouble();

		System.out.print("Enter Stock: ");
		int stock = sc.nextInt();
		sc.nextLine();

		Product product = new Product(id, name, category, price, discount, stock);

		FileDatabase.saveProduct(product);

		System.out.println("Product Added Successfully!");
	}

	// ================= VIEW PRODUCTS =================

	public static void viewProducts() {

		System.out.println("\n===== ALL PRODUCTS =====");

		try {

			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

			String line;
			boolean found = false;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 6) {

					found = true;

					double price = Double.parseDouble(data[3]);

					double discount = Double.parseDouble(data[4]);

					double finalPrice = price - (price * discount / 100);

					System.out.println("\n----------------------------");
					System.out.println("ID       : " + data[0]);
					System.out.println("Name     : " + data[1]);
					System.out.println("Category : " + data[2]);
					System.out.println("Price    : ₹" + price);
					System.out.println("Discount : " + discount + "%");
					System.out.println("Final    : ₹" + finalPrice);
					System.out.println("Stock    : " + data[5]);
				}
			}

			br.close();

			if (!found) {
				System.out.println("No Products Found.");
			}

		} catch (FileNotFoundException e) {

			System.out.println("No Products Found.");

		} catch (IOException e) {

			System.out.println("Error while reading products.");
		}
	}

	// ================= SEARCH PRODUCT =================

	public static void searchProduct(Scanner sc) {

		System.out.println("\n===== SEARCH PRODUCT =====");

		System.out.print("Enter Product Name: ");
		String search = sc.nextLine();

		Product product = findProductByName(search);

		if (product == null) {

			System.out.println("Product Not Found.");
			return;
		}

		System.out.println("\nProduct Found!");
		System.out.println("ID       : " + product.getId());
		System.out.println("Name     : " + product.getName());
		System.out.println("Category : " + product.getCategory());
		System.out.println("Price    : ₹" + product.getPrice());
		System.out.println("Discount : " + product.getDiscount() + "%");
		System.out.println("Final    : ₹" + product.getFinalPrice());
		System.out.println("Stock    : " + product.getStock());
	}

	// ================= PRODUCT DETAILS =================

	public static void viewProductDetails(Scanner sc) {

		System.out.println("\n===== PRODUCT DETAILS =====");

		System.out.print("Enter Product ID: ");
		int id = sc.nextInt();
		sc.nextLine();

		Product product = findProductById(id);

		if (product == null) {

			System.out.println("Product Not Found.");
			return;
		}

		System.out.println("\n===== DETAILS =====");
		System.out.println("ID       : " + product.getId());
		System.out.println("Name     : " + product.getName());
		System.out.println("Category : " + product.getCategory());
		System.out.println("Price    : ₹" + product.getPrice());
		System.out.println("Discount : " + product.getDiscount() + "%");
		System.out.println("Final    : ₹" + product.getFinalPrice());
		System.out.println("Stock    : " + product.getStock());
	}

	// ================= AVAILABILITY =================

	public static void checkAvailability(Scanner sc) {

		System.out.println("\n===== PRODUCT AVAILABILITY =====");

		System.out.print("Enter Product ID: ");
		int id = sc.nextInt();
		sc.nextLine();

		Product product = findProductById(id);

		if (product == null) {

			System.out.println("Product Not Found.");
			return;
		}

		if (product.getStock() > 0) {

			System.out.println("Product Available!");
			System.out.println("Name : " + product.getName());
			System.out.println("Available Stock : " + product.getStock());

		} else {

			System.out.println("Product is Out of Stock.");
		}
	}

	// ================= FIND BY ID =================

	public static Product findProductById(int productId) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 6 && Integer.parseInt(data[0]) == productId) {

					br.close();

					return new Product(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]),
							Double.parseDouble(data[4]), Integer.parseInt(data[5]));
				}
			}

			br.close();

		} catch (Exception e) {

			System.out.println("Error while finding product.");
		}

		return null;
	}

	// ================= FIND BY NAME =================

	public static Product findProductByName(String productName) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 6 && data[1].equalsIgnoreCase(productName)) {

					br.close();

					return new Product(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]),
							Double.parseDouble(data[4]), Integer.parseInt(data[5]));
				}
			}

			br.close();

		} catch (Exception e) {

			System.out.println("Error while reading product.");
		}

		return null;
	}

	// ================= UPDATE PRODUCT =================

	public static void updateProduct(Scanner sc) {

		System.out.println("\n===== UPDATE PRODUCT =====");

		System.out.print("Enter Product ID: ");
		int id = sc.nextInt();
		sc.nextLine();

		Product oldProduct = findProductById(id);

		if (oldProduct == null) {

			System.out.println("Product Not Found!");
			return;
		}

		System.out.println("\nCurrent Product Details:");
		System.out.println("Name     : " + oldProduct.getName());
		System.out.println("Category : " + oldProduct.getCategory());
		System.out.println("Price    : ₹" + oldProduct.getPrice());
		System.out.println("Discount : " + oldProduct.getDiscount());
		System.out.println("Stock    : " + oldProduct.getStock());

		System.out.print("\nEnter New Product Name: ");
		String name = sc.nextLine();

		System.out.print("Enter New Category: ");
		String category = sc.nextLine();

		System.out.print("Enter New Price: ");
		double price = sc.nextDouble();

		System.out.print("Enter New Discount: ");
		double discount = sc.nextDouble();

		System.out.print("Enter New Stock: ");
		int stock = sc.nextInt();
		sc.nextLine();

		Product newProduct = new Product(id, name, category, price, discount, stock);

		updateProductFile(newProduct);

		System.out.println("Product Updated Successfully!");
	}

	// ================= UPDATE FILE =================

	private static void updateProductFile(Product updatedProduct) {

		File file = new File(FILE_NAME);
		File temp = new File("products_temp.txt");

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 6 && Integer.parseInt(data[0]) == updatedProduct.getId()) {

					bw.write(updatedProduct.getId() + "|" + updatedProduct.getName() + "|"
							+ updatedProduct.getCategory() + "|" + updatedProduct.getPrice() + "|"
							+ updatedProduct.getDiscount() + "|" + updatedProduct.getStock());

				} else {

					bw.write(line);
				}

				bw.newLine();
			}

			br.close();
			bw.close();

			file.delete();
			temp.renameTo(file);

		} catch (IOException e) {

			System.out.println("Error while updating product.");
		}
	}

	// ================= DELETE PRODUCT =================

	public static void deleteProduct(Scanner sc) {

		System.out.println("\n===== DELETE PRODUCT =====");

		System.out.print("Enter Product ID: ");
		int id = sc.nextInt();
		sc.nextLine();

		Product product = findProductById(id);

		if (product == null) {

			System.out.println("Product Not Found!");
			return;
		}

		File file = new File(FILE_NAME);
		File temp = new File("products_temp.txt");

		boolean deleted = false;

		try {

			BufferedReader br = new BufferedReader(new FileReader(file));

			BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

			String line;

			while ((line = br.readLine()) != null) {

				String[] data = line.split("\\|");

				if (data.length == 6 && Integer.parseInt(data[0]) == id) {

					deleted = true;
					continue;
				}

				bw.write(line);
				bw.newLine();
			}

			br.close();
			bw.close();

			file.delete();
			temp.renameTo(file);

			if (deleted) {

				System.out.println("Product Deleted Successfully!");
			}

		} catch (IOException e) {

			System.out.println("Error while deleting product.");
		}
	}

	// ================= UPDATE STOCK =================

	public static void updateStock(int productId, int newStock) {

		Product product = findProductById(productId);

		if (product == null) {
			return;
		}

		product.setStock(newStock);

		updateProductFile(product);
	}
}