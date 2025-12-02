package usecase.profile;

import java.math.BigDecimal;

public class ProfileOutputData {
    private final String username;
    private final double balance;
    private final int bets;
    private final int gamesPlayed;

    public ProfileOutputData(String username, double balance, int bets, int gamesPlayed) {
        this.username = username;
        this.balance = balance;
        this.bets = bets;
        this.gamesPlayed = gamesPlayed;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public int getBets() {
        return bets;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }
}
