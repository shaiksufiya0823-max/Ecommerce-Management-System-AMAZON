package ecommerce;

import java.util.Scanner;

public class Customer extends User {

	public Customer(String name, String email, String password, String phone, String address) {

		super(name, email, password, phone, address);
	}

	// ================= REGISTRATION =================

	public static void register(Scanner sc) {

		System.out.println("\n===== CUSTOMER REGISTRATION =====");

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

			if (FileDatabase.emailExists(email)) {

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

			System.out.println("Invalid Phone Number!");
			System.out.println("Phone number must contain 10 digits and start with 6, 7, 8 or 9.");
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
			System.out.println("Password must contain:");
			System.out.println("1. Minimum 8 characters");
			System.out.println("2. One uppercase letter");
			System.out.println("3. One lowercase letter");
			System.out.println("4. One number");
			System.out.println("5. One special character");
		}

		// ================= ADDRESS =================

		System.out.print("Enter Address: ");
		String address = sc.nextLine();

		Customer customer = new Customer(name, email, password, phone, address);

		FileDatabase.saveCustomer(customer);

		System.out.println("\nRegistration Successful!");
	}

	// ================= LOGIN =================

	public static Customer login(Scanner sc) {

		System.out.println("\n===== CUSTOMER LOGIN =====");

		System.out.print("Enter Email: ");
		String email = sc.nextLine();

		System.out.print("Enter Password: ");
		String password = sc.nextLine();

		User user = FileDatabase.findCustomer(email, password);

		if (user != null) {

			System.out.println("\nLogin Successful!");
			System.out.println("Welcome, " + user.getName());

			return new Customer(user.getName(), user.getEmail(), user.getPassword(), user.getPhone(),
					user.getAddress());
		}

		System.out.println("\nInvalid Email or Password!");

		return null;
	}
}
