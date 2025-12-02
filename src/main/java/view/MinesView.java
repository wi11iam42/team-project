package view;

import javax.swing.*;
import java.awt.*;

import entity.*;
import interface_adapter.GameSelect.GameSelectViewModel;
import interface_adapter.Mines.MinesController;
import interface_adapter.Mines.MinesPresenter;
import interface_adapter.Mines.MinesViewModel;
import use_case.Mines.MinesInteractor;
import data_access.FileUserDataAccessObject;

public class MinesView extends JFrame {

    private MinesController controller;
    private MinesViewModel viewModel;
    private MinesGame game; // current game instance

    private final User user;

    private JLabel walletLabel;
    private JTextField walletField;
    private JTextField betField;
    private JLabel multiplierLabel;      // shows current multiplier
    private JSlider multSlider;

    private final int gridSize = 5;
    private final JButton[][] tiles = new JButton[gridSize][gridSize];

    private JButton submitButton;
    private JButton cashoutButton;
    private JButton returnButton;

    private double currentBet = 0.0;
    private boolean roundActive = false;
    private int safeClicks = 0;
    private int currentMineCount = 3;   // set from slider
    private final double payoutFactor = 0.97; // house-edge factor (tuneable)

    public MinesView(User user) {
        this.user = user;
        initCleanArchWithGame(3); // default 3 mines
        createAndShowGUI();
        registerViewModel();
    }

    private void initCleanArchWithGame(int mineCount) {
        this.currentMineCount = mineCount;
        this.game = new MinesGame(gridSize, mineCount);
        this.viewModel = new MinesViewModel();
        MinesPresenter presenter = new MinesPresenter(viewModel);
        MinesInteractor interactor = new MinesInteractor(game, presenter);
        this.controller = new MinesController(interactor);
    }

    private void registerViewModel() {
        viewModel.addListener((g, safe, x, y) -> {
            SwingUtilities.invokeLater(() -> handleTileUpdate(g, safe, x, y));
        });
    }

