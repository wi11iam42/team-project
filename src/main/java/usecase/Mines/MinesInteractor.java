package usecase.Mines;

import entity.MinesGame;

public class MinesInteractor {
    private final MinesGame game;
    private final MinesOutputBoundary presenter;

    public MinesInteractor(MinesGame game, MinesOutputBoundary presenter) {
        this.game = game;
        this.presenter = presenter;
    }

    /**
     * Reveals whether a mine was present or not once a tile is chosen..
     *
     * @param x the x coordinate of the tile
     * @param y the y coordinate of the tile
     */
    public void revealTile(int x, int y) {
        final boolean safe = game.reveal(x, y);
        // pass coords so the presenter/UI can update only that tile
        presenter.presentBoard(game, safe, x, y);
    }
}
