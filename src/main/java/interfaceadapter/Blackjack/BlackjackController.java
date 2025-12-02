package interfaceadapter.Blackjack;

import usecase.Blackjack.BlackjackInteractor;

public class BlackjackController {
    private final BlackjackInteractor interactor;

    public BlackjackController(BlackjackInteractor interactor) {
        this.interactor = interactor;
    }

    public void onDeal(double bet) { interactor.startRound(bet); }
    public void onHit() { interactor.playerHit(); }
    public void onStand() { interactor.stand(); }
}