package ecommerce;

import java.util.Scanner;

public class Payment {

	public static boolean makePayment(Scanner sc, String customerEmail, String orderId, double amount) {

		System.out.println("\n===== PAYMENT =====");

		System.out.println("Order ID : " + orderId);

		System.out.println("Amount   : ₹" + amount);

		System.out.println("\nSelect Payment Method");

		System.out.println("1. UPI");
		System.out.println("2. Card");
		System.out.println("3. Net Banking");
		System.out.println("4. Wallet");

		System.out.print("Enter Choice: ");

		int choice = sc.nextInt();
		sc.nextLine();

		String paymentMethod = "";

		switch (choice) {

		case 1:

			paymentMethod = "UPI";

			System.out.print("Enter UPI ID: ");
			String upi = sc.nextLine();

			if (upi.isEmpty()) {

				System.out.println("Invalid UPI ID.");

				return false;
			}

			break;

		case 2:

			paymentMethod = "Card";

			System.out.print("Enter Card Number: ");

			String card = sc.nextLine();

			if (card.length() != 16) {

				System.out.println("Invalid Card Number.");

				return false;
			}

			break;

		case 3:

			paymentMethod = "Net Banking";

			System.out.print("Enter Bank Name: ");

			String bank = sc.nextLine();

			if (bank.isEmpty()) {

				System.out.println("Invalid Bank Name.");

				return false;
			}

			break;

		case 4:

			paymentMethod = "Wallet";

			System.out.print("Enter Wallet Name: ");

			String wallet = sc.nextLine();

			if (wallet.isEmpty()) {

				System.out.println("Invalid Wallet.");

				return false;
			}

			break;

		default:

			System.out.println("Invalid Payment Choice.");

			return false;
		}

		System.out.println("\nProcessing Payment...");

		System.out.println("Payment Successful!");

		FileDatabase.savePayment(orderId, customerEmail, amount, paymentMethod, "SUCCESS");

		return true;
	}
}