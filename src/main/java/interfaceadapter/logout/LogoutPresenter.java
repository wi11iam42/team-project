package interfaceadapter.logout;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.login.LoginViewModel;
import view.LoginView;
import usecase.logout.LogoutOutputBoundary;
import usecase.logout.LogoutOutputData;

import javax.swing.*;

public class LogoutPresenter implements LogoutOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;

    public LogoutPresenter(ViewManagerModel viewManagerModel, LoginViewModel loginViewModel, LoginView loginView) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.loginView = loginView;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData outputData) {

        loginView.clearFields();

        SwingUtilities.getWindowAncestor(loginView).setVisible(true);

        System.out.println("Successfully logged out");
    }
}
