package interfaceadapter.Sportbet;

import entity.Sportbet;
import entity.User;
import usecase.Sportbet.SportbetInputBoundary;

public class SportbetController {

    private final SportbetInputBoundary interactor;

    public SportbetController(SportbetInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void placeBet(Sportbet bet, User user, double stake, String teamSelection) {
        interactor.placeBet(bet, user, stake, teamSelection);
    }

    public void simulateBet(Sportbet bet, User user) {
        interactor.simulateBet(bet, user);
    }

    public void getUserBets(String username) {
        interactor.getUserBets(username);
    }
}
