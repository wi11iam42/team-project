package interfaceadapter.signup;

import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;

public class SignupController {

    private final SignupInputBoundary userSignupusecaseInteractor;

    public SignupController(SignupInputBoundary userSignupusecaseInteractor) {
        this.userSignupusecaseInteractor = userSignupusecaseInteractor;
    }


    public void execute(String username, String password1, String password2) {
        final SignupInputData signupInputData = new SignupInputData(
                username, password1, password2);

        userSignupusecaseInteractor.execute(signupInputData);
    }


    public void switchToLoginView() {
        userSignupusecaseInteractor.switchToLoginView();
    }
}
