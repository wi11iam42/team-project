package view.MainMenuFrame;

import app.AppBuilder;
import entity.User;
import interface_adapter.Profile.ProfileController;
import interface_adapter.Profile.ProfilePresenter;
import interface_adapter.Profile.ProfileViewModel;
import use_case.profile.ProfileInteractor;
import data_access.InMemoryUserDataAccess;
import data_access.UserDataAccessInterface;
import view.BetHistoryFrame;
import view.ProfileFrame;
import view.SportbetFrame;
import view.WalletFrame;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

//    static {
//        System.setProperty("sun.java2d.uiScale", "1");
//    }

    private final ProfilePresenter profilePresenter;
    private final ProfileController profileController;

    public MainMenuFrame(User user) {

        setTitle("BET366 Main Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(1700, 1050));
        setPreferredSize(new Dimension(1700, 1050));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Profile interactor
        UserDataAccessInterface userDAO = new InMemoryUserDataAccess();
        userDAO.save(user);
        profilePresenter = new ProfilePresenter();
        ProfileInteractor interactor = new ProfileInteractor(userDAO, profilePresenter);
        profileController = new ProfileController(interactor);

        // === Background ===
        String bgPath = getClass().getResource("/Image_1-6.jpeg").getPath();
        BackgroundPanel root = new BackgroundPanel(bgPath);
        root.setLayout(new BorderLayout());
        setContentPane(root);

        // === Title ===
        JLabel title = new JLabel("Welcome to BET366", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 90));
        title.setForeground(Color.BLACK);
        root.add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        root.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(25, 25, 25, 25);

        // =====================================
        // New Size Settings (your requirements)
        // =====================================
        int SMALL_W = 560, SMALL_H = 350;

        // restore original width, but 1.5× height
        int MID_W = 660;
        int MID_H_NEW = (int) (350 * 1.5);   // 525

        int BIG_W = 1250, BIG_H = 320;

        // logout → quarter size
        int LOGOUT_W = SMALL_W / 4;
        int LOGOUT_H = SMALL_H / 4;

        // =====================================
        // Buttons
        // =====================================

        JButton profileBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:50px;'>👤</span><br>" +
                        "<span style='font-size:40px;'>Profile</span>" +
                        "</center></html>",
                SMALL_W, SMALL_H
        );

        JButton depositBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:50px;'>💰</span><br>" +
                        "<span style='font-size:36px;'>Deposit</span><br>" +
                        "<span style='font-size:36px;'>Withdraw</span>" +
                        "</center></html>",
                SMALL_W, SMALL_H
        );

        // === Sport Bet (bigger height + bigger font) ===
        JButton sportBetBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:75px;'>🏀</span><br>" +
                        "<span style='font-size:55px;'>Sport Bet</span>" +
                        "</center></html>",
                MID_W, MID_H_NEW
        );

        // === Play Bet Game (same style) ===
        JButton gameSelectBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:75px;'>🂡</span><br>" +
                        "<span style='font-size:55px;'>Play Bet Game</span>" +
                        "</center></html>",
                MID_W, MID_H_NEW
        );

        JButton historyBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:50px;'>📝</span><br>" +
                        "<span style='font-size:40px;'>View Bet History</span>" +
                        "</center></html>",
                BIG_W, BIG_H
        );

        // === Logout small button ===
        JButton logoutBtn = newButton(
                "<html><center>" +
                        "<span style='font-size:25px;'>🚪</span><br>" +
                        "<span style='font-size:22px;'>Logout</span>" +
                        "</center></html>",
                LOGOUT_W, LOGOUT_H
        );

        // =====================================
        // Add Buttons to Layout
        // =====================================

        c.gridx = 0; c.gridy = 0;
        centerPanel.add(profileBtn, c);

        c.gridx = 1; c.gridy = 0;
        centerPanel.add(sportBetBtn, c);

        c.gridx = 2; c.gridy = 0;
        centerPanel.add(gameSelectBtn, c);

        c.gridx = 1; c.gridy = 1; c.gridwidth = 2;
        centerPanel.add(historyBtn, c);
        c.gridwidth = 1;

        c.gridx = 0; c.gridy = 2;
        centerPanel.add(depositBtn, c);

        // === logout button stays at bottom-right ===
        JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomRight.setOpaque(false);
        bottomRight.add(logoutBtn);
        root.add(bottomRight, BorderLayout.SOUTH);

        // =====================================
        // Button actions
        // =====================================

        profileBtn.addActionListener(e -> {
            profileController.loadProfile(user.getUsername());
            ProfileViewModel vm = profilePresenter.getViewModel();
            new ProfileFrame(vm, this);
            setVisible(false);
        });

        sportBetBtn.addActionListener(e -> {
            new SportbetFrame(user, this);
            setVisible(false);
        });

        gameSelectBtn.addActionListener(e -> {
            AppBuilder builder = new AppBuilder();
            JFrame app = builder.addGameSelectView().build();
            app.setVisible(true);
            dispose();
        });

        historyBtn.addActionListener(e -> {
            new BetHistoryFrame(user, this);
            setVisible(false);
        });

        depositBtn.addActionListener(e -> {
            new WalletFrame(user, this);
            setVisible(false);
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out!");
            dispose();
        });

        pack();
        setVisible(true);
    }

    private JButton newButton(String html, int w, int h) {
        JButton btn = new RoundedButton(html);
        Dimension d = new Dimension(w, h);
        btn.setPreferredSize(d);
        btn.setMinimumSize(d);
        btn.setMaximumSize(d);
        return btn;
    }

    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 235));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 55, 55);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(4));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(), 55, 55);
            g2.dispose();
            super.paintComponent(g);
        }
    }

}
