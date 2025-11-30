
package use_case.GameSelect;

import entity.User;

public class GameSelectInteractor {
    private final User user;
    private final GameSelectOutputBoundary presenter;

    public GameSelectInteractor(User user, GameSelectOutputBoundary presenter) {
        this.user = user;
        this.presenter = presenter;
    }

    public void playGame(String game, float stakes) {
    }

}