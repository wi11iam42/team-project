package use_case.Sportbet;

import entity.Sportbet;
import entity.User;

import java.util.ArrayList;

public interface SportbetInputBoundary {

    void placeBet(Sportbet bet, User user, double stake, String teamSelection);

    void simulateBet(Sportbet bet, User user);

    ArrayList<Sportbet> getUserBets(String username);
}
