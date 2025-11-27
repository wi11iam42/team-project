package view;

import interface_adapter.Wallet.WalletController;
import interface_adapter.Wallet.WalletState;
import interface_adapter.Wallet.WalletViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WalletView extends JPanel implements PropertyChangeListener {

    private final WalletViewModel viewModel;
    private final WalletController controller;

    private final JLabel balanceLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextArea historyArea = new JTextArea();
    private final JTextField amountField = new JTextField(15);
    private final JButton depositButton = new JButton("💵 DEPOSIT");
    private final JButton withdrawButton = new JButton("💸 WITHDRAW");
    private final JButton backButton = new JButton("⬅ BACK");

    public WalletView(WalletViewModel viewModel, WalletController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));

        setupTop();
        setupCenter();
        setupBottom();
    }

    private void setupTop() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0, 0, 0, 180));
        top.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        balanceLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 80));
        balanceLabel.setForeground(new Color(255, 215, 0));

        top.add(balanceLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);
    }

    private void setupCenter() {
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel amountLabel = new JLabel("💰 Amount:");
        amountLabel.setFont(new Font("Bahnschrift", Font.BOLD, 55));
        amountLabel.setForeground(new Color(255, 215, 0));

        amountField.setFont(new Font("Bahnschrift", Font.PLAIN, 50));
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setMaximumSize(new Dimension(600, 80));
        amountField.setPreferredSize(new Dimension(600, 80));

        JPanel amountRow = new JPanel();
        amountRow.setOpaque(false);
        amountRow.add(amountLabel);
        amountRow.add(amountField);

        depositButton.setPreferredSize(new Dimension(350, 100));
        withdrawButton.setPreferredSize(new Dimension(350, 100));

        depositButton.addActionListener(e -> controller.executeDeposit(amountField.getText()));
        withdrawButton.addActionListener(e -> controller.executeWithdraw(amountField.getText()));

        JPanel buttonRow = new JPanel();
        buttonRow.setOpaque(false);
        buttonRow.add(depositButton);
        buttonRow.add(Box.createHorizontalStrut(40));
        buttonRow.add(withdrawButton);

        center.add(Box.createVerticalStrut(60));
        center.add(amountRow);
        center.add(Box.createVerticalStrut(60));
        center.add(buttonRow);

        add(center, BorderLayout.CENTER);
    }

    private void setupBottom() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);

        historyArea.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
        historyArea.setForeground(new Color(255, 215, 0));
        historyArea.setBackground(new Color(35, 35, 35));
        historyArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 5),
                "Transaction History",
                0, 0,
                new Font("Bahnschrift", Font.BOLD, 40),
                new Color(255, 215, 0)
        ));

        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);

        backButton.setPreferredSize(new Dimension(350, 100));
        backPanel.add(backButton);

        bottom.add(scrollPane, BorderLayout.CENTER);
        bottom.add(backPanel, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        WalletState state = viewModel.getState();
        balanceLabel.setText("💎 Balance: $" + state.getBalance());
        historyArea.setText(state.getHistoryText());
        amountField.setText("");
    }
}
