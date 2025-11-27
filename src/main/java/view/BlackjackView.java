package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import entity.*;
import interface_adapter.Blackjack.BlackjackController;
import interface_adapter.Blackjack.BlackjackPresenter;
import interface_adapter.Blackjack.BlackjackViewModel;
import use_case.*;

public class BlackjackView extends JFrame {

    private final BlackjackController controller;
    private final BlackjackViewModel viewModel;

    private JLabel walletLabel;
    private JTextField walletField;
    private JTextField betField;
    private User current_user;

    // Keep track of bet so payouts are correct
    private double lastBet = 0.0;

    // Controls whether dealer's hole card (and later dealer-drawn cards) are shown
    private boolean dealerRevealed = false;

    private JLabel[][] cardSlots = new JLabel[2][5];
    // row 0 = dealer, row 1 = player

    private JButton dealButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton betSubmit;

    public BlackjackView(User user) {
        // === Build Clean Architecture Stack ===
        viewModel = new BlackjackViewModel();
        BlackjackPresenter presenter = new BlackjackPresenter(viewModel);
        BlackjackGame game = new BlackjackGame(100);
        BlackjackInteractor interactor = new BlackjackInteractor(game, presenter);
        controller = new BlackjackController(interactor);

        current_user = user;

        createAndShowGUI();

        // === Register ViewModel Listener ===
        viewModel.addListener(new BlackjackViewModel.Listener() {
            @Override
            public void onHandsUpdated(Hand player, Hand dealer) {
                SwingUtilities.invokeLater(() -> updateHandsUI(player, dealer));
            }

            @Override
            public void onWalletUpdated(double wallet) {
                SwingUtilities.invokeLater(() ->
                        walletField.setText(String.format("$%.2f", current_user.getBalance())));
            }

            @Override
            public void onResult(GameResult result) {
                dealerRevealed = true;

                SwingUtilities.invokeLater(() -> {
                    updateHandsUI(
                            viewModel.getLastPlayer(),
                            viewModel.getLastDealer()
                    );
                    handleResult(result);
                });
            }
        });
    }


    private void createAndShowGUI() {
        setTitle("Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLayout(new BorderLayout(10, 10));

        // ================= LEFT PANEL =================
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        walletLabel = new JLabel("Wallet:");
        walletField = new JTextField(String.format("$%.2f", current_user.getBalance()));
        walletField.setEditable(false);

        JLabel betLabel = new JLabel("Bet amount:");
        betField = new JTextField("10");
        betSubmit = new JButton("Submit Bet");

        betSubmit.addActionListener(e -> {
            double betAmount;
            try {
                betAmount = Double.parseDouble(betField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric bet amount");
                return;
            }

            if (betAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid bet amount");
                return;
            }
            if (betAmount > current_user.getBalance()) {
                JOptionPane.showMessageDialog(this, "Bet exceeds wallet balance");
                return;
            }

            lastBet = betAmount;
            current_user.withdraw(lastBet);
            walletField.setText(String.format("$%.2f", current_user.getBalance()));

            dealerRevealed = false;

            controller.onDeal(lastBet);

            dealButton.setEnabled(false);
            betSubmit.setEnabled(false);
            betField.setEditable(false);
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        });

        JPanel betPanel = new JPanel(new BorderLayout(5, 5));
        betPanel.add(betField, BorderLayout.CENTER);
        betPanel.add(betSubmit, BorderLayout.EAST);

        leftPanel.add(walletLabel);
        leftPanel.add(walletField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(betLabel);
        leftPanel.add(betPanel);

        // ================= CENTER (TABLE) =================
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBorder(BorderFactory.createTitledBorder("Blackjack Table"));
        tablePanel.setBackground(new Color(0, 100, 0));

        JPanel dealerPanel = new JPanel(new FlowLayout());
        dealerPanel.setOpaque(false);
        dealerPanel.add(new JLabel("Dealer's Hand:"));

        for (int i = 0; i < 5; i++) {
            JLabel card = createCardSlot();
            cardSlots[0][i] = card;
            dealerPanel.add(card);
        }

        JPanel playerPanel = new JPanel(new FlowLayout());
        playerPanel.setOpaque(false);
        playerPanel.add(new JLabel("Your Hand:"));

        for (int i = 0; i < 5; i++) {
            JLabel card = createCardSlot();
            cardSlots[1][i] = card;
            playerPanel.add(card);
        }

        JPanel actionPanel = new JPanel();
        dealButton = new JButton("Deal");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        dealButton.setEnabled(true);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        dealButton.addActionListener(e -> betSubmit.doClick());
        hitButton.addActionListener(e -> controller.onHit());

        standButton.addActionListener(e -> {
            dealerRevealed = true;
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            controller.onStand();
        });

        actionPanel.add(dealButton);
        actionPanel.add(hitButton);
        actionPanel.add(standButton);

        tablePanel.add(dealerPanel, BorderLayout.NORTH);
        tablePanel.add(playerPanel, BorderLayout.CENTER);
        tablePanel.add(actionPanel, BorderLayout.SOUTH);

        add(leftPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLabel createCardSlot() {
        JLabel card = new JLabel(" ", SwingConstants.CENTER);
        card.setPreferredSize(new Dimension(80, 120));
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        card.setForeground(Color.WHITE);
        card.setFont(new Font("SansSerif", Font.BOLD, 24));
        return card;
    }

    private void updateHandsUI(Hand player, Hand dealer) {
        updateHandRowUI(1, player.getCards());
        updateHandRowUI(0, dealer.getCards());
    }

    private void updateHandRowUI(int row, List<Card> cards) {
        for (int i = 0; i < 5; i++) {
            JLabel slot = cardSlots[row][i];
            if (i < cards.size()) {
                if (row == 0 && !dealerRevealed) {
                    if (i == 0) slot.setText("🂠");
                    else if (i == 1) slot.setText(formatCard(cards.get(i)));
                    else slot.setText(" ");
                } else {
                    slot.setText(formatCard(cards.get(i)));
                }
            } else {
                slot.setText(" ");
            }
        }
    }

    private String formatCard(Card c) {
        String suitSymbol;
        switch (c.getSuit().toLowerCase()) {
            case "hearts": suitSymbol = "♥"; break;
            case "diamonds": suitSymbol = "♦"; break;
            case "clubs": suitSymbol = "♣"; break;
            case "spades": suitSymbol = "♠"; break;
            default: suitSymbol = "?"; break;
        }
        return c.getRank() + suitSymbol;
    }

    private void handleResult(GameResult result) {
        dealerRevealed = true;

        double payout = 0.0;
        switch (result) {
            case PLAYER_WIN:
            case DEALER_BUST:
                payout = lastBet * 2.0;
                break;
            case PUSH:
                payout = lastBet;
                break;
            default:
                payout = 0.0;
        }

        if (payout > 0) current_user.deposit(payout);
        walletField.setText(String.format("$%.2f", current_user.getBalance()));

        String msg;
        switch (result) {
            case PLAYER_WIN: msg = "You win!"; break;
            case DEALER_BUST: msg = "Dealer busted — you win!"; break;
            case PUSH: msg = "Push — your bet was returned."; break;
            case PLAYER_BUST: msg = "You busted — you lose."; break;
            case DEALER_WIN: msg = "Dealer wins."; break;
            default: msg = result.toString(); break;
        }
        JOptionPane.showMessageDialog(this, msg);

        lastBet = 0.0;
        betField.setEditable(true);
        betSubmit.setEnabled(true);
        dealButton.setEnabled(true);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
    }
}
