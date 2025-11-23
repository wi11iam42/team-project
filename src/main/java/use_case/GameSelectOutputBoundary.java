package use_case;

import entity.BlackjackGame;
import entity.GameResult;

public interface GameSelectOutputBoundary {
    void presentHands(BlackjackGame game);
    void presentResult(BlackjackGame game, GameResult result);
}
