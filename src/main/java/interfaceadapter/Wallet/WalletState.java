package interfaceadapter.Wallet;

public class WalletState {
    private String username = "";
    private double balance = 0.0;
    private String message = "";
    private String error = "";
    private String historyText = ""; // 显示在 text area 的历史

    public WalletState() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHistoryText() {
        return historyText;
    }

    public void appendHistory(String line) {
        if (historyText.isEmpty()) {
            historyText = line;
        } else {
            historyText += "\n" + line;
        }
    }
}
