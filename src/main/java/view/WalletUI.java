package view;

import entity.User;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class WalletUI extends JFrame {
    private final JLabel balanceLabel;
    private final JTextField amountField;
    private final JTextArea historyArea;
    private final User user;


    private final JFrame previousFrame;

    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.CANADA);

    public WalletUI(User user, JFrame previousFrame) {
        this.user = user;
        this.previousFrame = previousFrame;
        BigDecimal balance = new BigDecimal(user.getBalance());
        setTitle("Wallet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));

        balanceLabel = new JLabel("Balance: " + CURRENCY.format(balance), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 50));
        balanceLabel.setForeground(Color.BLACK);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        add(balanceLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        centerPanel.setOpaque(false);

        JLabel amountText = new JLabel("Amount:");
        amountText.setFont(new Font("Arial", Font.PLAIN, 30));

        amountField = new JTextField(10);
        amountField.setFont(new Font("Arial", Font.PLAIN, 28));

        JButton depositBtn = createButton("Deposit");
        JButton withdrawBtn = createButton("Withdraw");

        centerPanel.add(amountText);
        centerPanel.add(amountField);
        centerPanel.add(depositBtn);
        centerPanel.add(withdrawBtn);

        add(centerPanel, BorderLayout.CENTER);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 22));
        JScrollPane scrollPane = new JScrollPane(historyArea);

        JButton returnBtn = new JButton("Return to Home");
        returnBtn.setFont(new Font("Arial", Font.BOLD, 32));
        returnBtn.setBackground(new Color(230, 230, 230));
        returnBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        returnBtn.setFocusPainted(false);
        returnBtn.addActionListener(e -> {
            previousFrame.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(returnBtn, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());

        updateUI("Wallet opened");
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        btn.setFocusPainted(false);
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        return btn;
    }

    private void deposit() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Please enter a positive amount.");
                return;
            }
            user.deposit(amount.doubleValue());
            updateUI("Deposited " + CURRENCY.format(amount));
        } catch (NumberFormatException ex) {
            showError("Invalid input. Please enter a valid number.");
        }
    }

    private void withdraw() {
        try {
            BigDecimal amount = new BigDecimal(amountField.getText());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                showError("Please enter a positive amount.");
                return;
            }
            if (!user.checkwithdraw(amount.doubleValue())) {
                showError("Insufficient funds!");
                return;
            }
            user.withdraw(amount.doubleValue());
            updateUI("Withdrew " + CURRENCY.format(amount));
        } catch (NumberFormatException ex) {
            showError("Invalid input. Please enter a valid number.");
        }
    }

    private void updateUI(String action) {
        balanceLabel.setText("Balance: " + CURRENCY.format(user.getBalance()));
        historyArea.append(action + "\n");
        amountField.setText("");
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