    private void createAndShowGUI() {
        setTitle("Mines Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout(10, 10));
        Font bigFont = new Font("SansSerif", Font.BOLD, 24);
        Font hugeFont = new Font("SansSerif", Font.BOLD, 32);

        // Left controls
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        walletLabel = new JLabel("Wallet:");
        walletLabel.setFont(bigFont);
        walletField = new JTextField(String.format("$%.2f", user.getBalance()));
        walletField.setFont(bigFont);
        walletField.setEditable(false);

        JLabel betLabel = new JLabel("Bet amount:");
        betLabel.setFont(bigFont);
        betField = new JTextField("20");
        betField.setFont(bigFont);


        submitButton = new JButton("Submit Bet");
        submitButton.setFont(bigFont);
        submitButton.addActionListener(e -> onSubmitBet());

        JPanel betPanel = new JPanel(new BorderLayout(5, 5));
        betPanel.add(betField, BorderLayout.CENTER);
        betPanel.add(submitButton, BorderLayout.EAST);

        JLabel multLabel = new JLabel("Mines (slider):");
        multSlider = new JSlider(1, 8, 3); // allow 1..8 mines on 5x5 board
        multLabel.setFont(bigFont);
        multSlider.setMajorTickSpacing(1);
        multSlider.setPaintTicks(true);
        multSlider.setPaintLabels(true);

        multiplierLabel = new JLabel("Multiplier: x1.00");
        multiplierLabel.setFont(hugeFont);

        cashoutButton = new JButton("Cashout");
        cashoutButton.setFont(bigFont);
        cashoutButton.setEnabled(false);
        cashoutButton.addActionListener(e -> onCashout());

        returnButton = new JButton("Return to Game Select");
        returnButton.setFont(bigFont);
        returnButton.addActionListener(e -> {
            GameSelectViewModel viewModel = new GameSelectViewModel(user);
            GameSelectView gameSelectView = new GameSelectView(viewModel);
            JFrame gameSelectFrame = new JFrame("Game Select");
            gameSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameSelectFrame.add(gameSelectView);
            gameSelectFrame.setMinimumSize(new Dimension(1700, 1050));
            gameSelectFrame.setPreferredSize(new Dimension(1700, 1050));
            gameSelectFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            gameSelectFrame.setVisible(true);
            dispose();
        });

        leftPanel.add(walletLabel);
        leftPanel.add(walletField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(betLabel);
        leftPanel.add(betPanel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(multLabel);
        leftPanel.add(multSlider);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(multiplierLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(cashoutButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(returnButton);

        // Center grid
        JPanel grid = new JPanel(new GridLayout(gridSize, gridSize, 5, 5));
        grid.setBorder(BorderFactory.createTitledBorder("Mines Grid"));

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                JButton tile = new JButton();
                tile.setFont(hugeFont);
                tiles[x][y] = tile;
                tile.setPreferredSize(new Dimension(70, 70));
                tile.setBackground(Color.LIGHT_GRAY);
                final int fx = x, fy = y;
                tile.addActionListener(e -> {
                    if (!roundActive) return;
                    // controller field will be updated when new game starts
                    controller.onReveal(fx, fy);
                });
                grid.add(tile);
            }
        }

        add(leftPanel, BorderLayout.WEST);
        add(grid, BorderLayout.CENTER);

        setVisible(true);
    }

    private void onSubmitBet() {
        if (roundActive) {
            JOptionPane.showMessageDialog(this, "Round already in progress. Finish it before betting again.");
            return;
        }
        double betAmount;
        try {
            betAmount = Double.parseDouble(betField.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric bet amount");
            return;
        }
        if (betAmount <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter a positive bet.");
            return;
        }
        if (betAmount > user.getBalance()) {
            JOptionPane.showMessageDialog(this, "Bet exceeds wallet balance");
            return;
        }

        // withdraw bet immediately
        currentBet = betAmount;
        user.withdraw(currentBet);
        user.addGamePlayed();
        walletField.setText(String.format("$%.2f", user.getBalance()));

        // Save user data to persist gamesPlayed count
        FileUserDataAccessObject userDAO = new FileUserDataAccessObject("users.csv", new UserFactory());
        userDAO.save(user);


        int mines = multSlider.getValue();
        initCleanArchWithGame(mines);
        registerViewModel(); // register listener to the new viewModel

        // reset UI tiles and state
        resetTiles();
        safeClicks = 0;
        roundActive = true;
        cashoutButton.setEnabled(false);
        multiplierLabel.setText("Multiplier: x1.00");
    }

    private void resetTiles() {
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                JButton t = tiles[x][y];
                t.setBackground(Color.LIGHT_GRAY);
                t.setEnabled(true);
                t.setText("");
            }
        }
    }

    private void handleTileUpdate(MinesGame g, boolean safe, int x, int y) {
        JButton tile = tiles[x][y];
        tile.setEnabled(false);

        if (safe) {
            safeClicks++;
            tile.setBackground(new Color(144, 238, 144)); // light green

            // compute current multiplier based on safeClicks
            double mult = computeMultiplier(gridSize * gridSize, currentMineCount, safeClicks);
            multiplierLabel.setText(String.format("Multiplier: x%.2f", mult));

            // enable cashout (only after at least one safe click)
            cashoutButton.setEnabled(true);

            // check for win condition (all non-mine tiles revealed)
            if (checkWinCondition(g)) {
                // auto-pay player
                double payout = currentBet * mult;
                user.deposit(payout);
                walletField.setText(String.format("$%.2f", user.getBalance()));
                roundActive = false;
                cashoutButton.setEnabled(false);
                JOptionPane.showMessageDialog(this, String.format("You cleared the board! Payout: $%.2f", payout));
            }
        } else {
            // mine hit -> show mine, reveal all mines, end round with no payout
            tile.setBackground(new Color(255, 102, 102));
            tile.setText("💣");
            revealAllMines(g);
            roundActive = false;
            cashoutButton.setEnabled(false);
            multiplierLabel.setText("Multiplier: x0.00");
            JOptionPane.showMessageDialog(this, "You hit a mine — you lose.");
        }
    }

    private void revealAllMines(MinesGame g) {
        boolean[][] mines = g.getMines();
        boolean[][] revealed = g.getRevealed();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mines[i][j]) {
                    JButton t = tiles[i][j];
                    t.setBackground(new Color(255, 102, 102));
                    t.setText("💣");
                    t.setEnabled(false);
                } else {
                    if (revealed[i][j]) {
                        tiles[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    private boolean checkWinCondition(MinesGame g) {
        boolean[][] mines = g.getMines();
        boolean[][] revealed = g.getRevealed();

        int totalCells = gridSize * gridSize;
        int mineCount = 0;
        int revealedCount = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (mines[i][j]) mineCount++;
                if (revealed[i][j]) revealedCount++;
            }
        }
        return revealedCount == (totalCells - mineCount);
    }

    private void onCashout() {
        if (!roundActive) {
            JOptionPane.showMessageDialog(this, "No active round.");
            return;
        }
        if (safeClicks == 0) {
            JOptionPane.showMessageDialog(this, "You must reveal at least one safe tile before cashing out.");
            return;
        }

        double mult = computeMultiplier(gridSize * gridSize, currentMineCount, safeClicks);
        double payout = currentBet * mult;
        user.deposit(payout);
        walletField.setText(String.format("$%.2f", user.getBalance()));
        roundActive = false;
        cashoutButton.setEnabled(false);
        multiplierLabel.setText(String.format("Multiplier: x%.2f", mult));
        JOptionPane.showMessageDialog(this, String.format("Cashed out: $%.2f (x%.2f)", payout, mult));
    }

    /**
     * Compute multiplier from survival probability:
     * P_survive(k) = ∏_{i=0..k-1} (T - B - i) / (T - i)
     * fairMultiplier = 1 / P_survive(k)
     * apply payoutFactor (house edge).
     */
    private double computeMultiplier(int totalTiles, int mines, int picks) {
        if (picks <= 0) return 1.0;
        double prob = 1.0;
        for (int i = 0; i < picks; i++) {
            double numerator = (double) (totalTiles - mines - i);
            double denominator = (double) (totalTiles - i);
            if (denominator <= 0) return 0.0;
            prob *= numerator / denominator;
        }
        if (prob <= 0.0) return 0.0;
        double fairMultiplier = 1.0 / prob;
        double mult = fairMultiplier * payoutFactor;
        return Math.max(1.0, mult);
    }
}
