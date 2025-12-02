package app;

import entity.User;
import interfaceadapter.GameSelect.GameSelectPresenter;
import interfaceadapter.GameSelect.GameSelectViewModel;
import interfaceadapter.ViewManagerModel;
import usecase.GameSelect.GameSelectInteractor;
import view.GameSelectView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager vm = new ViewManager(cardPanel, cardLayout, viewManagerModel);
    private GameSelectPresenter presenter;
    private GameSelectView gameSelectView;
    private GameSelectViewModel gameSelectViewModel;
    private GameSelectInteractor gameSelectInteractor;


    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addGameSelectView(User user) {
        presenter = new GameSelectPresenter();
        presenter.prepareView(user);
        gameSelectViewModel = presenter.getViewModel();
        gameSelectView = new GameSelectView(gameSelectViewModel);
        gameSelectInteractor = new GameSelectInteractor(user, presenter);
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
