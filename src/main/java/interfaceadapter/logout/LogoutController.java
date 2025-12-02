package interfaceadapter.logout;

import usecase.logout.LogoutInputBoundary;

public class LogoutController {

    private LogoutInputBoundary logoutInputBoundary;

    public LogoutController(LogoutInputBoundary logoutInputBoundary) {
        this.logoutInputBoundary = logoutInputBoundary;
    }

    public void execute(){
        logoutInputBoundary.execute();
    }
}
