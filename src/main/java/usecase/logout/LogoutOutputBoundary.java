package usecase.logout;

public interface LogoutOutputBoundary {

    /**
     * Prepares the view to show a successful logout.
     *
     * @param outputData the data to be presented after logout
     */
    void prepareSuccessView(LogoutOutputData outputData);
}
