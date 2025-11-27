package use_case.Wallet;

public class WalletOutputData {

    private final String username;
    private final double balance;
    private final String message;

    public WalletOutputData(String username, double balance, String message) {
        this.username = username;
        this.balance = balance;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public String getMessage() {
        return message;
    }
}
