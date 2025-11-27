package use_case.Wallet;

public interface WalletOutputBoundary {
    void prepareSuccessView(WalletOutputData outputData);

    void prepareFailView(String errorMessage);
}
