package view;

import data_access.SportsAPIDataAccess;
import entity.Sportbet;
import entity.User;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class SportbetFrame extends JFrame {
    public SportbetFrame(User user, JFrame mainMenu){
        setTitle("Place a Sports Bet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        // ----- LIST OF BETS -----
        JList<Sportbet> betsList = new JList<>(
                SportsAPIDataAccess.allbets.toArray(new Sportbet[0])
        );
        betsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(betsList);
        add(scrollPane, BorderLayout.CENTER);

        // ----- INPUT FOR BET AMOUNT -----
        AtomicBoolean teamselected = new AtomicBoolean(false);
        JTextField amountField = new JTextField(10);
        JButton placeBetButton = new JButton("Place Bet");
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(e -> {
            new MainMenuFrame(user);
            dispose();
        });
        JButton pickteam1 = new JButton("Bet on team 1");
        JButton pickteam2 = new JButton("Bet on team 2");
        pickteam1.addActionListener(e -> {
            Sportbet selected = betsList.getSelectedValue();
            teamselected.set(true);
            if (selected == null || amountField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Select a bet and bet amount first.");
                return;
            }
            selected.setSelection(selected.getTeam1());


        });
        placeBetButton.addActionListener(e -> {
            Sportbet selected = betsList.getSelectedValue();

            if (selected == null || teamselected.get() == false) {
                JOptionPane.showMessageDialog(this, "Select a bet and team first.");
                return;
            }

            String text = amountField.getText();

            int amount;
            try {
                amount = Integer.parseInt(text);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid integer amount.");
                return;
            }
            if(user.checkwithdraw(amount)){
                user.addBet(selected,amount);
                user.viewBets();
                JOptionPane.showMessageDialog(this,
                        "Bet placed: " + selected.toString() + " for $" + amount);
                selected.setPayout(selected.getTeam1(),amount);
            }
            else{
                JOptionPane.showMessageDialog(this,"Please enter an amount lower than your current balance.");
            }
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
