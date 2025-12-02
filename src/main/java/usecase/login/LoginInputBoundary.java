package usecase.login;

public interface LoginInputBoundary {

    /**
     * Executes the login process.
     *
     * @param loginInputData the data entered by the user for login
     */
    void execute(LoginInputData loginInputData);

    /**
    * Switches the view to the signup screen.
    */
    void switchToSignupView();

}
