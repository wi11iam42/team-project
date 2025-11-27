package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInState;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import use_case.login.LoginUserDataAccessInterface;
import view.LoginView;
import view.MainMenuFrame.MainMenuFrame;
import entity.User;


public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final LoginUserDataAccessInterface userDataAccess;
    private final LogoutController logoutController;
    private final LoginView loginView;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel,
                          SignupViewModel signupViewModel,
                          LoginUserDataAccessInterface userDataAccess,
                          LogoutController logoutController,
                          LoginView loginView) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
        this.userDataAccess = userDataAccess;
        this.logoutController = logoutController;
        this.loginView = loginView;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.firePropertyChange();

        loginViewModel.setState(new LoginState());

        loginView.close();

        String username = response.getUsername();
        User loggedInUser = userDataAccess.get(username);
        MainMenuFrame mainMenu = new MainMenuFrame(loggedInUser);
        mainMenu.setLogoutController(logoutController);

        mainMenu.setVisible(true);
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void switchToSignupView() {
        viewManagerModel.setState(signupViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
