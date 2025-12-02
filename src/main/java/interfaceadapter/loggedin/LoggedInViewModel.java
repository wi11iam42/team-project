package interfaceadapter.loggedin;

import interfaceadapter.ViewModel;

public class LoggedInViewModel extends ViewModel<LoggedInState> {

    public LoggedInViewModel() {
        super("logged in");
        setState(new LoggedInState());
    }

}
