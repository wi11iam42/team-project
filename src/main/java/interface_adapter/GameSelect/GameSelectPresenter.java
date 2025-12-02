package interface_adapter.GameSelect;

import entity.User;
import use_case.gameselect.GameSelectOutputBoundary;

public class GameSelectPresenter implements GameSelectOutputBoundary {

    private GameSelectViewModel gsvm;

    @Override
    public void prepareView(User user) {
        this.gsvm = new GameSelectViewModel(user);
    }

    public GameSelectViewModel getViewModel(){
        return gsvm;
    }
}
