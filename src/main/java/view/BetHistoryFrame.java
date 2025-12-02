package view;

import entity.Sportbet;
import entity.User;
import use_case.Sportbet.SportbetInteractor;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class BetHistoryFrame extends JFrame {

    public BetHistoryFrame(User user, JFrame MainMenu){

        SportbetInteractor interactor = new SportbetInteractor();
        data_access.FileUserDataAccessObject userDAO =
                new data_access.FileUserDataAccessObject("users.csv", new entity.UserFactory());

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

        setTitle("Bet History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        Font big = new Font("Segoe UI", Font.BOLD, 40);
        Font info = new Font("Segoe UI", Font.PLAIN, 36);

        JButton backButton = new JButton("Go Back");
        JButton simulateButton = new JButton("Simulate Game");
        backButton.setFont(big);
        simulateButton.setFont(big);
        backButton.setPreferredSize(new Dimension(350, 80));
        simulateButton.setPreferredSize(new Dimension(350, 80));

        // active bets list
        DefaultListModel<Sportbet> activeModel = new DefaultListModel<>();
        for (Sportbet b : interactor.getUserBets(user.getUsername())) {
            if (!b.getStatus().equalsIgnoreCase("completed")) {
                activeModel.addElement(b);
            }
        }

        JList<Sportbet> activeList = new JList<>(activeModel);
        activeList.setOpaque(false);

        activeList.setCellRenderer(new ListCellRenderer<Sportbet>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends Sportbet> list, Sportbet value,
                    int index, boolean isSelected, boolean cellHasFocus) {

                JPanel row = new JPanel();
                row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
                row.setOpaque(true);

                JLabel l1 = new JLabel(value.getSport() + " | "
                        + value.getTeam1() + " vs " + value.getTeam2());
                JLabel l2 = new JLabel("Odds: " +
                        value.getTeam1price() + " / " + value.getTeam2price());
                JLabel l3;
                l3 = new JLabel("Your Bet: " + value.getSelection()
                        + " | Stake: " + value.getStake()
                        + " | " + value.getStatus());

                if (value.getBetwon() && value.getStatus().equalsIgnoreCase("completed")){
                    l3 = new JLabel("Your Bet: " + value.getSelection()
                            + " | Stake: " + value.getStake()
                            + " | " + value.getStatus()
                            + " | Result: +" + value.getPayout());
                }
                else if (!value.getBetwon() && value.getStatus().equalsIgnoreCase("completed")) {
                    l3 = new JLabel("Your Bet: " + value.getSelection()
                            + " | Stake: " + value.getStake()
                            + " | " + value.getStatus()
                            + " | Result: -" + value.getStake());
                }


                l1.setFont(big);
                l2.setFont(info);
                l3.setFont(info);

                l1.setForeground(Color.BLACK);
                l2.setForeground(Color.BLACK);
                l3.setForeground(Color.BLACK);

                row.add(l1);
                row.add(l2);
                row.add(l3);
                row.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

                if (isSelected)
                    row.setBackground(new Color(180,200,240,230));
                else
                    row.setBackground(new Color(255,255,255,160));

                return row;
            }
        });

        // all bets list
        DefaultListModel<Sportbet> allModel = new DefaultListModel<>();
        for (Sportbet b : interactor.getUserBets(user.getUsername()))
            allModel.addElement(b);

        JList<Sportbet> allList = new JList<>(allModel);
        allList.setOpaque(false);
        allList.setEnabled(false);

        allList.setCellRenderer(activeList.getCellRenderer());

        JScrollPane sp1 = new JScrollPane(activeList);
        JScrollPane sp2 = new JScrollPane(allList);

        makeScrollPaneTransparent(sp1);
        makeScrollPaneTransparent(sp2);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp1, sp2);
        splitPane.setDividerLocation(500);
        splitPane.setOpaque(false);
        splitPane.setBackground(new Color(0,0,0,0));

        bg.add(splitPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            userDAO.save(user);
            new MainMenuFrame(user);
            dispose();
        });

        simulateButton.addActionListener(e -> {
            Sportbet selected = activeList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a bet first!");
                return;
            }

            interactor.simulateBet(selected, user);
            userDAO.save(user);
            interactor.betDAO.replaceByUsernameAndId(
                    user.getUsername(), selected.getId(), selected);

            if (selected.getBetwon())
                JOptionPane.showMessageDialog(this, selected.getSelection() + " won!");
            else
                JOptionPane.showMessageDialog(this, selected.getSelection() + " lost!");

            activeModel.removeElement(selected);

            allModel.clear();
            for (Sportbet b : interactor.getUserBets(user.getUsername()))
                allModel.addElement(b);
        });

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setOpaque(false);
        bottom.add(simulateButton);
        bottom.add(backButton);

        bg.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void makeScrollPaneTransparent(JScrollPane sp) {
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(null);
    }
}
