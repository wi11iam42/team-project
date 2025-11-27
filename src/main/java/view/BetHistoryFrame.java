package view;

import entity.Sportbet;
import entity.User;
import use_case.SportbetInteractor;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class BetHistoryFrame extends JFrame {
    public BetHistoryFrame(User user, JFrame MainMenu){

        SportbetInteractor interactor = new SportbetInteractor();

        setTitle("Bet History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Go Back");
        JButton simulateButton = new JButton("Simulate Game");

        // Top list (active bets)
        DefaultListModel<Sportbet> model = new DefaultListModel<>();
        for (Sportbet b : user.getSbs()) {
            if (!b.getStatus().equals("completed")) { // only active bets
                model.addElement(b);
            }
        }
        JList<Sportbet> betList = new JList<>(model);
        betList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        betList.setVisibleRowCount(-1);

        // Bottom list (all bets)
        DefaultListModel<Sportbet> allModel = new DefaultListModel<>();
        for (Sportbet b : user.getSbs()) {
            allModel.addElement(b);
        }
        JList<Sportbet> allBetsList = new JList<>(allModel);
        allBetsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        allBetsList.setVisibleRowCount(-1);
        allBetsList.setEnabled(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(betList), new JScrollPane(allBetsList));
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            new MainMenuFrame(user);
            dispose();
        });

        simulateButton.addActionListener(e -> {
            Sportbet selected = betList.getSelectedValue();
            if (selected == null){
                JOptionPane.showMessageDialog(this, "Select a bet to simulate first!");
                return;
            }

            // Call interactor to simulate bet
            interactor.simulateBet(selected, user);

            // Show result
            if (selected.getBetwon()) {
                JOptionPane.showMessageDialog(this,
                        selected.getSelection() + " won! Your winnings have been deposited.");
            } else {
                JOptionPane.showMessageDialog(this,
                        selected.getSelection() + " lost. Better luck next time!");
            }

            // Update UI
            model.removeElement(selected);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(simulateButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
