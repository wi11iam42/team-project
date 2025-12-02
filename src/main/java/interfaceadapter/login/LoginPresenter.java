package interfaceadapter.login;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.loggedin.LoggedInState;
import interfaceadapter.loggedin.LoggedInViewModel;
import interfaceadapter.signup.SignupViewModel;
import interfaceadapter.logout.LogoutController;
import usecase.login.LoginOutputBoundary;
import usecase.login.LoginOutputData;
import usecase.login.LoginUserDataAccessInterface;
import view.MainMenuFrame.MainMenuFrame;
import view.LoginView;
import entity.User;


public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;
    private final LoginUserDataAccessInterface userDataAccess;
    private final LoginView loginView;
    private final LogoutController logoutController;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel,
                          SignupViewModel signupViewModel,
                          LoginUserDataAccessInterface userDataAccess,
                          LoginView loginView,
                          LogoutController logoutController) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
        this.userDataAccess = userDataAccess;
        this.loginView = loginView;
        this.logoutController = logoutController;
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
