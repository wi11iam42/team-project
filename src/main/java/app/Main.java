package app;

import data_access.FileUserDataAccessObject;
import data_access.SportsAPIDataAccess;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;

import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;

import interface_adapter.loggedin.LoggedInViewModel;

import use_case.login.LoginInteractor;
import use_case.logout.LogoutInteractor;
import use_case.signup.SignupInteractor;

import view.LoginView;
import view.SignupView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1");

        SportsAPIDataAccess data = new SportsAPIDataAccess();
        data.readdata();

        UserFactory userFactory = new UserFactory();
        final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);


        ViewManagerModel viewManager = new ViewManagerModel();
        LoginViewModel loginVM = new LoginViewModel();
        SignupViewModel signupVM = new SignupViewModel();
        LoggedInViewModel loggedInVM = new LoggedInViewModel();
        LoginView loginView = new LoginView(loginVM);

        LogoutPresenter logoutPresenter = new LogoutPresenter(viewManager, loginVM, loginView);

        LogoutInteractor logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutPresenter);

        LogoutController logoutController = new LogoutController(logoutInteractor);

        LoginPresenter loginPresenter =
                new LoginPresenter(viewManager, loggedInVM, loginVM, signupVM, userDataAccessObject, loginView, logoutController);

        SignupPresenter signupPresenter =
                new SignupPresenter(viewManager, signupVM, loginVM);

        LoginInteractor loginInteractor =
                new LoginInteractor(userDataAccessObject, loginPresenter);

        SignupInteractor signupInteractor =
                new SignupInteractor(userDataAccessObject, signupPresenter, userFactory);

        LoginController loginController =
                new LoginController(loginInteractor);

        SignupController signupController =
                new SignupController(signupInteractor);

        loginView.setLoginController(loginController);

        SignupView signupView = new SignupView(signupVM);
        signupView.setSignupController(signupController);

        JFrame frame = new JFrame("BET366 - Gambling Software");
        ImageIcon icon = new ImageIcon(Main.class.getResource("/SmallLogo.png"));
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());

        JPanel cards = new JPanel(new CardLayout());
        cards.add(loginView, loginView.getViewName());
        cards.add(signupView, signupView.getViewName());

        frame.setContentPane(cards);
        frame.setVisible(true);

        viewManager.addPropertyChangeListener(evt -> {
            String viewName = (String) evt.getNewValue();
            CardLayout layout = (CardLayout) cards.getLayout();
            layout.show(cards, viewName);
            System.out.println("Switched to view: " + viewName);
        });

        viewManager.setState(loginView.getViewName());
        viewManager.firePropertyChange();
    }
}
