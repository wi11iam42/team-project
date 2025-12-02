package use_case.Blackjack;

import entity.BlackjackGame;
import entity.GameResult;

public interface BlackjackOutputBoundary {
    void presentHands(BlackjackGame game);
    void presentResult(BlackjackGame game, GameResult result);
}
