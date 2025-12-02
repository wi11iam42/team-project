package usecase.Mines;

import entity.MinesGame;

public interface MinesOutputBoundary {
    // include coordinates of the revealed tile

    /**
     * Presents the current state of the Mines game board to the user interface.
     *
     * @param game the MinesGame instance containing the current board state
     * @param safe true if the revealed tile is safe, false if it contains a mine
     * @param x the x-coordinate of the revealed tile
     * @param y the y-coordinate of the revealed tile
     */
    void presentBoard(MinesGame game, boolean safe, int x, int y);
}
