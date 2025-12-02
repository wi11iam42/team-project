package usecase.login;

import entity.User;

public interface LoginUserDataAccessInterface {

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to check
     * @return true if a user with the username exists, false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the given user.
     *
     * @param user being saved.
     */
    void save(User user);

    /**
     * Retrieves a user by their username.
     *
     * @param username of the user to retrieve
     * @return the User object corresponding to the username
     */
    User get(String username);

    /**
     * Sets the username of the current user in context.
     *
     * @param name the username to set
     */
    void setCurrentUsername(String name);

    /**
     * Gets the username of the current user in context.
     *
     * @return the current username
     */
    String getCurrentUsername();
}
