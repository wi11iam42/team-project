
package usecase.GameSelect;

import entity.User;
import interfaceadapter.GameSelect.GameSelectController;
import interfaceadapter.GameSelect.GameSelectState;

public class GameSelectInteractor implements GameSelectInputBoundary {
    private final User user;
    private final GameSelectOutputBoundary presenter;

    public GameSelectInteractor(User user, GameSelectOutputBoundary presenter) {
        this.user = user;
        this.presenter = presenter;
    }

    /**
     * Executes the game selection process for the current user.
     * @param game   the name or type of the game to be played
     * @param stakes the amount of stakes for the game
     */
    public void execute(String game, float stakes) {
        final GameSelectController controller = new GameSelectController();
        final GameSelectState state = new GameSelectState(user);
        state.setGame(game);
        state.setStakes(stakes);
        controller.execute(state);
    }

    public User getUser() {
        return user;
    }

    public GameSelectOutputBoundary getPresenter() {
        return presenter;
    }

}
