package view;

import interface_adapter.GameSelect.GameSelectController;
import interface_adapter.GameSelect.GameSelectState;
import interface_adapter.GameSelect.GameSelectViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logging into the program.
 */
public class GameSelectView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Game Select";
    private final GameSelectViewModel gameSelectViewModel;
    private GameSelectController gameSelectController;

    public GameSelectView(GameSelectViewModel gameSelectViewModel) {

        JTextField stakesInputField = new JTextField(10);

        JButton BackButton = new JButton(("Back"));

        JPanel buttons = new JPanel();
        JButton MinesButton = new JButton("Mines");
        JButton WheelButton = new JButton("Wheel");
        JButton BlackJackButton = new JButton("Black Jack");
        buttons.add(MinesButton);
        buttons.add(WheelButton);
        buttons.add(BlackJackButton);

        LabelTextPanel stakesInfo = new LabelTextPanel(
                new JLabel("Stakes:"), stakesInputField);

        JPanel finalButton = new JPanel();
        JButton playButton = new JButton("Play!");
        finalButton.add(playButton);

        this.gameSelectViewModel = gameSelectViewModel;
        this.gameSelectController = new GameSelectController();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(BackButton);
        this.add(buttons);
        this.add(stakesInfo);
        this.add(finalButton);

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
                        }
                    }
                }
        );
        WheelButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(WheelButton)) {
                            gameSelectViewModel.getState().setGame("Wheel");
                        }
                    }
                }
        );
        BlackJackButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(BlackJackButton)) {
                            gameSelectViewModel.getState().setGame("BlackJack");
                        }
                    }
                }
        );
        playButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(playButton)) {
                            GameSelectState currentState = gameSelectViewModel.getState();
                            gameSelectController.execute(currentState);
                            SwingUtilities.getWindowAncestor(GameSelectView.this).dispose();
                        }
                    }
                }
        );
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
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

    public String getViewName() {
        return viewName;
    }

    public void setGameSelectController(GameSelectController gameSelectController) {
        this.gameSelectController = gameSelectController;
    }
}
