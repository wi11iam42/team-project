package use_case;

import entity.MinesGame;

public interface MinesOutputBoundary {
    // include coordinates of the revealed tile
    void presentBoard(MinesGame game, boolean safe, int x, int y);
}
