package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import view.LoginView;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;

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
