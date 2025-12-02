package usecase.GameSelect;

import entity.User;

public interface GameSelectOutputBoundary {

    /**
     * Prepares the view for the game selection screen for the specified user.
     * @param user the user for whom the game selection view should be prepared
     */
    void prepareView(User user);
}
