package entity;

public class Card {
    private final String rank;
    private final String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    /**
     * Returns the Blackjack value of this card based on its rank.
     * Number cards return their face value, face cards (J, Q, K) return 10,
     * and Ace returns 11 by default (adjustment for 1 or 11 should be handled elsewhere).
     *
     * @return the Blackjack point value of the card
     * @throws IllegalArgumentException if the card rank is invalid
     */
    public int getValue() {
        switch (rank) {
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 9;
            case "10":
            case "J":
            case "Q":
            case "K":
                return 10;
            case "A":
                // Ace can be 1 or 11 — default value logic is in the hand class
                return 11;
            default:
                throw new IllegalArgumentException("Unknown card rank: " + rank);
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
