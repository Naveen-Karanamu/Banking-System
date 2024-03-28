// BankAccount.java
package bank;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import exceptions.InsufficientFundsException;

public class BankAccount implements AccountOperations, Serializable {
	private static final long serialVersionUID = -2218768535367158258L;
	private UserInfo userInfo;
    private double balance;
    private Lock lock;

    public BankAccount(UserInfo userInfo, double initialBalance) {
        this.userInfo = userInfo;
        this.balance = initialBalance;
        this.lock = new ReentrantLock();
    }
    
    public Lock getLock() {
        return lock;
    }

    // Getter for user information
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public void deposit(double amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
            } else {
                throw new InsufficientFundsException("Insufficient funds, available balace: "+ balance);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
