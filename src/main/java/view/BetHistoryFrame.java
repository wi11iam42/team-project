package view;

import entity.Sportbet;
import entity.User;
import view.MainMenuFrame.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class BetHistoryFrame extends JFrame {
    public BetHistoryFrame(User user, JFrame MainMenu){
        setTitle("Place a Sports Bet");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JButton backButton = new JButton("Go Back");

        backButton.addActionListener(e -> {
            new MainMenuFrame(user);
        });

        StringBuilder sb = new StringBuilder();
        for (Sportbet b : user.getSbs()) {
            sb.append(b.toString()).append("\n");
        }

        area.setText(sb.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
