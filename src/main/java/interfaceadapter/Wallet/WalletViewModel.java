package interfaceadapter.Wallet;

import interfaceadapter.ViewModel;

public class WalletViewModel extends ViewModel<WalletState> {

    public WalletViewModel() {
        super("wallet");
        setState(new WalletState());
    }
}
