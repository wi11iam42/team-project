package usecase.Blackjack;

import entity.BlackjackGame;
import entity.GameResult;

public class BlackjackInteractor {
    private final BlackjackGame game;
    private final BlackjackOutputBoundary presenter;

    public BlackjackInteractor(BlackjackGame game, BlackjackOutputBoundary presenter) {
        this.game = game;
        this.presenter = presenter;
    }

    /**
     * Starts a new round of the game with the given bet amount.
     * @param bet the amount wagered to begin the round
     */
    public void startRound(double bet) {
        game.startRound(bet);
        presenter.presentHands(game);
    }

    /**
     * Player gets another card.
     */
    public void playerHit() {
        game.playerHit();
        // Always update the hands after a hit
        presenter.presentHands(game);

        // IF PLAYER BUSTS → END ROUND IMMEDIATELY
        if (game.getPlayerHand().getValue() > 21) {
            presenter.presentResult(game, GameResult.PLAYER_BUST);
        }
    }

    /**
     * Handles the player's decision to stand in the game.
     */
    public void stand() {
        game.dealerPlay();
        final GameResult result = game.determineResult();
        game.updateWallet(result);
        presenter.presentResult(game, result);
    }
}
