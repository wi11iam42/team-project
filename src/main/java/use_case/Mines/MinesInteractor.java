package use_case.Mines;

import entity.MinesGame;

public class MinesInteractor {
    private final MinesGame game;
    private final MinesOutputBoundary presenter;

    public MinesInteractor(MinesGame game, MinesOutputBoundary presenter) {
        this.game = game;
        this.presenter = presenter;
    }

    public void revealTile(int x, int y) {
        boolean safe = game.reveal(x, y);
        // pass coords so the presenter/UI can update only that tile
        presenter.presentBoard(game, safe, x, y);
    }
}
