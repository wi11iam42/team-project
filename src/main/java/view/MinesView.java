package view;


import javax.swing.*;
import java.awt.*;

import entity.*;
import interface_adapter.GameSelect.GameSelectViewModel;
import interface_adapter.Mines.MinesController;
import interface_adapter.Mines.MinesPresenter;
import interface_adapter.Mines.MinesViewModel;
import use_case.*;
import view.GameSelectView;


public class MinesView extends JFrame {


    private final MinesController controller;
    private final MinesViewModel viewModel;
    private final User user;


    public MinesView(User user) {
        this.user = user;
        viewModel = new MinesViewModel();
        MinesPresenter presenter = new MinesPresenter(viewModel);
        MinesGame game = new MinesGame(5, 5);
        MinesInteractor interactor = new MinesInteractor(game, presenter);
        controller = new MinesController(interactor);


        createAndShowGUI();
    }


    private void createAndShowGUI() {
        setTitle("Mines Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLayout(new BorderLayout(10, 10));


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel walletLabel = new JLabel("Wallet:");
        JTextField walletField = new JTextField("$100.00");
        walletField.setEditable(false);


        JLabel betLabel = new JLabel("Bet amount:");
        JTextField betField = new JTextField("20");
        JButton submitButton = new JButton("Submit Bet");


        JPanel betPanel = new JPanel(new BorderLayout(5, 5));
        betPanel.add(betField, BorderLayout.CENTER);
        betPanel.add(submitButton, BorderLayout.EAST);


        JLabel multiplierLabel = new JLabel("Multiplier:");
        JSlider mult = new JSlider(1, 5, 2);
        mult.setMajorTickSpacing(1);
        mult.setPaintTicks(true);
        mult.setPaintLabels(true);


        JLabel winningsLabel = new JLabel("Winnings:");
        JTextField winningsField = new JTextField("$0.00");
        winningsField.setEditable(false);


        JButton cashoutButton = new JButton("Cashout");
        cashoutButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Cashout not implemented"));

        JButton returnButton = new JButton("Return to Game Select");
        returnButton.addActionListener(e -> {
            GameSelectViewModel viewModel = new GameSelectViewModel(user);
            GameSelectView gameSelectView = new GameSelectView(viewModel);
            JFrame gameSelectFrame = new JFrame("366");
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
        leftPanel.add(multiplierLabel);
        leftPanel.add(mult);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(winningsLabel);
        leftPanel.add(winningsField);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(cashoutButton);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(returnButton);


        JPanel grid = new JPanel(new GridLayout(5, 5, 5, 5));
        grid.setBorder(BorderFactory.createTitledBorder("Mines Grid"));


        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                JButton tile = new JButton();
                final int fx = x, fy = y;
                tile.setPreferredSize(new Dimension(70, 70));
                tile.addActionListener(e -> controller.onReveal(fx, fy));
                grid.add(tile);
            }
        }


        add(leftPanel, BorderLayout.WEST);
        add(grid, BorderLayout.CENTER);


        setVisible(true);
    }

}