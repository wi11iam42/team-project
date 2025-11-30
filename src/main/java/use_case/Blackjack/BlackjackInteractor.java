
package use_case.Blackjack;

import entity.*;

public class BlackjackInteractor {
    private final BlackjackGame game;
    private final BlackjackOutputBoundary presenter;

    public BlackjackInteractor(BlackjackGame game, BlackjackOutputBoundary presenter) {
        this.game = game;
        this.presenter = presenter;
    }

    public void startRound(double bet) {
        game.startRound(bet);
        presenter.presentHands(game);
    }

    public void playerHit() {
        game.playerHit();
        // Always update the hands after a hit
        presenter.presentHands(game);

        //IF PLAYER BUSTS → END ROUND IMMEDIATELY
        if (game.getPlayerHand().getValue() > 21) {
            presenter.presentResult(game, GameResult.PLAYER_BUST);
            return;
        }
    }


    public void stand() {
        game.dealerPlay();
        GameResult result = game.determineResult();
        game.updateWallet(result);
        presenter.presentResult(game, result);
    }
}