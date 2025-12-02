package interfaceadapter.Wallet;

import usecase.Wallet.WalletInputBoundary;
import usecase.Wallet.WalletInputData;

public class WalletController {

    private final WalletInputBoundary interactor;
    private final WalletViewModel viewModel;

    public WalletController(WalletInputBoundary interactor,
                            WalletViewModel viewModel) {
        this.interactor = interactor;
        this.viewModel = viewModel;
    }

    public void executeDeposit(String amountText) {
        String username = viewModel.getState().getUsername();
        WalletInputData inputData = new WalletInputData(username, amountText);
        interactor.deposit(inputData);
    }

    public void executeWithdraw(String amountText) {
        String username = viewModel.getState().getUsername();
        WalletInputData inputData = new WalletInputData(username, amountText);
        interactor.withdraw(inputData);
    }
}
