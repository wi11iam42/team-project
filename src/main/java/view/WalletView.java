package view;

import interface_adapter.Wallet.WalletController;
import interface_adapter.Wallet.WalletViewModel;
import interface_adapter.Wallet.WalletState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WalletView extends JPanel implements PropertyChangeListener {

    private final WalletViewModel viewModel;
    private final WalletController controller;

    private final JLabel balanceLabel = new JLabel("Balance: $0", SwingConstants.CENTER);
    private final JTextArea historyArea = new JTextArea(10, 30);
    private final JTextField amountField = new JTextField(10);
    private final JButton depositButton = new JButton("Deposit");
    private final JButton withdrawButton = new JButton("Withdraw");
    private final JButton backButton = new JButton("Back");

    public WalletView(WalletViewModel viewModel, WalletController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setName("wallet");

        setupTopPanel();
        setupCenterPanel();
        setupBottomPanel();

        historyArea.setEditable(false);
    }

    private void setupTopPanel() {
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(balanceLabel, BorderLayout.NORTH);
    }

    private void setupCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel amountText = new JLabel("Amount:");
        amountText.setFont(new Font("Arial", Font.PLAIN, 18));

        depositButton.addActionListener(e ->
                controller.executeDeposit(amountField.getText())
        );
        withdrawButton.addActionListener(e ->
                controller.executeWithdraw(amountField.getText())
        );

        panel.add(amountText);
        panel.add(amountField);
        panel.add(depositButton);
        panel.add(withdrawButton);

        add(panel, BorderLayout.CENTER);
    }

    private void setupBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(historyArea);
        bottom.add(scrollPane, BorderLayout.CENTER);

        bottom.add(backButton, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        WalletState state = viewModel.getState();

        balanceLabel.setText("Balance: " + state.getBalance());

        historyArea.setText(state.getHistoryText());

        amountField.setText("");

        if (!state.getError().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
