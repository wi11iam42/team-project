package use_case.logout;


public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutUserDataAccessInterface userDataAccessObject;
    private LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        String name = userDataAccessObject.getCurrentUsername();
        userDataAccessObject.setCurrentUsername(null);
        LogoutOutputData lod = new LogoutOutputData(name);
        logoutPresenter.prepareSuccessView(lod);

    }
}