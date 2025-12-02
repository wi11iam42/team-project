
package use_case.gameselect;

import entity.User;
import interface_adapter.GameSelect.GameSelectController;
import interface_adapter.GameSelect.GameSelectState;

public class GameSelectInteractor implements GameSelectInputBoundary {
    private final User user;
    private final GameSelectOutputBoundary presenter;

    public GameSelectInteractor(User user, GameSelectOutputBoundary presenter) {
        this.user = user;
        this.presenter = presenter;
    }

    public void execute(String game, float stakes){
        GameSelectController controller = new GameSelectController();
        GameSelectState state = new GameSelectState(user);
        state.setGame(game);
        state.setStakes(stakes);
        controller.execute(state);
    }

    public User getUser(){
        return user;
    }

    public GameSelectOutputBoundary getPresenter(){
        return presenter;
    }

}