package entity;

import java.util.ArrayList;

import static java.lang.Math.round;

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

    public String getUsername() { return username; }
    public double getBalance() { return balance; }
    public int getBets() { return totalBets; }
    public int getGamesPlayed() { return gamesPlayed; }
    public String getPasswordHash() { return passwordHash; }
    public ArrayList<Sportbet> getSbs() { return sbs; }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        balance *= 100;
        balance = Math.round(balance);
        balance /= 100;
    }

    public boolean checkwithdraw(double amount) {
        return balance >= amount;
    }

    public void withdraw(double amount) {
        if (checkwithdraw(amount)) {
        balance -= amount;
        balance *= 100;
        balance = Math.round(balance);
        balance /= 100;
    }}

    public void addBet(Sportbet sb, double betamount) {
        withdraw(betamount);
        sbs.add(sb);
        sb.setStake(betamount);
        totalBets++;
    }

    public void viewBets() {
        for (Sportbet s : sbs) {
            System.out.println(s);
        }
    }

    public void addGamePlayed() {
        gamesPlayed++;
    }

    public void incrementBetCount() {
        totalBets++;
    }
}
