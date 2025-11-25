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

        // ========= Background =========
        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(20, 20, 20));
        setContentPane(background);

        // ========= Title =========
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 0, 0, 160));
        titlePanel.setPreferredSize(new Dimension(0, 150));

        JLabel title = new JLabel("👑 PROFILE 👑", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 110));
        title.setForeground(new Color(255, 215, 0));

        titlePanel.add(title);
        background.add(titlePanel, BorderLayout.NORTH);

        // ========= Center Info Card =========
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.setOpaque(false);
        cardContainer.setBorder(BorderFactory.createEmptyBorder(80, 200, 80, 200));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 0), 5, true),
                BorderFactory.createEmptyBorder(50, 150, 50, 150)
        ));

        // Add info rows
        card.add(makeRichLabel("👤", "Username: " + vm.getUsername()));
        card.add(Box.createVerticalStrut(35));

        card.add(makeRichLabel("💰", "Balance: $" + vm.getBalance()));
        card.add(Box.createVerticalStrut(35));

        card.add(makeRichLabel("🎯", "Bets: " + vm.getBets()));
        card.add(Box.createVerticalStrut(35));

        card.add(makeRichLabel("🎮", "Games Played: " + vm.getGamesPlayed()));

        cardContainer.add(card);
        background.add(cardContainer, BorderLayout.CENTER);

        // ========= Return Button =========
        JButton returnBtn = new JButton("Return to Home") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(30, 30, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 45, 45);

                g2.setColor(new Color(255, 215, 0));
                g2.setStroke(new BasicStroke(5));
                g2.drawRoundRect(0, 0, getWidth(), getHeight(), 45, 45);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        returnBtn.setFont(new Font("Bahnschrift", Font.BOLD, 55));
        returnBtn.setForeground(new Color(255, 215, 0));
        returnBtn.setFocusPainted(false);
        returnBtn.setContentAreaFilled(false);
        returnBtn.setOpaque(false);
        returnBtn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        returnBtn.addActionListener(e -> {
            mainMenu.setVisible(true);
            dispose();
        });

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createEmptyBorder(30, 0, 60, 0));
        bottom.add(returnBtn);

        background.add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ========= Helper: Mixed Font Label (Emoji + Gold Text) =========
    private JPanel makeRichLabel(String emoji, String text) {
        JPanel line = new JPanel();
        line.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        line.setOpaque(false);

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 65));
        emojiLabel.setForeground(new Color(255, 215, 0));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 60));
        textLabel.setForeground(new Color(255, 215, 0));

        line.add(emojiLabel);
        line.add(textLabel);

        return line;
    }
}
