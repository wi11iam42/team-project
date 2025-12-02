package test;

import entity.MinesGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.Mines.MinesInteractor;
import use_case.Mines.MinesOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

class MinesInteractorTest {

    private MinesGame game;
    private TestMinesPresenter presenter;
    private MinesInteractor interactor;

    @BeforeEach
    void setUp() {
        game = new MinesGame(5, 3);
        presenter = new TestMinesPresenter();
        interactor = new MinesInteractor(game, presenter);
    }

    @Test
    void testRevealSafeTileCallsPresenter() {
        interactor.revealTile(0, 0);

        assertTrue(presenter.wasCalled, "Presenter should be called");
        assertEquals(0, presenter.x);
        assertEquals(0, presenter.y);
        assertNotNull(presenter.game);
    }

    @Test
    void testTileIsMarkedRevealed() {
        interactor.revealTile(1, 1);
        assertTrue(game.getRevealed()[1][1], "Tile should be revealed");
    }

    private static class TestMinesPresenter implements MinesOutputBoundary {
        boolean wasCalled = false;
        int x, y;
        boolean safe;
        MinesGame game;

        @Override
        public void presentBoard(MinesGame game, boolean safe, int x, int y) {
            this.wasCalled = true;
            this.game = game;
            this.safe = safe;
            this.x = x;
            this.y = y;
        }
    }
}
