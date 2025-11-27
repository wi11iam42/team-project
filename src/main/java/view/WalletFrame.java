package view;

import interface_adapter.Wallet.WalletController;
import interface_adapter.Wallet.WalletViewModel;

import javax.swing.*;
import java.awt.*;

public class WalletFrame extends JFrame {

    private final WalletView walletView;

    public WalletFrame(WalletViewModel vm, WalletController controller) {
        setTitle("Wallet");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ★★ FULLSCREEN ★★
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);   // Keep title bar

        walletView = new WalletView(vm, controller);
        setContentPane(walletView);

        // Optional: background color
        walletView.setBackground(new Color(20,20,20));

        setVisible(true);
    }

    public WalletView getWalletView() {
        return walletView;
    }
}
