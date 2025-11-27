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

    private final JButton depositButton = new GoldButton("💵 DEPOSIT");
    private final JButton withdrawButton = new GoldButton("💸 WITHDRAW");
    private final JButton backButton = new GoldButton("⬅ BACK");

    private final JLabel balanceLabel = new JLabel("", SwingConstants.CENTER);
    private final JTextArea historyArea = new JTextArea();
    private final JTextField amountField = new JTextField(15);

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
        top.setOpaque(false);
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

        JLabel amountLabel = new JLabel("▢ Amount:");
        amountLabel.setFont(new Font("Bahnschrift", Font.BOLD, 55));
        amountLabel.setForeground(new Color(255, 215, 0));
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        amountField.setFont(new Font("Bahnschrift", Font.PLAIN, 50));
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setMaximumSize(new Dimension(600, 80));
        amountField.setPreferredSize(new Dimension(600, 80));
        amountField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel amountRow = new JPanel();
        amountRow.setOpaque(false);
        amountRow.add(amountLabel);
        amountRow.add(Box.createHorizontalStrut(20));
        amountRow.add(amountField);

        depositButton.setPreferredSize(new Dimension(420, 120));
        withdrawButton.setPreferredSize(new Dimension(420, 120));

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
        bottom.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        historyArea.setFont(new Font("Bahnschrift", Font.PLAIN, 30));
        historyArea.setForeground(new Color(255, 215, 0));
        historyArea.setBackground(new Color(30, 30, 30));
        historyArea.setEditable(false);
        historyArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setPreferredSize(new Dimension(1600, 350));
        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(255, 215, 0), 5),
                        "Transaction History",
                        0, 0,
                        new Font("Bahnschrift", Font.BOLD, 40),
                        new Color(255, 215, 0)
                )
        );

        JPanel backPanel = new JPanel();
        backPanel.setOpaque(false);
        backButton.setPreferredSize(new Dimension(420, 120));
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

    static class GoldButton extends JButton {

        private static final Color GOLD = new Color(255, 215, 0);

        public GoldButton(String text) {
            super(text);
            setForeground(GOLD);
            setFont(new Font("Segoe UI Emoji", Font.BOLD, 40));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.setColor(GOLD);
            g2.setStroke(new BasicStroke(4));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.dispose();

            super.paintComponent(g);
        }
    }
}
