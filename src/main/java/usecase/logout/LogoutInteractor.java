package usecase.logout;

public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutUserDataAccessInterface userDataAccessInterface;
    private LogoutOutputBoundary logoutOutputBoundary;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessInterface = userDataAccessInterface;
        this.logoutOutputBoundary = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        final String name = userDataAccessInterface.getCurrentUsername();
        userDataAccessInterface.setCurrentUsername(null);
        final LogoutOutputData lod = new LogoutOutputData(name);
        logoutOutputBoundary.prepareSuccessView(lod);

    }
}
