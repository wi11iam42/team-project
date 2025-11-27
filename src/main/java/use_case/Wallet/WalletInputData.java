package use_case.Wallet;

public class WalletInputData {

    private final String username;
    private final String amountText;

    public WalletInputData(String username, String amountText) {
        this.username = username;
        this.amountText = amountText;
    }

    public String getUsername() {
        return username;
    }

    public String getAmountText() {
        return amountText;
    }
}
