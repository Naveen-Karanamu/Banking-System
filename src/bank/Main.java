package bank;

import exceptions.InsufficientFundsException;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = Bank.loadAccountsFromFile("bank_data.txt");
        
        System.out.println("Hello! Welcome to the BT Bank 😊");
        while (true) {        	
            System.out.println("\nBanking System Menu:");
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Show Particular Account Details");
            System.out.println("6. Show all the Existing Account IDs & Names");
            System.out.println("7. Delete an Account");
            System.out.println("8. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = validateChoice(scanner);

            switch (choice) {
                case 1:
                    createAccount(bank, scanner);
                    bank.saveAccountsToFile("bank_data.txt");
                    break;
                case 2:
                    performDeposit(bank, scanner);
                    bank.saveAccountsToFile("bank_data.txt");
                    break;
                case 3:
                    performWithdraw(bank, scanner);
                    bank.saveAccountsToFile("bank_data.txt");
                    break;
                case 4:
                    performTransfer(bank, scanner);
                    bank.saveAccountsToFile("bank_data.txt");
                    break;
                case 5:
                    showAccountDetails(bank, scanner);
                    break;
                case 6:
                    showExistingAccountIds(bank);
                    break;
                case 7:
                    deleteAccount(bank, scanner);
                    break;
                case 8:
                    System.out.println("\nThank you! Please visit our Bank again !!!");
                    bank.saveAccountsToFile("bank_data.txt");
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice.");
            }
        }
    }

    private static int validateChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("\nInvalid input! Please enter a number.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        if (choice < 1 || choice > 8) {
            System.out.println("\nInvalid choice! Please enter a number between 1 and 8.");
            return validateChoice(scanner);
        }
        return choice;
    }

	private static void createAccount(Bank bank, Scanner scanner) {
		
//		int maxAccountId = bank.getAllAccountIds().stream().max(Integer::compare).orElse(0);
//	    int nextAccountId = maxAccountId + 1;
	    
	    String name = null;
	    int age = 0;
	    String phoneNumber = null;
	    String email = null;
	    String aadhaarNumber = null;
	    double initialBalance = 0;
	    
	    boolean validInput = false;
	    while (!validInput) {
	        System.out.println("\nEnter user information:");
	        if (name == null) {
	            System.out.print("Name: ");
	            name = scanner.next();
	            if (!name.matches("[a-zA-Z]+")) {
	                System.out.println("\nInvalid name. Name should contain only alphabets.");
	                name = null;
	                continue;
	            }
	        }
	        
	        if (age == 0) {
	            System.out.print("Age: ");
	            try {	                
	            	age = scanner.nextInt();
	                if (age < 18 || age > 150) {
	                	age=0;
	                    throw new IllegalArgumentException("\nInvalid age. Age should be between 18 and 150.");
	                }
	                
	            } catch (Exception e) {
	                System.out.println("\nInvalid age input. Age should be an integer between 18 and 150.");
	                scanner.nextLine();
	                continue;
	            }
	        }
	        
	        if (phoneNumber == null) {
	            System.out.print("Phone Number: ");
	            phoneNumber = scanner.next();
	            if (!phoneNumber.matches("(?!0)\\d{10}")) {
	                System.out.println("\nInvalid phone number. Phone number shouldn't start with 0");
	                phoneNumber = null;
	                continue;
	            }
	            if (!phoneNumber.matches("\\d{10}")) {
	                System.out.println("\nInvalid phone number. Phone number should contain 10 digits.");
	                phoneNumber = null;
	                continue;
	            }
	        }
	        
	        if (email == null) {
	            System.out.print("Email: ");
	            email = scanner.next();
	            if (!email.matches("[\\w.-]+@\\w+\\.\\w+")) {
	                System.out.println("\nInvalid email. Email should end with '@<some domain>.com'.");
	                email = null;
	                continue;
	            }
	        }
	        
	        if (aadhaarNumber == null) {
	            System.out.print("Aadhaar Number: ");
	            aadhaarNumber = scanner.next();
	            if (!aadhaarNumber.matches("\\d{12}")) {
	                System.out.println("\nInvalid Aadhaar number. Aadhaar number should contain exactly 12 digits.");
	                aadhaarNumber = null;
	                continue;
	            }
	            if (aadhaarNumber.startsWith("0")) {
	                System.out.println("\nInvalid Aadhaar number. Aadhaar number shouldn't start with 0.");
	                aadhaarNumber = null;
	                continue;
	            }
	        }


	        if (initialBalance == 0) {
	            System.out.print("Enter initial balance you want to deposite: ");
	            try {
	                initialBalance = scanner.nextDouble();
	            } catch (Exception e) {
	                System.out.println("\nInvalid initial balance input. Please enter a valid numeric value.");
	                scanner.nextLine();
	                initialBalance = 0;
	                continue;
	            }
	        }

	        validInput = true;
	        UserInfo userInfo = new UserInfo(name, age, phoneNumber, email, aadhaarNumber);
	        int accountId = bank.createAccount(userInfo, initialBalance);
	        System.out.println("\nAccount created with ID: " + accountId);
	    }
	}
	
	private static int validateAccountId(Bank bank, Scanner scanner) {
	    while (true) {
	        if (scanner.hasNextInt()) {
	            int accountId = scanner.nextInt();
	            if (bank.getAccount(accountId) != null) {
	                return accountId;
	            }
	        }
	        System.out.println("\nInvalid account ID! Please enter a valid ID.");
	        scanner.nextLine();
	    }
	}

	private static double validateAmount(Scanner scanner) {
	    while (true) {
	        if (scanner.hasNextDouble()) {
	            double amount = scanner.nextDouble();
	            if (amount > 0) {
	                return amount;
	            }
	        }
	        System.out.println("\nInvalid amount! Please enter a positive number.");
	        scanner.nextLine();
	    }
	}


	private static void performDeposit(Bank bank, Scanner scanner) {
	    while (true) {
	        System.out.print("Enter account ID to which you want to deposite: ");
	        int accountId = validateAccountId(bank, scanner);
	        System.out.print("Enter deposit amount: ");
	        double amount = validateAmount(scanner);
	        BankAccount account = bank.getAccount(accountId);
	        if (account != null) {
	            account.deposit(amount);
	            System.out.println("\nDeposit successful. Updated balance: " + account.getBalance());
	            break;
	        } else {
	            System.out.println("\nAccount not found. Please enter a valid account ID.");
	        }
	    }
	}

	private static void performWithdraw(Bank bank, Scanner scanner) {
	    while (true) {
	        System.out.print("Enter account ID from which you want to withdraw: ");
	        int accountId = validateAccountId(bank, scanner);
	        System.out.print("\nEnter withdrawal amount: ");
	        double amount = validateAmount(scanner);
	        BankAccount account = bank.getAccount(accountId);
	        if (account != null) {
	            try {
	                account.withdraw(amount);
	                System.out.println("\nWithdrawal successful. Updated balance: " + account.getBalance());
	                break;
	            } catch (InsufficientFundsException e) {
	                System.out.println("\nWithdrawal failed: " + e.getMessage());
	            }
	        } else {
	            System.out.println("\nAccount not found. Please enter a valid account ID.");
	        }
	    }
	}

	private static void performTransfer(Bank bank, Scanner scanner) {
	    while (true) {
	    	try {
		        System.out.print("Enter the Bank account ID from which you want to send the money: ");
		        int fromAccountId = validateAccountId(bank, scanner);
		        System.out.print("Enter Bank account ID to which you want to send the money: ");
		        int toAccountId = validateAccountId(bank, scanner);
		        if(fromAccountId == toAccountId) {
		        	throw new IllegalArgumentException("\nYou are transfering the amount to the same account, please choose a different account to transfer the money");
		        }
	        System.out.print("Enter transfer amount: ");
	        double amount = validateAmount(scanner);

	            bank.transfer(fromAccountId, toAccountId, amount);
	            System.out.println("\nTransfer successful.");
	            break; 
	        } catch (InsufficientFundsException e) {
	            System.out.println("\nTransfer failed: " + e.getMessage());
	        } catch (IllegalArgumentException e) {
	            System.out.println(e.getMessage());
	        }
	    }
	}

	private static void showAccountDetails(Bank bank, Scanner scanner) {
	    while (true) {
	        System.out.print("Enter account ID for which you want to see the details: ");
	        int accountId = validateAccountId(bank, scanner);
	        BankAccount account = bank.getAccount(accountId);
	        if (account != null) {
	            System.out.println("\nAccount Details:");
	            UserInfo userInfo = account.getUserInfo();
	            System.out.println("Name: " + userInfo.getName());
	            System.out.println("Age: " + userInfo.getAge());
	            System.out.println("Phone Number: " + userInfo.getPhoneNumber());
	            System.out.println("Email: " + userInfo.getEmail());
	            System.out.println("Aadhaar Number: " + userInfo.getAadhaarNumber());
	            System.out.println("Balance: " + account.getBalance());
	            break; 
	        } else {
	            System.out.println("\nAccount not found. Please enter a valid account ID.");
	        }
	    }
	}

    private static void showExistingAccountIds(Bank bank) {
        
        List<Integer> accountIds = bank.getAllAccountIds();

        if (accountIds.isEmpty()) {
            System.out.println("\nNo accounts exist yet.");
        } else {
            System.out.println("\nExisting Account IDs:");
            for (int accountId : accountIds) {
                BankAccount account = bank.getAccount(accountId);
                UserInfo userInfo = account.getUserInfo();
                System.out.println("ID: " + accountId + ", Name: " + userInfo.getName());
            }
        }
    }
    
    private static void deleteAccount(Bank bank, Scanner scanner) {
        while(true) {
        	System.out.print("Enter account ID you want to delete: ");
            int accountId = validateAccountId(bank, scanner);
            if(accountId != 0) {
            	bank.deleteAccount(accountId);
                bank.saveAccountsToFile("bank_data.txt");
                break;
            }
            else {
            	System.out.println("\nAccount not found. Please enter a valid account ID.");
            }
        }
    }

}
