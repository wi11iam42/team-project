package entity;

public class BlackjackGame {
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private double wallet;
    private double bet;
    private final int maxPoints = 21;

    public BlackjackGame(double startingWallet) {
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.wallet = startingWallet;
    }

    /**
     * Starts a new round by shuffling the deck and dealing two cards
     * to both the player and the dealer.
     *
     * @param bet the amount wagered for this round
     */
    public void startRound(double bet) {
        this.bet = bet;
        playerHand.clear();
        dealerHand.clear();
        deck.shuffle();
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
        dealerHand.addCard(deck.draw());
    }

    /**
     * Deals one additional card to the player.
     */
    public void playerHit() {
        playerHand.addCard(deck.draw());
    }

    /**
     * Executes the dealer’s turn.
     * The dealer draws cards until the hand value is at least 17.
     */
    public void dealerPlay() {
        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.draw());
        }
    }

    /**
     * Compares player and dealer hands and determines the game's result.
     *
     * @return the result of the round
     */
    public GameResult determineResult() {
        if (playerHand.getValue() > maxPoints) {
            return GameResult.PLAYER_BUST;
        }
        if (dealerHand.getValue() > maxPoints) {
            return GameResult.DEALER_BUST;
        }
        if (playerHand.getValue() > dealerHand.getValue()) return GameResult.PLAYER_WIN;
        if (playerHand.getValue() < dealerHand.getValue()) return GameResult.DEALER_WIN;
        return GameResult.PUSH;
    }

    /**
     * Updates the wallet based on the game result.
     *
     * @param result the outcome of the round
     */
    public void updateWallet(GameResult result) {
        switch (result) {
            case PLAYER_WIN:
                wallet += bet;
                break;
            case DEALER_WIN:
            case PLAYER_BUST:
                wallet -= bet;
                break;
            default:
                break;
        }
    }

    /**
     * Returns the player's hand.
     *
     * @return the player's hand
     */
    public Hand getPlayerHand() {
        return playerHand;
    }

    /**
     * Returns the dealer's hand.
     *
     * @return the dealer's hand
     */
    public Hand getDealerHand() {
        return dealerHand;
    }

    /**
     * Returns the player's wallet balance.
     *
     * @return current wallet amount
     */
    public double getWallet() {
        return wallet;
    }
}
