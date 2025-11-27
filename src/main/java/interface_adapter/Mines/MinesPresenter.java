package interface_adapter.Mines;

import entity.MinesGame;
import use_case.MinesOutputBoundary;

public class MinesPresenter implements MinesOutputBoundary {
    private final MinesViewModel viewModel;

    public MinesPresenter(MinesViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentBoard(MinesGame game, boolean safe, int x, int y) {
        viewModel.updateBoard(game, safe, x, y);
    }
}
