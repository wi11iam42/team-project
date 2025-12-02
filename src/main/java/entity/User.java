package entity;

import java.util.ArrayList;

public class User {
    private final String username;
    private double balance;
    private int totalBets;
    private int gamesPlayed;
    private final String passwordHash;
    private ArrayList<Sportbet> sbs = new ArrayList<>();

    public User(String username, double balance, int totalBets, int gamesPlayed, String passwordHash) {
        this.username = username;
        this.balance = balance;
        this.totalBets = totalBets;
        this.gamesPlayed = gamesPlayed;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public int getTotalBets() {
        return totalBets;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public ArrayList<Sportbet> getSbs() {
        return sbs;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Adds money to the user's balance.
     * The balance is rounded to two decimal places after the deposit.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        balance += amount;
        balance *= 100;
        balance = Math.round(balance);
        balance /= 100;
    }

    /**
     * Checks if the user has enough balance to withdraw the given amount.
     *
     * @param amount the amount to check
     * @return true if the user can withdraw the amount, false otherwise
     */
    public boolean checkwithdraw(double amount) {
        return balance >= amount;
    }

    /**
     * Withdraws money from the user's balance if sufficient funds exist.
     * The balance is rounded to two decimal places after withdrawal.
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        if (checkwithdraw(amount)) {
            balance -= amount;
            balance *= 100;
            balance = Math.round(balance);
            balance /= 100;
        }
    }

    /**
     * Adds a sport bet to the user's account and deducts the stake.
     * Increments the total number of bets placed.
     *
     * @param sb the sport bet being added
     * @param betamount the amount wagered
     */
    public void addBet(Sportbet sb, double betamount) {
        withdraw(betamount);
        sbs.add(sb);
        sb.setStake(betamount);
        totalBets++;
    }

    /**
     * Prints all bets associated with this user to the console.
     */
    public void viewBets() {
        for (Sportbet s : sbs) {
            System.out.println(s);
        }
    }

    /**
     * Increments the count of games played by the user by one.
     */
    public void addGamePlayed() {
        gamesPlayed++;
    }

    /**
     * Increments the total number of bets placed by the user by one.
     */
    public void incrementBetCount() {
        totalBets++;
    }
}
