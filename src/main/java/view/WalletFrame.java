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

        walletView = new WalletView(vm, controller);
        setContentPane(walletView);
        walletView.setBackground(new Color(20,20,20));

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        Rectangle bounds = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
        setBounds(bounds);

        setVisible(true);
    }

    public WalletView getWalletView() {
        return walletView;
    }
}
