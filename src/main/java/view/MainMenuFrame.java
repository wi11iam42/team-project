package view;

import app.AppBuilder;
import entity.User;
import interface_adapter.GameSelect.GameSelectViewModel;
import interface_adapter.Profile.ProfileController;
import interface_adapter.Profile.ProfilePresenter;
import interface_adapter.Profile.ProfileViewModel;
import use_case.profile.ProfileInteractor;
import data_access.InMemoryUserDataAccess;
import data_access.UserDataAccessInterface;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private final ProfilePresenter profilePresenter;
    private final ProfileController profileController;

    public MainMenuFrame(User user) {
        setTitle("BET366 Main Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ====== Clean Architecture Wiring for Profile ======
        UserDataAccessInterface userDAO = new InMemoryUserDataAccess();
        userDAO.save(user);  // 必须保存，否则 interactor 找不到 User

        profilePresenter = new ProfilePresenter();
        ProfileInteractor interactor = new ProfileInteractor(userDAO, profilePresenter);
        profileController = new ProfileController(interactor);

        // ====== UI Layout ======

        JLabel title = new JLabel("Welcome to BET366", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(Color.BLACK);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
        panel.setOpaque(false);

        JButton profileBtn = createMenuButton("Profile");
        JButton betHistoryBtn = createMenuButton("View Bet History");
        JButton sportBetBtn = createMenuButton("Sport Bet");
        JButton gameSelectBtn = createMenuButton("Play Bet Games");
        JButton depositBtn = createMenuButton("Deposit / Withdraw");
        JButton logoutBtn = createMenuButton("Logout");

        JPanel betRow = new JPanel(new GridLayout(1, 2, 15, 0));
        betRow.setOpaque(false);
        betRow.add(sportBetBtn);
        betRow.add(gameSelectBtn);

        panel.add(profileBtn);
        panel.add(betHistoryBtn);
        panel.add(betRow);
        panel.add(depositBtn);
        panel.add(logoutBtn);

        add(title, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // ====== PROFILE BUTTON (Clean Architecture) ======
        profileBtn.addActionListener(e -> {
            profileController.loadProfile(user.getUsername());
            ProfileViewModel vm = profilePresenter.getViewModel();
            new ProfileFrame(vm, this);
            setVisible(false);
        });

        // ====== Keep Original Functions ======

        betHistoryBtn.addActionListener(e -> {
            new BetHistoryFrame(user, this);
            this.dispose();
        });

        depositBtn.addActionListener(e -> {
            new WalletUI(user, this);
            setVisible(false);
        });

        sportBetBtn.addActionListener(e -> {
            new SportbetFrame(user, this);
            this.dispose();
        });

        gameSelectBtn.addActionListener(e -> {
            AppBuilder appBuilder = new AppBuilder();
            JFrame application = appBuilder
                    .addGameSelectView()
                    .build();

            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logged Out!");
            dispose();
        });

        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 28));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        btn.setFocusPainted(false);
        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        return btn;
    }
}
