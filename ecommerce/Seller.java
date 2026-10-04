package ecommerce;

import java.util.Scanner;

public class Seller extends User {

	public Seller(String name, String email, String password, String phone) {

		super(name, email, password, phone);
	}

	// ================= SELLER REGISTRATION =================

	public static void register(Scanner sc) {

		System.out.println("\n===== SELLER REGISTRATION =====");

		System.out.print("Enter Name: ");
		String name = sc.nextLine();

		// ================= EMAIL =================

		String email;

		while (true) {

			System.out.print("Enter Email: ");
			email = sc.nextLine();

			if (!email.matches("^[a-zA-Z0-9._%+-]+@gmail\\.com$")) {

				System.out.println("Invalid Email! Example: abc123@gmail.com");
				continue;
			}

			if (FileDatabase.sellerEmailExists(email)) {

				System.out.println("Email already registered!");
				continue;
			}

			break;
		}

		// ================= PHONE =================

		String phone;

		while (true) {

			System.out.print("Enter Phone Number: ");
			phone = sc.nextLine();

			boolean validPhone = true;

			if (phone.length() != 10) {
				validPhone = false;
			}

			for (int i = 0; i < phone.length(); i++) {

				if (!Character.isDigit(phone.charAt(i))) {

					validPhone = false;
					break;
				}
			}

			if (phone.length() == 10) {

				if (phone.charAt(0) < '6' || phone.charAt(0) > '9') {

					validPhone = false;
				}
			}

			if (validPhone) {
				break;
			}

			System.out.println("Phone must contain 10 digits and start with 6, 7, 8 or 9.");
		}

		// ================= PASSWORD =================

		String password;

		while (true) {

			System.out.print("Enter Password: ");
			password = sc.nextLine();

			boolean upper = false;
			boolean lower = false;
			boolean number = false;
			boolean special = false;

			for (int i = 0; i < password.length(); i++) {

				char ch = password.charAt(i);

				if (Character.isUpperCase(ch)) {

					upper = true;

				} else if (Character.isLowerCase(ch)) {

					lower = true;

				} else if (Character.isDigit(ch)) {

					number = true;

				} else {

					special = true;
				}
			}

			if (password.length() >= 8 && upper && lower && number && special) {

				break;
			}

			System.out.println("\nInvalid Password!");
			System.out.println("Minimum 8 characters");
			System.out.println("One uppercase letter");
			System.out.println("One lowercase letter");
			System.out.println("One number");
			System.out.println("One special character");
		}

		Seller seller = new Seller(name, email, password, phone);

		FileDatabase.saveSeller(seller);

		System.out.println("\nSeller Registration Successful!");
	}

	// ================= LOGIN =================

	public static Seller login(Scanner sc) {

		System.out.println("\n===== SELLER LOGIN =====");

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Password: ");
		String password = sc.nextLine();

		Seller seller = FileDatabase.findSeller(email, password);

		if (seller != null) {

			System.out.println("\nSeller Login Successful!");
			System.out.println("Welcome, " + seller.getName());

			return seller;
		}

		System.out.println("\nInvalid Email or Password!");

		return null;
	}
}