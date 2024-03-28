package bank;
import java.io.*;
import java.util.*;

import exceptions.InsufficientFundsException;

public class Bank implements Serializable {
	private static final long serialVersionUID = -5898988048051297447L;
	private Map<Integer, BankAccount> accounts;
    private int accountIdCounter;

    public Bank() {
        this.accounts = new HashMap<>();
        this.accountIdCounter = 1;
    }

    public Bank(Map<Integer, BankAccount> accounts, int accountIdCounter) {
        this.accounts = accounts;
        this.accountIdCounter = accountIdCounter;
    }

    public int createAccount(UserInfo userInfo, double initialBalance) {
        int accountId = accountIdCounter++;
        BankAccount account = new BankAccount(userInfo, initialBalance);
        accounts.put(accountId, account);
        return accountId;
    }

    public BankAccount getAccount(int accountId) {
        return accounts.get(accountId);
    }

    public List<Integer> getAllAccountIds() {
        return new ArrayList<>(accounts.keySet());
    }
    
    public void transfer(int fromAccountId, int toAccountId, double amount) throws InsufficientFundsException {
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        else {
        	fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }
    
    public void saveAccountsToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Map.Entry<Integer, BankAccount> entry : accounts.entrySet()) {
                writer.println("Account ID: " + entry.getKey());
                BankAccount account = entry.getValue();
                UserInfo userInfo = account.getUserInfo();
                writer.println("Name: " + userInfo.getName());
                writer.println("Age: " + userInfo.getAge());
                writer.println("Phone Number: " + userInfo.getPhoneNumber());
                writer.println("Email: " + userInfo.getEmail());
                writer.println("Aadhaar Number: " + userInfo.getAadhaarNumber());
                writer.println("Balance: " + account.getBalance());
                writer.println(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bank loadAccountsFromFile(String fileName) {
        Bank bank = new Bank();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Account ID: ")) {
                    int accountId = Integer.parseInt(line.substring("Account ID: ".length()));
                    String name = reader.readLine().substring("Name: ".length());
                    int age = Integer.parseInt(reader.readLine().substring("Age: ".length()));
                    String phoneNumber = reader.readLine().substring("Phone Number: ".length());
                    String email = reader.readLine().substring("Email: ".length());
                    String aadhaarNumber = reader.readLine().substring("Aadhaar Number: ".length());
                    double balance = Double.parseDouble(reader.readLine().substring("Balance: ".length()));
                    reader.readLine();
                    UserInfo userInfo = new UserInfo(name, age, phoneNumber, email, aadhaarNumber);
                    bank.accounts.put(accountId, new BankAccount(userInfo, balance));
                    bank.accountIdCounter = Math.max(bank.accountIdCounter, accountId + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bank;
    }
    
    public void deleteAccount(int accountId) {
        if(accounts.containsKey(accountId)) {
            accounts.remove(accountId);
            System.out.println("\nAccount with ID " + accountId + " has been deleted.");
        } else {
            System.out.println("\nAccount with ID " + accountId + " does not exist.");
        }
    }

}
