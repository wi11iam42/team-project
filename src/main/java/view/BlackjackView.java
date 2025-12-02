package view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

import entity.*;
import interface_adapter.Blackjack.BlackjackController;
import interface_adapter.Blackjack.BlackjackPresenter;
import interface_adapter.Blackjack.BlackjackViewModel;
import interface_adapter.GameSelect.GameSelectViewModel;
import use_case.Blackjack.BlackjackInteractor;
import data_access.FileUserDataAccessObject;

public class BlackjackView extends JFrame {

    private final BlackjackController controller;
    private final BlackjackViewModel viewModel;

    private JLabel walletLabel;
    private JTextField walletField;
    private JTextField betField;
    private User current_user;


    private double lastBet = 0.0;


    private boolean dealerRevealed = false;

    private JLabel[][] cardSlots = new JLabel[2][5];


    private JButton dealButton;
    private JButton hitButton;
    private JButton standButton;
    private JButton betSubmit;
    private JButton returnButton;


    private static final int SLOT_WIDTH = 220;
    private static final int SLOT_HEIGHT = 320;

    // Fonts
    private final Font LARGE_LABEL = new Font("Segoe UI", Font.BOLD, 22);
    private final Font LARGE_BUTTON = new Font("Segoe UI", Font.BOLD, 20);
    private final Font CARD_FONT = new Font("SansSerif", Font.BOLD, 28);

