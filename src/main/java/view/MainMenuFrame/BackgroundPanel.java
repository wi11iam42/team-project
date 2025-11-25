package view.MainMenuFrame;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image bg;

    public BackgroundPanel(String path) {
        bg = new ImageIcon(path).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
}
