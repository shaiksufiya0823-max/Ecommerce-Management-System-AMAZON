package ecommerce;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		FileDatabase.initializeDatabase();

		Scanner sc = new Scanner(System.in);

		int choice;

		do {

			System.out.println("\n==============================");

			System.out.println("     AMAZON MINI E-COMMERCE");

			System.out.println("==============================");

			System.out.println("1. Customer");
			System.out.println("2. Seller");
			System.out.println("3. Exit");

			System.out.println("==============================");

			System.out.print("Enter your choice: ");

			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {

			// =================================================
			// CUSTOMER
			// =================================================

			case 1:

				int customerChoice;

				do {

					System.out.println("\n===== CUSTOMER =====");

					System.out.println("1. Register");

					System.out.println("2. Login");

					System.out.println("3. Back");

					System.out.print("Enter your choice: ");

					customerChoice = sc.nextInt();

					sc.nextLine();

					switch (customerChoice) {

					case 1:

						Customer.register(sc);

						break;

					case 2:

						Customer customer = Customer.login(sc);

						if (customer != null) {

							int menuChoice;

							do {

								System.out.println("\n===== CUSTOMER MENU =====");

								System.out.println("1. View Profile");

								System.out.println("2. View Products");

								System.out.println("3. Search Product");

								
								System.out.println("4. Add to Cart");

								System.out.println("5. View Cart");

								System.out.println("6. Remove from Cart");

								System.out.println("7. Checkout");

								System.out.println("8. Order History");

								System.out.println("9. Logout");

								System.out.print("Enter your choice: ");

								menuChoice = sc.nextInt();

								sc.nextLine();

								switch (menuChoice) {

								case 1:

									System.out.println("\n===== MY PROFILE =====");

									System.out.println("Name    : " + customer.getName());

									System.out.println("Email   : " + customer.getEmail());

									System.out.println("Phone   : " + customer.getPhone());

									System.out.println("Address : " + customer.getAddress());

									break;

								case 2:

									Product.viewProducts();

									break;

								case 3:

									Product.searchProduct(sc);

									break;

								

								case 4:

									Cart.addToCart(sc, customer.getEmail());

									break;

								case 5:

									Cart.viewCart(customer.getEmail());

									break;

								case 6:

									Cart.removeFromCart(sc, customer.getEmail());

									break;

								case 7:

									Order.checkout(sc, customer.getEmail());

									break;

								case 8:

									Order.viewOrderHistory(customer.getEmail());

									break;

								case 9:

									System.out.println("\nLogged out successfully!");

									break;

								default:

									System.out.println("Invalid Choice!");
								}

							} while (menuChoice != 9);
						}

						break;

					case 3:

						System.out.println("Returning to Main Menu...");

						break;

					default:

						System.out.println("Invalid Choice!");
					}

				} while (customerChoice != 3);

				break;

			// =================================================
			// SELLER
			// =================================================

			case 2:

				int sellerChoice;

				do {

					System.out.println("\n===== SELLER =====");

					System.out.println("1. Seller Register");

					System.out.println("2. Seller Login");

					System.out.println("3. Back");

					System.out.print("Enter Choice: ");

					sellerChoice = sc.nextInt();

					sc.nextLine();

					switch (sellerChoice) {

					case 1:

						Seller.register(sc);

						break;

					case 2:

						Seller seller = Seller.login(sc);

						if (seller != null) {

							int sellerMenuChoice;

							do {

								System.out.println("\n===== SELLER MENU =====");

								System.out.println("1. View Products");

								System.out.println("2. Add Product");

								System.out.println("3. Update Product");

								System.out.println("4. Delete Product");

								System.out.println("5. View Orders");

								System.out.println("6. Logout");

								System.out.print("Enter Choice: ");

								sellerMenuChoice = sc.nextInt();

								sc.nextLine();

								switch (sellerMenuChoice) {

								case 1:

									Product.viewProducts();

									break;

								case 2:

									Product.addProduct(sc);

									break;

								case 3:

									Product.updateProduct(sc);

									break;

								case 4:

									Product.deleteProduct(sc);

									break;

								case 5:

									Order.viewAllOrders();

									break;

								case 6:

									System.out.println("\nSeller Logged Out!");

									break;

								default:

									System.out.println("Invalid Choice!");
								}

							} while (sellerMenuChoice != 6);
						}

						break;

					case 3:

						System.out.println("Returning to Main Menu...");

						break;

					default:

						System.out.println("Invalid Choice!");
					}

				} while (sellerChoice != 3);

				break;

			// =================================================
			// EXIT
			// =================================================

			case 3:

				System.out.println("\nThank you for using " + "Amazon Mini E-Commerce!");

				break;

			default:

				System.out.println("Invalid Choice!");
			}

		} while (choice != 3);

		sc.close();
	}
}