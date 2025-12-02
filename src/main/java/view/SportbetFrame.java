package view;

import data_access.FileUserDataAccessObject;
import data_access.SportsAPIDataAccess;
import entity.Sportbet;
import entity.User;
import entity.UserFactory;
import use_case.Sportbet.SportbetInteractor;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SportbetFrame extends JFrame {

    public SportbetFrame(User user, JFrame mainMenu) {

        setTitle("Place a Sports Bet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);

        JPanel bg = new JPanel() {
            private final Image img = new ImageIcon(
                    getClass().getResource("/Image-from-iOS.jpg")
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(bg);
        bg.setLayout(new BorderLayout());

        SportbetInteractor interactor = new SportbetInteractor();

        ArrayList<Sportbet> userBets = interactor.getUserBets(user.getUsername());

        Map<String, Sportbet> userBetMap = new HashMap<>();
        for (Sportbet bet : userBets) {
            userBetMap.put(bet.getId(), bet);
        }

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

            if (userBetMap.containsKey(apiBet.getId())) {
                displayBet = userBetMap.get(apiBet.getId());
            }

            displayList.add(displayBet);
        }

        JList<Sportbet> betsList = new JList<>(displayList.toArray(new Sportbet[0]));
        betsList.setOpaque(false);

        betsList.setCellRenderer(new ListCellRenderer<Sportbet>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends Sportbet> list,
                    Sportbet value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
                row.setOpaque(true);

                JLabel line1 = new JLabel(
                        value.getSport() + "  |  " + value.getTeam1() + " vs " + value.getTeam2()
                );
                JLabel line2 = new JLabel(
                        "Odds: " + value.getTeam1price() + " / " + value.getTeam2price()
                );
                JLabel line3 = new JLabel(
                        "Your Bet: " + value.getSelection() +
                                "   |  Stake: " + value.getStake() +
                                "   |  Status: " + value.getStatus()
                );

                final Font title = new Font("Segoe UI", Font.BOLD, 40);
                final Font info = new Font("Segoe UI", Font.PLAIN, 36);

                line1.setFont(title);
                line2.setFont(info);
                line3.setFont(info);

                line1.setForeground(Color.BLACK);
                line2.setForeground(Color.BLACK);
                line3.setForeground(Color.BLACK);

                row.add(line1);
                row.add(line2);
                row.add(line3);

                row.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                if (isSelected) {
                    row.setBackground(new Color(180, 200, 240, 220));
                }
                else {
                    row.setBackground(new Color(255, 255, 255, 170));
                }

                return row;
            }
        });

        final JScrollPane scrollPane = new JScrollPane(betsList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);

        bg.add(scrollPane, BorderLayout.CENTER);

        AtomicBoolean hasTeam = new AtomicBoolean(false);
        AtomicBoolean pickedTeam1 = new AtomicBoolean(false);

        final JTextField amountField = new JTextField(10);
        amountField.setFont(new Font("Segoe UI", Font.BOLD, 50));
        amountField.setPreferredSize(new Dimension(350, 80));

        final JButton pickTeam1Btn = new JButton("Bet on Team 1");
        final JButton pickTeam2Btn = new JButton("Bet on Team 2");
        final JButton placeBetButton = new JButton("Place Bet");
        final JButton backButton = new JButton("Go Back");

        final Font buttonFont = new Font("Segoe UI", Font.BOLD, 40);

        final JButton[] allButtons = {pickTeam1Btn, pickTeam2Btn, placeBetButton, backButton};
        for (JButton b : allButtons) {
            b.setFont(buttonFont);
            b.setPreferredSize(new Dimension(350, 80));
        }

        pickTeam1Btn.addActionListener(e -> {
            final Sportbet s = betsList.getSelectedValue();
            if (s == null) {
                return;
            }
            hasTeam.set(true);
            pickedTeam1.set(true);
            s.setSelection(s.getTeam1());
            betsList.repaint();
        });

        pickTeam2Btn.addActionListener(e -> {
            final Sportbet s = betsList.getSelectedValue();
            if (s == null) {
                return;
            }
            hasTeam.set(true);
            pickedTeam1.set(false);
            s.setSelection(s.getTeam2());
            betsList.repaint();
        });

        placeBetButton.addActionListener(e -> {
            final Sportbet s = betsList.getSelectedValue();
            if (s == null || !hasTeam.get()) {
                JOptionPane.showMessageDialog(this, "Select a bet and team first.");
                return;
            }
            if (!s.getStatus().equals("No bets")) {
                JOptionPane.showMessageDialog(this, "You already bet on this game!");
                return;
            }

            final int amount;
            try {
                amount = Integer.parseInt(amountField.getText());
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid integer.");
                return;
            }

            if (!user.checkwithdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
                return;
            }

            interactor.placeBet(
                    s,
                    user,
                    amount,
                    pickedTeam1.get() ? s.getTeam1() : s.getTeam2()
            );

            JOptionPane.showMessageDialog(this, "Bet placed:\n" + s);
            betsList.repaint();
        });

        backButton.addActionListener(e -> {
            FileUserDataAccessObject userDAO =
                    new FileUserDataAccessObject("users.csv", new UserFactory());
            userDAO.save(user);
            new MainMenuFrame(user);
            dispose();
        });

        final JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setOpaque(false);

        bottomPanel.add(pickTeam1Btn);
        bottomPanel.add(pickTeam2Btn);
        bottomPanel.add(amountField);
        bottomPanel.add(placeBetButton);
        bottomPanel.add(backButton);

        bg.add(bottomPanel, BorderLayout.SOUTH);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }
}
