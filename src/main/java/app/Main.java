package app;

import dataaccess.FileUserDataAccessObject;
import dataaccess.SportsAPIDataAccess;
import entity.UserFactory;
import interfaceadapter.ViewManagerModel;

import interfaceadapter.login.LoginController;
import interfaceadapter.login.LoginPresenter;
import interfaceadapter.login.LoginViewModel;

import interfaceadapter.signup.SignupController;
import interfaceadapter.signup.SignupPresenter;
import interfaceadapter.signup.SignupViewModel;

import interfaceadapter.logout.LogoutController;
import interfaceadapter.logout.LogoutPresenter;

import interfaceadapter.loggedin.LoggedInViewModel;

import usecase.login.LoginInteractor;
import usecase.logout.LogoutInteractor;
import usecase.signup.SignupInteractor;

import view.LoginView;
import view.SignupView;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1");

        SportsAPIDataAccess data = new SportsAPIDataAccess();
        // data.fetchOdds();
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
