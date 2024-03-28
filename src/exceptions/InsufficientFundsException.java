package exceptions;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = -4951721617151705345L;

	public InsufficientFundsException(String message) {
        super(message);
    }
}
