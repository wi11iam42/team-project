package usecase.profile;

import entity.User;

public interface ProfileUserDataAccessInterface {

    /**
     * Retrieves a user by their username.
     *
     * @param username of the user to retrieve
     * @return the User object corresponding to the given username
     */
    User get(String username);
}

