package view;

import entity.User;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class WalletFrame extends JFrame {

    private final User user;
    private final JLabel balanceLabelTop;
    private final JLabel balanceLabelCenter;
    private final JTextField amountField;
    private final JTextArea historyArea;
    private final JFrame previousFrame;

    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.CANADA);

    public WalletFrame(User user, JFrame previousFrame) {

        this.user = user;
        this.previousFrame = previousFrame;

        setTitle("Wallet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        JLabel title = new JLabel("💰 WALLET 💰", SwingConstants.CENTER);
        title.setFont(new Font("Montserrat", Font.BOLD, 70));
        title.setForeground(new Color(255, 215, 0));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        balanceLabelTop = new JLabel("Balance: " + CURRENCY.format(user.getBalance()), SwingConstants.CENTER);
        balanceLabelTop.setFont(new Font("Montserrat", Font.BOLD, 50));
        balanceLabelTop.setForeground(new Color(255, 215, 0));
        balanceLabelTop.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLabelTop.setBorder(BorderFactory.createEmptyBorder(10, 0, 40, 0));

        balanceLabelCenter = new JLabel("💵 " + CURRENCY.format(user.getBalance()), SwingConstants.CENTER);
        balanceLabelCenter.setFont(new Font("Montserrat", Font.BOLD, 80));
        balanceLabelCenter.setForeground(new Color(255, 215, 0));
        balanceLabelCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceLabelCenter.setBorder(BorderFactory.createEmptyBorder(10, 0, 50, 0));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        inputPanel.setOpaque(false);

        JLabel amountText = new JLabel("Amount:");
        amountText.setFont(new Font("Montserrat", Font.BOLD, 40));
        amountText.setForeground(new Color(255, 215, 0));

        amountField = new JTextField(10);
        amountField.setFont(new Font("Montserrat", Font.PLAIN, 35));
        amountField.setBackground(Color.BLACK);
        amountField.setForeground(new Color(255, 215, 0));
        amountField.setCaretColor(new Color(255, 215, 0));
        amountField.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));

        JButton depositBtn = createButton("Deposit 💵");
        JButton withdrawBtn = createButton("Withdraw 💸");

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());

        inputPanel.add(amountText);
        inputPanel.add(amountField);
        inputPanel.add(depositBtn);
        inputPanel.add(withdrawBtn);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Montserrat", Font.PLAIN, 30));
        historyArea.setForeground(new Color(255, 215, 0));
        historyArea.setBackground(Color.BLACK);
        historyArea.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 3));

        JButton returnBtn = new JButton("Return to Home");
        returnBtn.setFont(new Font("Montserrat", Font.BOLD, 40));
        returnBtn.setBackground(Color.BLACK);
        returnBtn.setForeground(new Color(255, 215, 0));
        returnBtn.setFocusPainted(false);
        returnBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 4));
        returnBtn.addActionListener(e -> {
            previousFrame.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(returnBtn, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(balanceLabelTop);
        centerPanel.add(balanceLabelCenter);
        centerPanel.add(inputPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateUI("Wallet opened");
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Montserrat", Font.BOLD, 35));
        btn.setForeground(new Color(255, 215, 0));
        btn.setBackground(Color.BLACK);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 4));
        btn.setFocusPainted(false);
        return btn;
    }

    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) return;
            user.deposit(amount);
            updateUI("Deposited " + CURRENCY.format(amount));
        } catch (Exception ignored) {}
    }

    private void withdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) return;
            if (!user.checkwithdraw(amount)) return;
            user.withdraw(amount);
            updateUI("Withdrew " + CURRENCY.format(amount));
        } catch (Exception ignored) {}
    }

    private void updateUI(String text) {
        balanceLabelTop.setText("Balance: " + CURRENCY.format(user.getBalance()));
        balanceLabelCenter.setText("💵 " + CURRENCY.format(user.getBalance()));
        historyArea.append("• " + text + "\n");
        amountField.setText("");
    }
}
