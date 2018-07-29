package com.cg.WalletApplication.pl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.cg.WalletApplication.Exception.BankException;
import com.cg.WalletApplication.Exception.IBankException;
import com.cg.WalletApplication.bean.Customer;
import com.cg.WalletApplication.service.IWalletService;
import com.cg.WalletApplication.service.WalletServiceImpl;

public class WalletUi {
	static IWalletService iWalletService = new WalletServiceImpl();
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		int choice;
		try {
			do {
				chooseOperation();
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					addCustomer();
					break;
				case 2:
					showBalance();
					break;
				case 3:
					depositAmount();
					break;
				case 4:
					withdrawAmount();
					break;
				case 5:
					fundTransfer();
					break;
				case 6:
					printMyTransactions();

					break;
				case 7:
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Enter your choice correctly");
					break;

				}
			} while (choice != 7);
		} catch (InputMismatchException exception) {
			try {
				throw new BankException(IBankException.inputMismatch);
			} catch (BankException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void chooseOperation() {
		System.out.println("Enter 1 to Create Account");
		System.out.println("Enter 2 to show balance");
		System.out.println("Enter 3 to deposit amount");
		System.out.println("Enter 4 to withdraw amount");
		System.out.println("Enter 5 for fund transfer");
		System.out.println("Enter 6 to print your transactions");
		System.out.println("Enter 7 to exit the application");

	}

	private static void addCustomer() {
		try {
			Customer customer = new Customer();
			System.out.println("Enter Customer  Name");
			String name = scanner.nextLine();
			name += scanner.nextLine();
			iWalletService.checkName(name);
			customer.setName(name);
			System.out.println("Enter Mobile Number");
			String mobileNumber = scanner.next();
			iWalletService.checkMobileNumber(mobileNumber);
			customer.setMobileNumber(mobileNumber);
			System.out.println("Enter Password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			customer.setPassword(password);
			System.out.println("Enter Email Id");
			String emailId = scanner.next();
			iWalletService.checkEmail(emailId);
			customer.setEmailId(emailId);
			String result = iWalletService.addCustomer(customer);
			if (result != null)
				System.out.println("Account created with Account Number " + result);
		} catch (BankException bankException) {
			System.out.println(bankException.getMessage());
		}
	}

	private static void showBalance() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.showBalance(mobileNum, password);
			if (customer != null) {
				System.out.println("Available balance is " + customer.getWallet().getBalance());
			}
		} catch (BankException bankException) {

			System.out.println(bankException.getMessage());
		}
	}

	private static void depositAmount() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				System.out.println("Enter amount to deposit ");
				BigDecimal amount = scanner.nextBigDecimal();
				if (iWalletService.checkEnteredAmount(amount)) {
					iWalletService.deposit(customer, amount);
					System.out.println("Deposited");
				}
			}
		} catch (BankException bankException) {

			System.out.println(bankException.getMessage());
		}

	}

	private static void withdrawAmount() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				System.out.println("Enter the amount to withdraw");
				BigDecimal amount = scanner.nextBigDecimal();
				if (iWalletService.checkEnteredAmount(amount)) {
					boolean result = iWalletService.withDraw(customer, amount);
					if (result == true) {
						System.out.println("Amount is succesfully withdrawn and current balance is "
								+ customer.getWallet().getBalance());

					}
				}
			}
		} catch (BankException bankException) {
			System.out.println(bankException.getMessage());
		}

	}

	private static void printMyTransactions() {
		try {
			System.out.println("Enter your mobile number");
			String mobileNum = scanner.next();
			iWalletService.checkMobileNumber(mobileNum);
			System.out.println("Enter the password");
			String password = scanner.next();
			iWalletService.checkPassword(password);
			Customer customer = iWalletService.check(mobileNum, password);
			if (customer != null) {
				TreeMap<LocalDateTime, String> transactions = customer.getTransactions();
				for (Map.Entry<LocalDateTime, String> trans : transactions.entrySet()) {
					System.out.println(trans.getKey() + " @ " + trans.getValue());
				}
			}

		} catch (BankException bankException) {
			System.out.println(bankException.getMessage());
		}

	}

	private static void fundTransfer() {
		try {
			System.out.println("Enter Receivers mobile number ");
			String receiverMobile = scanner.next();
			iWalletService.checkMobileNumber(receiverMobile);
			boolean result = iWalletService.isFound(receiverMobile);
			if (result) {
				System.out.println("Enter sender mobile number");
				String senderMobile = scanner.next();
				iWalletService.checkMobileNumber(senderMobile);
				System.out.println("Enter senders password");
				String password = scanner.next();
				iWalletService.checkPassword(password);
				Customer customer = iWalletService.check(senderMobile, password);
				if (customer != null) {
					System.out.println("Enter the amount to transfer");
					BigDecimal amount = scanner.nextBigDecimal();
					if (iWalletService.checkEnteredAmount(amount)) {
						boolean output = iWalletService.transfer(senderMobile, receiverMobile, amount);
						if (output == true) {
							System.out.println("Amount is succesfully transferred to " + receiverMobile
									+ " and current balance is " + customer.getWallet().getBalance());
						}

					}
				}
			}

		} catch (BankException bankException) {
			System.out.println(bankException.getMessage());
		} catch (InterruptedException interruptedException) {
			System.out.println(interruptedException.getMessage());
		}
	}

}
