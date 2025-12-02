package usecase.Sportbet;

import java.util.ArrayList;

import entity.Sportbet;
import entity.User;

public interface SportbetInputBoundary {

    void placeBet(Sportbet bet, User user, double stake, String teamSelection);

    void simulateBet(Sportbet bet, User user);

    ArrayList<Sportbet> getUserBets(String username);
}
