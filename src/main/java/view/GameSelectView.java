package view;

import interface_adapter.GameSelect.GameSelectController;
import interface_adapter.GameSelect.GameSelectState;
import interface_adapter.GameSelect.GameSelectViewModel;
import view.MainMenuFrame.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class GameSelectView extends BackgroundPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Game Select";
    private final GameSelectViewModel gameSelectViewModel;
    private GameSelectController gameSelectController;

    public GameSelectView(GameSelectViewModel gameSelectViewModel) {

        super();

//        JTextField stakesInputField = new JTextField(10);
        JButton BackButton = new JButton("←");
        BackButton.setFont(new Font("SansSerif",Font.PLAIN,64));
        BackButton.setBackground(new Color(255,0,0));
        BackButton.setFocusPainted(false);
        BackButton.setPreferredSize(new Dimension(100,100));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        topPanel.setOpaque(false);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        topPanel.add(BackButton);

        JButton MinesButton = new JButton("Play Mines!");
        JButton BlackJackButton = new JButton("Play Blackjack!");
        MinesButton.setFont(new Font("SansSerif", Font.PLAIN,48));
        BlackJackButton.setFont(new Font("SansSerif", Font.PLAIN,48));
        MinesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        BlackJackButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left,BoxLayout.Y_AXIS));
        ImageIcon image1 = new ImageIcon(GameSelectView.class.getResource("/BlackJack6.jpg"));
        Image scaled = image1.getImage().getScaledInstance(-1, 400, Image.SCALE_SMOOTH);
        image1 = new ImageIcon(scaled);
        JLabel image1label = new JLabel(image1);
        image1label.setAlignmentX(Component.CENTER_ALIGNMENT);
        left.add(image1label);
        JTextArea text1 = new JTextArea("Blackjack - The casino classic where you try to get 21" +
                " without going over.");
        text1.setLineWrap(true);
        text1.setMaximumSize(new Dimension(700,350));
        text1.setWrapStyleWord(true);
        text1.setEditable(false);
        text1.setOpaque(false);
        text1.setForeground(new Color(255,0,255));
        text1.setFocusable(false);
        text1.setFont(new Font("SansSerif",Font.BOLD,64));
        left.add(text1);
        left.add(BlackJackButton);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS));
        ImageIcon image2 = new ImageIcon(GameSelectView.class.getResource("/MinesPicture.jpg"));
        Image scaled2 = image2.getImage().getScaledInstance(-1, 400, Image.SCALE_SMOOTH);
        image2 = new ImageIcon(scaled2);
        JLabel image2label = new JLabel(image2);
        image2label.setAlignmentX(Component.CENTER_ALIGNMENT);
        right.add(image2label);
        JTextArea text2 = new JTextArea("Mines - how many can you click before you hit a mine?");
        text2.setLineWrap(true);
        text2.setWrapStyleWord(true);
        text2.setEditable(false);
        text2.setMaximumSize(new Dimension(700,350));
        text2.setOpaque(false);
        text2.setForeground(new Color(0,255,255));
        text2.setFocusable(false);
        text2.setFont(new Font("SansSerif",Font.BOLD,64));
        right.add(text2);
        right.add(MinesButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1,2));
        middlePanel.setOpaque(false);
        middlePanel.add(left);
        middlePanel.add(right);

        this.gameSelectViewModel = gameSelectViewModel;
        this.gameSelectController = new GameSelectController();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        this.add(middlePanel);
//        this.add(finalButton);

        BackButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(BackButton)) {
                            gameSelectViewModel.getState().setGame("MainMenu");
                            gameSelectController.execute(gameSelectViewModel.getState());
                            SwingUtilities.getWindowAncestor(GameSelectView.this).dispose();
                        }
                    }
                }
        );


        MinesButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(MinesButton)) {
                            gameSelectViewModel.getState().setGame("Mines");
                            go();
                        }
                    }
                }
        );

//        WheelButton.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(WheelButton)) {
//                            gameSelectViewModel.getState().setGame("Wheel");
//                        }
//                    }
//                }
//        );

        BlackJackButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(BlackJackButton)) {
                            gameSelectViewModel.getState().setGame("BlackJack");
                            go();
                        }
                    }
                }
        );

//        playButton.addActionListener(
//                new ActionListener() {
//                    public void actionPerformed(ActionEvent evt) {
//                        if (evt.getSource().equals(playButton)) {
//                            GameSelectState currentState = gameSelectViewModel.getState();
//                            gameSelectController.execute(currentState);
//                            SwingUtilities.getWindowAncestor(GameSelectView.this).dispose();
//                        }
//                    }
//                }
//        );
    }


    /**
     * React to a button click that results in evt.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final GameSelectState state = (GameSelectState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(GameSelectState state) {
    }

    public void go(){
        GameSelectState currentState = gameSelectViewModel.getState();
        gameSelectController.execute(currentState);
        SwingUtilities.getWindowAncestor(GameSelectView.this).dispose();
    }

    public String getViewName() {
        return viewName;
    }

    public void setGameSelectController(GameSelectController gameSelectController) {
        this.gameSelectController = gameSelectController;
    }
}
