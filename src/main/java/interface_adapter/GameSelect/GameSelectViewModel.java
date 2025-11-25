package interface_adapter.GameSelect;

import entity.User;
import interface_adapter.ViewModel;

/**
 * The View Model for the Login View.
 */
public class GameSelectViewModel extends ViewModel<GameSelectState> {

    public GameSelectViewModel(User user) {
        super("game select");
        setState(new GameSelectState(user));
    }
    public GameSelectViewModel() {
        super("game select");
        setState(new GameSelectState(new User("Joe", 0,0,0,
                "Password")));
    }

}
