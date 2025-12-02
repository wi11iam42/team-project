package usecase.logout;

public interface LogoutUserDataAccessInterface {
    /**
     * Retrieves the username of the currently logged-in user.
     *
     * @return the current username
     */
    String getCurrentUsername();

    /**
     * Sets the username of the currently logged-in user.
     *
     * @param username to set as the current user
     */
    void setCurrentUsername(String username);
}
