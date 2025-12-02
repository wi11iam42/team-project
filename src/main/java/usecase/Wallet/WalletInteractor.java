package usecase.Wallet;

import dataaccess.UserDataAccessInterface;
import entity.User;

public class WalletInteractor implements WalletInputBoundary {

    private final UserDataAccessInterface userDAO;
    private final WalletOutputBoundary presenter;

    public WalletInteractor(UserDataAccessInterface userDAO,
                            WalletOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void deposit(WalletInputData inputData) {
        handle(inputData, true);
    }

    @Override
    public void withdraw(WalletInputData inputData) {
        handle(inputData, false);
    }

    private void handle(WalletInputData inputData, boolean isDeposit) {
        final String amountText = inputData.getAmountText();
        final double amount;

        try {
            amount = Double.parseDouble(amountText);
        }
        catch (NumberFormatException e) {
            presenter.prepareFailView("Invalid number.");
            return;
        }

        if (amount <= 0) {
            presenter.prepareFailView("Please enter a positive amount.");
            return;
        }

        final User user = userDAO.get(inputData.getUsername());

        if (!isDeposit) {
            if (!user.checkwithdraw(amount)) {
                presenter.prepareFailView("Insufficient funds!");
                return;
            }
            user.withdraw(amount);
        }
        else {
            user.deposit(amount);
        }

        userDAO.save(user);

        final String msg = (isDeposit ? "Deposited " : "Withdrew ") + amount;
        final WalletOutputData out = new WalletOutputData(
                user.getUsername(),
                user.getBalance(),
                msg);

        presenter.prepareSuccessView(out);
    }
}
