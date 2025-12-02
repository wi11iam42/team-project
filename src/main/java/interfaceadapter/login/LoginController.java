package interfaceadapter.login;

import usecase.login.LoginInputBoundary;
import usecase.login.LoginInputData;

public class LoginController {

    private final LoginInputBoundary loginusecaseInteractor;

    public LoginController(LoginInputBoundary loginusecaseInteractor) {
        this.loginusecaseInteractor = loginusecaseInteractor;
    }

    public void execute(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginusecaseInteractor.execute(loginInputData);
    }
    public void switchToSignupView() {loginusecaseInteractor.switchToSignupView();
    }

}
