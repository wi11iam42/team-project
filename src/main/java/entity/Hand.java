package entity;

import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public void clear() {
        cards.clear();
    }

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Calculates the total Blackjack value of the hand.
     * Aces are initially counted as 11 and reduced to 1 as needed
     * to prevent the hand from busting.
     *
     * @return the total Blackjack point value of the hand
     */
    public int getValue() {
        int value = 0;
        int aces = 0;
        for (Card c : cards) {
            value += c.getValue();
            if (c.getRank().equals("A")) aces++;
        }
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }
}