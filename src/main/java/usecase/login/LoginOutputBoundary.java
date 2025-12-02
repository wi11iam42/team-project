package usecase.login;

public interface LoginOutputBoundary {

    /**
     * Prepares the view to display a successful login.
     *
     * @param outputData the data resulting from a successful login
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the view to display a failed login attempt.
     *
     * @param errorMessage the error message explaining why the login failed
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the view to the signup screen.
     */
    void switchToSignupView();
}
