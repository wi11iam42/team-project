package usecase.GameSelect;

public interface GameSelectInputBoundary {

    /**
     * Executes the game selection with the given type and stakes.
     *
     * @param game   the name or type of the game to be played
     * @param stakes the amount of stakes for the game
     */
    void execute(String game, float stakes);
}
