package usecase.Wallet;

public interface WalletInputBoundary {
    void deposit(WalletInputData inputData);

    void withdraw(WalletInputData inputData);
}
