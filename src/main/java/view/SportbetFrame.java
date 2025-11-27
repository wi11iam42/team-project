package view;

import data_access.SportbetFileDataAccessObject;
import data_access.SportsAPIDataAccess;
import entity.Sportbet;
import entity.User;
import use_case.SportbetInteractor;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SportbetFrame extends JFrame {
    public SportbetFrame(User user, JFrame mainMenu) {

        SportbetInteractor interactor = new SportbetInteractor();

        setTitle("Place a Sports Bet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        // ✅ Load user bets ONCE through interactor
        ArrayList<Sportbet> userBets = interactor.getUserBets(user.getUsername());

        // ✅ Map bets by ID for fast lookup
        Map<String, Sportbet> userBetMap = new HashMap<>();
        for (Sportbet bet : userBets) {
            userBetMap.put(bet.getId(), bet);
        }

        // ✅ Build DISPLAY list (NEVER MODIFY API LIST)
        ArrayList<Sportbet> displayList = new ArrayList<>();

        for (Sportbet apiBet : SportsAPIDataAccess.allbets) {

            Sportbet displayBet = new Sportbet(
                    apiBet.getId(),
                    apiBet.getSport(),
                    apiBet.getTeam1(),
                    apiBet.getTeam2(),
                    apiBet.getTeam1price(),
                    apiBet.getTeam2price(),
                    "No bets"
            );

            Sportbet userBet = userBetMap.get(apiBet.getId());

            if (userBet != null) {
                displayBet = userBet;
            }

            displayList.add(displayBet);
        }

        // ✅ Create list using displayList
        JList<Sportbet> betsList = new JList<>(
                displayList.toArray(new Sportbet[0])
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
            betsList.repaint();
        });

        pickteam2.addActionListener(e -> {
            Sportbet s = betsList.getSelectedValue();
            if (s == null) return;
            hasTeam.set(true);
            pickedTeam1.set(false);
            s.setSelection(s.getTeam2());
            betsList.repaint();
        });

        placeBetButton.addActionListener(e -> {
            Sportbet s = betsList.getSelectedValue();
            if (s == null || !hasTeam.get()) {
                JOptionPane.showMessageDialog(this,"Select a bet and team first.");
                return;
            }

            if (!s.getStatus().equals("No bets")) {
                JOptionPane.showMessageDialog(this,"You already bet on this game!");
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"Enter a valid integer.");
                return;
            }

            if (!user.checkwithdraw(amount)) {
                JOptionPane.showMessageDialog(this,"Insufficient balance.");
                return;
            }

            interactor.placeBet(
                    s,
                    user,
                    amount,
                    pickedTeam1.get() ? s.getTeam1() : s.getTeam2()
            );

            JOptionPane.showMessageDialog(this,"Bet placed:\n" + s);

            betsList.repaint();
        });

        backButton.addActionListener(e -> {
            // Save user data before returning to ensure database is synchronized
            data_access.FileUserDataAccessObject userDAO =
                    new data_access.FileUserDataAccessObject("users.csv", new entity.UserFactory());
            userDAO.save(user);
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
