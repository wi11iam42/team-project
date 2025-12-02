package interfaceadapter.Wallet;

import usecase.Wallet.WalletOutputBoundary;
import usecase.Wallet.WalletOutputData;

public class WalletPresenter implements WalletOutputBoundary {

    private final WalletViewModel walletViewModel;

    public WalletPresenter(WalletViewModel walletViewModel) {
        this.walletViewModel = walletViewModel;
    }

    @Override
    public void prepareSuccessView(WalletOutputData outputData) {
        WalletState state = walletViewModel.getState();
        state.setUsername(outputData.getUsername());
        state.setBalance(outputData.getBalance());
        state.setMessage(outputData.getMessage());
        state.setError("");

        state.appendHistory(outputData.getMessage());

        walletViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        WalletState state = walletViewModel.getState();
        state.setError(errorMessage);
        walletViewModel.firePropertyChange();
    }
}
