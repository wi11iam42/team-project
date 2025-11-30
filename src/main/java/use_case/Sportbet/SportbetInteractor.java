package use_case.Sportbet;

import data_access.FileUserDataAccessObject;
import data_access.SportbetFileDataAccessObject;
import entity.Sportbet;
import entity.User;
import entity.UserFactory;

import java.util.ArrayList;

public class SportbetInteractor {
    public final SportbetFileDataAccessObject betDAO =
            new SportbetFileDataAccessObject("bets.csv");
    private final FileUserDataAccessObject userDAO = new FileUserDataAccessObject("users.csv",new UserFactory());

    // Places the bet: set selection, stake, compute payout
    public void placeBet(Sportbet bet, User user, double stake, String teamSelection) {

        bet.setSelection(teamSelection);
        bet.setStake(stake);

        double payout = teamSelection.equals(bet.getTeam1())
                ? stake * bet.getTeam1price()
                : stake * bet.getTeam2price();
        System.out.println(payout);

        bet.setPayout(payout);

        bet.setStatus("Bet Placed");

        user.addBet(bet, stake);
        userDAO.save(user);
        betDAO.saveBet(user.getUsername(), bet);
    }

    // Simulates the game & settles the bet
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
        betDAO.replaceByUsernameAndId(user.getUsername(),bet.getId(),bet);

        bet.setStatus("completed");


    }
    public ArrayList<Sportbet> getUserBets(String username) {
        return betDAO.loadBetsForUser(username);
    }
}
