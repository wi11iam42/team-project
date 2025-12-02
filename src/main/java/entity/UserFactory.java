package entity;

public class UserFactory {

    /**
     * Creates and returns a new User with the provided details.
     *
     * @param username the user's username
     * @param balance the starting balance
     * @param totalbets the total number of bets already placed
     * @param gamesPlayed the number of games already played
     * @param password the user's password (not hashed unless done elsewhere)
     * @return a new User instance
     */
    public static User create(String username, double balance, int totalbets, int gamesPlayed, String password) {
        // Hash the password here if needed
        return new User(username, balance, totalbets, gamesPlayed, password);
    }
}
