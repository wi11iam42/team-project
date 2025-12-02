package test.Sportbet;

import entity.Sportbet;
import entity.User;
import org.junit.jupiter.api.Test;
import use_case.Sportbet.SportbetInteractor;
import use_case.Sportbet.SportbetOutputBoundary;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SportbetInteractorTest {

    private static class FakePresenter implements SportbetOutputBoundary {
        Sportbet lastBet;
        boolean called = false;

        @Override
        public void presentBetPlaced(Sportbet bet) {
            called = true;
            lastBet = bet;
        }
        @Override
        public void presentBetSimulated(Sportbet bet) {}
        @Override
        public void presentUserBets(ArrayList<Sportbet> bets) {}
        public void presentError(String message) {
            // just a stub; do nothing
        }
    }

    @Test
    void placeBet_setsStakeAndCallsPresenter() {
        User user = new User("testUser", 1000, 0,0,"pw");
        Sportbet bet = new Sportbet("1", "Soccer", "TeamA", "TeamB", 2.0, 3.0, "N/A");
        bet.setUser(user);

        FakePresenter presenter = new FakePresenter();
        SportbetInteractor interactor = new SportbetInteractor(presenter);

        interactor.placeBet(bet, user, 50, "TeamA");

        assertTrue(presenter.called);
        assertEquals(50, bet.getStake());
        assertEquals(100, bet.getPayout());
        assertEquals("Bet Placed", bet.getStatus());
        assertEquals("TeamA", bet.getSelection());
    }

    @Test
    void simulateBet_setsStatusAndBetwon() {
        User user = new User("testUser", 1000,0,0,"pw");
        Sportbet bet = new Sportbet("1", "Soccer", "TeamA", "TeamB", 1.2, 1.5, "N/A");
        bet.setSelection("TeamA");
        bet.setUser(user);

        FakePresenter presenter = new FakePresenter();
        SportbetInteractor interactor = new SportbetInteractor(presenter);

        interactor.simulateBet(bet, user);

        assertEquals("completed", bet.getStatus());
        assertNotNull(bet.getBetwon());
    }
}
