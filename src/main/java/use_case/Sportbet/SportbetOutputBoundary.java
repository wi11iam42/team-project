package use_case.Sportbet;

import entity.Sportbet;
import java.util.ArrayList;

public interface SportbetOutputBoundary {

    void presentBetPlaced(Sportbet bet);

    void presentBetSimulated(Sportbet bet);

    void presentUserBets(ArrayList<Sportbet> bets);

    void presentError(String message);
}
