// AccountOperations.java
package bank;

import exceptions.InsufficientFundsException;

public interface AccountOperations {
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientFundsException;
    double getBalance();
}
