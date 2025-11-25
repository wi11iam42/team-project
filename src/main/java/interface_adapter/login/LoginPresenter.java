package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedin.LoggedInState;
import interface_adapter.loggedin.LoggedInViewModel;
import interface_adapter.signup.SignupViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import view.MainMenuFrame.MainMenuFrame;
import entity.User;


public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final LoggedInViewModel loggedInViewModel;
    private final ViewManagerModel viewManagerModel;
    private final SignupViewModel signupViewModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel,
                          LoginViewModel loginViewModel, SignupViewModel signupViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loginViewModel = loginViewModel;
        this.signupViewModel = signupViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(response.getUsername());
        this.loggedInViewModel.firePropertyChange();

        loginViewModel.setState(new LoginState());

        User loggedInUser = new User(response.getUsername(), 0, 0, 0, ""); // create User entity
        new MainMenuFrame(loggedInUser);

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
