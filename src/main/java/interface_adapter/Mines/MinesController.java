package interface_adapter.Mines;

import use_case.Mines.MinesInteractor;

public class MinesController {
    private final MinesInteractor interactor;

    public MinesController(MinesInteractor interactor) {
        this.interactor = interactor;
    }

    public void onReveal(int x, int y) {
        interactor.revealTile(x, y);
    }
}