    public BlackjackView(User user) {

        viewModel = new BlackjackViewModel();
        BlackjackPresenter presenter = new BlackjackPresenter(viewModel);
        BlackjackGame game = new BlackjackGame(100);
        BlackjackInteractor interactor = new BlackjackInteractor(game, presenter);
        controller = new BlackjackController(interactor);

        current_user = user;

        createAndShowGUI();


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
        setLayout(new BorderLayout(12, 12));

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        walletLabel = new JLabel("Wallet:");
        walletLabel.setFont(LARGE_LABEL);

        walletField = new JTextField(String.format("$%.2f", current_user.getBalance()));
        walletField.setFont(LARGE_LABEL);
        walletField.setEditable(false);

        JLabel betLabel = new JLabel("Bet amount:");
        betLabel.setFont(LARGE_LABEL);

        betField = new JTextField("10");
        betField.setFont(LARGE_LABEL);
        betSubmit = new JButton("Submit Bet");
        betSubmit.setFont(LARGE_BUTTON);

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
            current_user.addGamePlayed();
            walletField.setText(String.format("$%.2f", current_user.getBalance()));

            // Save user data to persist gamesPlayed count
            FileUserDataAccessObject userDAO = new FileUserDataAccessObject("users.csv", new UserFactory());
            userDAO.save(current_user);

            dealerRevealed = false;

            controller.onDeal(lastBet);

            dealButton.setEnabled(false);
            betSubmit.setEnabled(false);
            betField.setEditable(false);
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        });

        JPanel betPanel = new JPanel(new BorderLayout(6, 6));
        betPanel.add(betField, BorderLayout.CENTER);
        betPanel.add(betSubmit, BorderLayout.EAST);

        leftPanel.add(walletLabel);
        leftPanel.add(Box.createVerticalStrut(6));
        leftPanel.add(walletField);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(betLabel);
        leftPanel.add(Box.createVerticalStrut(6));
        leftPanel.add(betPanel);
        leftPanel.add(Box.createVerticalStrut(12));

        returnButton = new JButton("Return to Game Select");
        returnButton.setFont(LARGE_BUTTON);
        returnButton.addActionListener(e -> {
            GameSelectViewModel viewModel = new GameSelectViewModel(current_user);
            GameSelectView gameSelectView = new GameSelectView(viewModel);
            JFrame gameSelectFrame = new JFrame("Game Select");
            gameSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameSelectFrame.add(gameSelectView);
            gameSelectFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameSelectFrame.setVisible(true);
            dispose();
        });
        leftPanel.add(returnButton);

        // CENTER TABLE
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(new Color(0, 100, 0));

        JPanel dealerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        dealerPanel.setOpaque(false);
        JLabel dealerLabel = new JLabel("Dealer's Hand:");
        dealerLabel.setFont(LARGE_LABEL);
        dealerPanel.add(dealerLabel);

        for (int i = 0; i < 5; i++) {
            JLabel card = createCardSlot();
            cardSlots[0][i] = card;
            dealerPanel.add(card);
        }

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        playerPanel.setOpaque(false);
        JLabel playerLabel = new JLabel("Your Hand:");
        playerLabel.setFont(LARGE_LABEL);
        playerPanel.add(playerLabel);

        for (int i = 0; i < 5; i++) {
            JLabel card = createCardSlot();
            cardSlots[1][i] = card;
            playerPanel.add(card);
        }

        JPanel actionPanel = new JPanel();
        dealButton = new JButton("Deal");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        dealButton.setFont(LARGE_BUTTON);
        hitButton.setFont(LARGE_BUTTON);
        standButton.setFont(LARGE_BUTTON);

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

        // Make window open maximized
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JLabel createCardSlot() {
        JLabel card = new JLabel("", SwingConstants.CENTER);
        card.setPreferredSize(new Dimension(SLOT_WIDTH, SLOT_HEIGHT));
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        card.setFont(CARD_FONT);
        card.setOpaque(false);
        return card;
    }

    private void updateHandsUI(Hand player, Hand dealer) {
        updateHandRowUI(1, player.getCards());
        updateHandRowUI(0, dealer.getCards());
    }

    private void updateHandRowUI(int row, List<Card> cards) {
        for (int i = 0; i < 5; i++) {
            JLabel slot = cardSlots[row][i];
            slot.setIcon(null);
            slot.setText(" ");

            if (i < cards.size()) {
                if (row == 0 && !dealerRevealed && i == 0) {
                    ImageIcon back = new ImageIcon(getClass().getResource("/cards/back.png"));
                    Image scaled = back.getImage().getScaledInstance(SLOT_WIDTH, SLOT_HEIGHT, Image.SCALE_SMOOTH);
                    slot.setIcon(new ImageIcon(scaled));
                    slot.setText("");
                } else {
                    setCardImage(slot, cards.get(i));
                }
            }
        }
    }



    private String cardToFilename(Card c) {
        String rawRank = c.getRank().toLowerCase().trim();
        String rank;
        switch (rawRank) {
            case "a":
            case "ace": rank = "ace"; break;
            case "k":
            case "king": rank = "king"; break;
            case "q":
            case "queen": rank = "queen"; break;
            case "j":
            case "jack": rank = "jack"; break;
            default: rank = rawRank; break;
        }

        String suit = c.getSuit().toLowerCase().trim();
        return rank + "_of_" + suit + "2";
    }

    private ImageIcon loadCardImageIcon(String name) {
        String fileName = "/cards/" + name + ".png";
        try {
            URL res = getClass().getResource(fileName);
            if (res != null) {
                ImageIcon orig = new ImageIcon(res);
                Image scaled = orig.getImage().getScaledInstance(SLOT_WIDTH, SLOT_HEIGHT, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }

            // Fallback: try the path you gave: scs/main/java/resources/cards/<name>.png
            String fallbackPath = "scs/main/java/resources/cards/" + name + ".png";
            java.io.File f = new java.io.File(fallbackPath);
            if (f.exists()) {
                ImageIcon orig = new ImageIcon(fallbackPath);
                Image scaled = orig.getImage().getScaledInstance(SLOT_WIDTH, SLOT_HEIGHT, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }


            String altPath = "resources/cards/" + name + ".png";
            f = new java.io.File(altPath);
            if (f.exists()) {
                ImageIcon orig = new ImageIcon(altPath);
                Image scaled = orig.getImage().getScaledInstance(SLOT_WIDTH, SLOT_HEIGHT, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void setCardImage(JLabel slot, Card c) {

        String rawRank = c.getRank().toString().toLowerCase();
        String suit = c.getSuit().toLowerCase();

        String rank;


        switch (rawRank) {
            case "j":
            case "jack":
            case "11":
                rank = "jack";
                break;

            case "q":
            case "queen":
            case "12":
                rank = "queen";
                break;

            case "k":
            case "king":
            case "13":
                rank = "king";
                break;

            case "a":
            case "ace":
            case "1":
                rank = "ace";
                break;

            default:
                rank = rawRank;
        }

        boolean useTwoSuffix =
                rank.equals("jack") ||
                        rank.equals("queen") ||
                        rank.equals("king");

        String filename = useTwoSuffix
                ? rank + "_of_" + suit + "2.png"
                : rank + "_of_" + suit + ".png";

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/cards/" + filename));
            Image scaled = icon.getImage().getScaledInstance(SLOT_WIDTH, SLOT_HEIGHT, Image.SCALE_SMOOTH);
            slot.setIcon(new ImageIcon(scaled));
            slot.setText("");
        } catch (Exception e) {
            System.out.println("❌ MISSING IMAGE: " + filename);
            slot.setText(rank + " of " + suit);
        }
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
            default: msg = "Round finished."; break;
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
