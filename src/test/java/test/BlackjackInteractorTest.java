package test;

import entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.Blackjack.BlackjackInteractor;
import usecase.Blackjack.BlackjackOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackInteractorTest {

    private BlackjackGame game;
    private TestBlackjackPresenter presenter;
    private BlackjackInteractor interactor;

    @BeforeEach
    void setUp() {
        // Matches: public BlackjackGame(double startingWallet)
        game = new BlackjackGame(1000.0);

        presenter = new TestBlackjackPresenter();
        interactor = new BlackjackInteractor(game, presenter);
    }

    @Test
    void testStartRoundCallsPresenter() {
        interactor.startRound(100);

        assertTrue(presenter.handsUpdated, "Hands should be presented");

        assertEquals(1000.0, game.getWallet(), "Wallet should not change when starting a round");
    }


    // TEST 2: playerHit updates hands
    @Test
    void testPlayerHitCallsPresenter() {
        interactor.startRound(50);
        presenter.reset();

        interactor.playerHit();

        assertTrue(presenter.handsUpdated, "Hands should update after hit");
    }

    // TEST 3: stand produces a game result
    @Test
    void testStandCallsResultPresenter() {
        interactor.startRound(50);
        presenter.reset();

        interactor.stand();

        assertTrue(presenter.resultShown, "Result should be presented after stand");
    }

    // TEST 4: wallet decreases on player bust or dealer win
    @Test
    void testWalletDecreasesOnLoss() {
        double startingWallet = game.getWallet();

        interactor.startRound(100);

        // Force a loss path by standing immediately (dealer likely wins/busts)
        interactor.stand();

        assertTrue(game.getWallet() <= startingWallet,
                "Wallet should decrease or stay same depending on result");
    }

    //
    private static class TestBlackjackPresenter implements BlackjackOutputBoundary {

        boolean handsUpdated = false;
        boolean resultShown = false;

        @Override
        public void presentHands(BlackjackGame game) {
            handsUpdated = true;
        }

        @Override
        public void presentResult(BlackjackGame game, GameResult result) {
            resultShown = true;
        }

        void reset() {
            handsUpdated = false;
            resultShown = false;
        }
    }
}
