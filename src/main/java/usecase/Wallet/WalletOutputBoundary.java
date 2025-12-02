package usecase.Wallet;

public interface WalletOutputBoundary {
    void prepareSuccessView(WalletOutputData outputData);

    void prepareFailView(String errorMessage);
}
