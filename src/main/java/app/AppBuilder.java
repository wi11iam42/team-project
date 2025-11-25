package app;

import interface_adapter.GameSelect.GameSelectController;
import interface_adapter.GameSelect.GameSelectState;
import interface_adapter.GameSelect.GameSelectViewModel;
import interface_adapter.ViewModel;
import interface_adapter.ViewManagerModel;
import view.GameSelectView;
import view.MainMenuFrame.BackgroundPanel;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private GameSelectView gameSelectView;
    private GameSelectViewModel gameSelectViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addGameSelectView() {
        gameSelectViewModel = new GameSelectViewModel();
        gameSelectView = new GameSelectView(gameSelectViewModel);
        cardPanel.add(gameSelectView, gameSelectView.getViewName());
        return this;
    }


    public JFrame build() {
        final JFrame application = new JFrame("366");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel,0);
        application.setMinimumSize(new Dimension(1700, 1050));
        application.setPreferredSize(new Dimension(1700, 1050));
        application.setExtendedState(JFrame.MAXIMIZED_BOTH);

        viewManagerModel.setState(gameSelectView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }


}
