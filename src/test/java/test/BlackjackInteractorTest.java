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
        game = new BlackjackGame(1000.0);
        presenter = new TestBlackjackPresenter();
        interactor = new BlackjackInteractor(game, presenter);
    }

    // TEST 1: startRound calls presenter and wallet not changed
    @Test
    void testStartRoundCallsPresenter() {
        interactor.startRound(100);
        assertTrue(presenter.handsUpdated, "Hands should be presented");
        assertEquals(1000.0, game.getWallet(), "Wallet should not change when starting a round");
    }

    // TEST 2: playerHit updates hands, does not bust
    @Test
    void testPlayerHitCallsPresenter() {
        interactor.startRound(50);
        presenter.reset();
        interactor.playerHit();
        assertTrue(presenter.handsUpdated, "Hands should update after hit");
        assertFalse(presenter.resultShown, "Result should not show if player did not bust");
    }

    // TEST 3: playerHit triggers bust
    @Test
    void testPlayerBust() {
        interactor.startRound(100);

        // Force bust: manually add cards to exceed 21
        game.getPlayerHand().clear();
        game.getPlayerHand().addCard(new Card("K", "hearts"));
        game.getPlayerHand().addCard(new Card("Q", "spades"));
        game.getPlayerHand().addCard(new Card("2", "diamonds"));

        presenter.reset();
        interactor.playerHit();
        assertTrue(presenter.resultShown, "Result should show after player bust");
    }

    // TEST 4: stand calls result presenter
    @Test
    void testStandCallsResultPresenter() {
        interactor.startRound(50);
        presenter.reset();
        interactor.stand();
        assertTrue(presenter.resultShown, "Result should be presented after stand");
    }

    // TEST 5: wallet decreases on loss (dealer wins or player bust)
    @Test
    void testWalletDecreasesOnLoss() {
        double startingWallet = game.getWallet();

        interactor.startRound(100);

        // Force player bust
        game.getPlayerHand().clear();
        game.getPlayerHand().addCard(new Card("K", "hearts"));
        game.getPlayerHand().addCard(new Card("Q", "spades"));
        game.getPlayerHand().addCard(new Card("2", "diamonds"));

        interactor.playerHit();
        assertTrue(game.getWallet() <= startingWallet, "Wallet should decrease on bust");

        // Force dealer win
        interactor.startRound(50);
        game.getDealerHand().clear();
        game.getDealerHand().addCard(new Card("K", "hearts"));
        game.getDealerHand().addCard(new Card("Q", "spades"));
        game.getPlayerHand().clear();
        game.getPlayerHand().addCard(new Card("9", "hearts"));
        game.getPlayerHand().addCard(new Card("8", "clubs"));

        interactor.stand();
        assertTrue(game.getWallet() <= startingWallet, "Wallet should decrease on dealer win");
    }

    // TEST 6: wallet increases on player win or dealer bust
    @Test
    void testWalletIncreasesOnWin() {
        double startingWallet = game.getWallet();

        // Force player win
        interactor.startRound(100);
        game.getPlayerHand().clear();
        game.getDealerHand().clear();

        game.getPlayerHand().addCard(new Card("K", "hearts"));
        game.getPlayerHand().addCard(new Card("Q", "spades"));

        game.getDealerHand().addCard(new Card("9", "hearts"));
        game.getDealerHand().addCard(new Card("8", "clubs"));

        interactor.stand();
        assertTrue(game.getWallet() > startingWallet, "Wallet should increase on player win");
    }

    // ======== Test presenter ========
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
