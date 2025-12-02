package use_case.Sportbet;

import data_access.FileUserDataAccessObject;
import data_access.SportbetFileDataAccessObject;
import entity.Sportbet;
import entity.User;
import entity.UserFactory;

import java.util.ArrayList;

public class SportbetInteractor implements SportbetInputBoundary {

    public final SportbetFileDataAccessObject betDAO =
            new SportbetFileDataAccessObject("bets.csv");
    private final FileUserDataAccessObject userDAO =
            new FileUserDataAccessObject("users.csv", new UserFactory());

    private final SportbetOutputBoundary outputBoundary;

    // Optional: pass an output boundary (can be null if you don't want to use it yet)
    public SportbetInteractor(SportbetOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
    }

    public SportbetInteractor() {
        this.outputBoundary = null; // for now
    }

    @Override
    public void placeBet(Sportbet bet, User user, double stake, String teamSelection) {
        bet.setSelection(teamSelection);
        bet.setStake(stake);

        double payout = teamSelection.equals(bet.getTeam1())
                ? stake * bet.getTeam1price()
                : stake * bet.getTeam2price();

        bet.setPayout(payout);
        bet.setStatus("Bet Placed");

        user.addBet(bet, stake);
        userDAO.save(user);
        betDAO.saveBet(user.getUsername(), bet);

        if (outputBoundary != null) {
            outputBoundary.presentBetPlaced(bet);
        }
    }

    @Override
    public void simulateBet(Sportbet bet, User user) {
        double team1chance = 1 / bet.getTeam1price();
        double sim = Math.random();

        String winner = (sim < team1chance) ? bet.getTeam1() : bet.getTeam2();
        boolean won = bet.getSelection().equals(winner);

        bet.setBetwon(won);

        if (won) {
            user.deposit(bet.getPayout());
        }
        userDAO.save(user);
        betDAO.replaceByUsernameAndId(user.getUsername(), bet.getId(), bet);

        bet.setStatus("completed");

        if (outputBoundary != null) {
            outputBoundary.presentBetSimulated(bet);
        }
    }

    @Override
    public ArrayList<Sportbet> getUserBets(String username) {
        ArrayList<Sportbet> bets = betDAO.loadBetsForUser(username);

        if (outputBoundary != null) {
            outputBoundary.presentUserBets(bets);
        }

        return bets;
    }
}
