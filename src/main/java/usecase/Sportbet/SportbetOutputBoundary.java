package usecase.Sportbet;

import java.util.ArrayList;

import entity.Sportbet;

public interface SportbetOutputBoundary {

    void presentBetPlaced(Sportbet bet);

    void presentBetSimulated(Sportbet bet);

    void presentUserBets(ArrayList<Sportbet> bets);

    void presentError(String message);
}
