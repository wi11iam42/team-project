package interfaceadapter.GameSelect;

import entity.User;
import interfaceadapter.ViewModel;

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
