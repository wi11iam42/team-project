package interfaceadapter.Blackjack;

import entity.*;
import java.util.ArrayList;
import java.util.List;


public class BlackjackViewModel {
    private Hand lastPlayer;
    private Hand lastDealer;

    public interface Listener {
        void onHandsUpdated(Hand player, Hand dealer);
        void onWalletUpdated(double wallet);
        void onResult(GameResult result);
    }

    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener l) {
        if (l != null && !listeners.contains(l)) listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }


    public void updateHands(Hand player, Hand dealer) {
        this.lastPlayer = player;
        this.lastDealer = dealer;

        for (Listener l : listeners) {
            l.onHandsUpdated(player, dealer);
        }
    }

    public Hand getLastPlayer() {
        return lastPlayer;
    }

    public Hand getLastDealer() {
        return lastDealer;
    }

    public void updateWallet(double wallet) {
        for (Listener l : listeners) {
            l.onWalletUpdated(wallet);
        }
    }

    public void showResult(GameResult result) {
        for (Listener l : listeners) {
            l.onResult(result);
        }
    }
}
