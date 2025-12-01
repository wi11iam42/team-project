package interface_adapter.Blackjack;

import entity.*;
import use_case.Blackjack.BlackjackOutputBoundary;

public class BlackjackPresenter implements BlackjackOutputBoundary {
    private final BlackjackViewModel viewModel;

    public BlackjackPresenter(BlackjackViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHands(BlackjackGame game) {
        viewModel.updateHands(game.getPlayerHand(), game.getDealerHand());
        viewModel.updateWallet(game.getWallet());
    }

    @Override
    public void presentResult(BlackjackGame game, GameResult result) {
        viewModel.showResult(result);
    }
}
