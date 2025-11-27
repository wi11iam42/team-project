package interface_adapter.Mines;

import entity.MinesGame;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for Mines — not printing to console.
 * Notifies UI listeners of single-tile updates and stores last game.
 */
public class MinesViewModel {
    public interface Listener {
        void onTileUpdated(MinesGame game, boolean safe, int x, int y);
    }

    private final List<Listener> listeners = new ArrayList<>();
    private MinesGame lastGame;

    public void addListener(Listener l) {
        if (l != null && !listeners.contains(l)) listeners.add(l);
    }

    public void removeListener(Listener l) {
        listeners.remove(l);
    }

    public void updateBoard(MinesGame game, boolean safe, int x, int y) {
        this.lastGame = game;
        for (Listener l : listeners) {
            l.onTileUpdated(game, safe, x, y);
        }
    }

    public MinesGame getLastGame() {
        return lastGame;
    }
}
