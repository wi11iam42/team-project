package interface_adapter.Wallet;

import interface_adapter.ViewModel;

public class WalletViewModel extends ViewModel<WalletState> {

    public WalletViewModel() {
        super("wallet");
        setState(new WalletState());
    }
}
