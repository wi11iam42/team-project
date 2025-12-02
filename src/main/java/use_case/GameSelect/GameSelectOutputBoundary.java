package use_case.GameSelect;

import entity.BlackjackGame;
import entity.GameResult;

public interface GameSelectOutputBoundary {
    void presentHands(BlackjackGame game);
    void presentResult(BlackjackGame game, GameResult result);
}
