package entity;

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    /**
     * Constructs a standard 52-card deck and shuffles it.
     */
    public Deck() {
        final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        final String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    /**
     * Randomly shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes and returns the top card from the deck.
     *
     * @return the card drawn from the top of the deck
     * @throws IndexOutOfBoundsException if the deck is empty
     */
    public Card draw() {
        return cards.remove(0);
    }
}
