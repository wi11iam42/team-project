package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;
import view.LoginView;

import javax.swing.*;


public class LogoutPresenter implements LogoutOutputBoundary {

    private ViewManagerModel viewManagerModel;
    private LoginViewModel loginViewModel;
    private LoginView loginView;


    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           LoginViewModel loginViewModel,
                           LoginView loginView) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.loginView = loginView;
    }

    @Override
    public void prepareSuccessView(LogoutOutputData response) {

        loginView.clearFields();

        // Show the login frame
        SwingUtilities.getWindowAncestor(loginView).setVisible(true);

        System.out.println("Switched to login view");

    }
}
