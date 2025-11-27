package use_case.Wallet;

public interface WalletInputBoundary {
    void deposit(WalletInputData inputData);
    void withdraw(WalletInputData inputData);
}
