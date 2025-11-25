package view;

import data_access.SportsAPIDataAccess;
import entity.Sportbet;
import entity.User;
import use_case.SportbetInteractor;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SportbetFrame extends JFrame {
    public SportbetFrame(User user, JFrame mainMenu){

        SportbetInteractor interactor = new SportbetInteractor();

        setTitle("Place a Sports Bet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        JList<Sportbet> betsList = new JList<>(
                SportsAPIDataAccess.allbets.toArray(new Sportbet[0])
        );
        JScrollPane scrollPane = new JScrollPane(betsList);
        add(scrollPane, BorderLayout.CENTER);

        AtomicBoolean hasTeam = new AtomicBoolean(false);
        AtomicBoolean pickedTeam1 = new AtomicBoolean(false);

        JTextField amountField = new JTextField(10);

        JButton pickteam1 = new JButton("Bet on team 1");
        JButton pickteam2 = new JButton("Bet on team 2");
        JButton placeBetButton = new JButton("Place Bet");
        JButton backButton = new JButton("Go Back");

        pickteam1.addActionListener(e -> {
            Sportbet s = betsList.getSelectedValue();
            if (s == null) return;
            hasTeam.set(true);
            pickedTeam1.set(true);
            s.setSelection(s.getTeam1());
        });

        pickteam2.addActionListener(e -> {
            Sportbet s = betsList.getSelectedValue();
            if (s == null) return;
            hasTeam.set(true);
            pickedTeam1.set(false);
            s.setSelection(s.getTeam2());
        });

        placeBetButton.addActionListener(e -> {
            Sportbet s = betsList.getSelectedValue();
            if (s == null || !hasTeam.get()) {
                JOptionPane.showMessageDialog(this, "Select a bet and team first.");
                return;
            }

            int amount;
            try { amount = Integer.parseInt(amountField.getText()); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid integer.");
                return;
            }

            if (!user.checkwithdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
                return;
            }

            // CLEAN ARCHITECTURE: interactor handles everything
            interactor.placeBet(
                    s,
                    user,
                    amount,
                    pickedTeam1.get() ? s.getTeam1() : s.getTeam2()
            );

            JOptionPane.showMessageDialog(this,
                    "Bet placed:\n" + s.toString());
        });

        backButton.addActionListener(e -> {
            new MainMenuFrame(user);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(pickteam1);
        bottomPanel.add(pickteam2);
        bottomPanel.add(new JLabel("Amount:"));
        bottomPanel.add(amountField);
        bottomPanel.add(placeBetButton);
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
