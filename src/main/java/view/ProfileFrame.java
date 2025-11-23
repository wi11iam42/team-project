package view;

import interface_adapter.Profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {

    public ProfileFrame(ProfileViewModel vm, JFrame mainMenu) {

        setTitle("Profile");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        Font font = new Font("Arial", Font.PLAIN, 30);

        centerPanel.add(createLabel("Username: " + vm.getUsername(), font));
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createLabel("Balance: $" + vm.getBalance(), font));
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createLabel("Bets: " + vm.getBets(), font));
        centerPanel.add(Box.createVerticalStrut(20));

        centerPanel.add(createLabel("Games Played: " + vm.getGamesPlayed(), font));

        JButton returnBtn = new JButton("Return to Home");
        returnBtn.setFont(new Font("Arial", Font.BOLD, 40));
        returnBtn.setPreferredSize(new Dimension(400, 100));
        returnBtn.addActionListener(e -> {
            mainMenu.setVisible(true);
            dispose();
        });

        add(centerPanel, BorderLayout.CENTER);
        add(returnBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(font);
        return label;
    }
}
