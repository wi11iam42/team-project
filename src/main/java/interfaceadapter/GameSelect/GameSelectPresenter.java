package interfaceadapter.GameSelect;

import entity.User;
import usecase.GameSelect.GameSelectOutputBoundary;

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
