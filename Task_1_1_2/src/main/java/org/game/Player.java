package org.game;

import java.util.Vector;

import static org.game.BlackJack.counterOfUsedCards;

/**
 * Abstract class representing a player in the Blackjack game.
 * A player can be either a human player or a dealer, and has a set of cards, score, and status.
 */
abstract class Player {
    private int score = 0;
    private int points;
    Vector<Card> cards = new Vector<>();

    /**
     * Enum representing the possible status of a player in the game.
     * A player can either win or lose.
     */
    enum Status {
        WIN, LOSE;
    }

    /**
     * Sets the points of the player based on the value of their cards.
     *
     * @param points the total points to be set for the player.
     */
    void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the current points of the player.
     *
     * @return the player's points as an integer.
     */
    int getPoints() {
        return points;
    }

    /**
     * Sets the score of the player, which is used to track wins and losses.
     *
     * @param score the score to set for the player.
     */
    void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the player's score as an integer.
     */
    int getScore() {
        return score;
    }

    /**
     * Deals two cards to the player from the deck. If all cards have been used, reshuffles the deck.
     * Updates the player's points based on the initial two cards dealt.
     */
    void setCards() {
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        for (int i = 0; i < 2; i++) {
            cards.add(BlackJack.cards[counterOfUsedCards]);
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
        }
        setPoints(cards.get(0).getPoints() + cards.get(1).getPoints());
    }

    /**
     * Prints the cards of the player or dealer. For the dealer, the second card is hidden
     * until revealed later in the game.
     *
     * @param isHuman true if the player is a human, false if it is the dealer.
     * @param fl      indicates whether the dealer's second card should remain hidden.
     * @param pl      the player whose cards are to be printed.
     */
    void printCards(boolean isHuman, boolean fl, Player pl) {
        if (isHuman) {
            System.out.print("     Ваши карты: [");
        } else {
            System.out.print("     Карты дилера: [");
            if (fl) {
                System.out.printf("%s (%d), <зарытая карта>]\n", pl.cards.get(0).getNameCard(), pl.cards.get(0).getPoints());
                return;
            }
        }
        for (int i = 0; i < pl.cards.size(); i++) {
            if (i == pl.cards.size() - 1) {
                System.out.printf("%s (%d)] => %d\n", pl.cards.get(i).getNameCard(), pl.cards.get(i).getPoints(), pl.getPoints());
            } else {
                System.out.printf("%s (%d), ", pl.cards.get(i).getNameCard(), pl.cards.get(i).getPoints());
            }
        }
    }

    /**
     * Represents the main step logic for the player. The player continues to take actions
     * until they either win, lose, or choose to stop.
     *
     * @param human  the human player in the game.
     * @param dealer the dealer in the game.
     * @return the status of the player, either WIN or LOSE.
     */
    Status step(Player human, Player dealer) {
        while (shouldContinue()) {
            takeCardAction(human, dealer);
            if (getPoints() == 21) {
                return Status.WIN;
            }
            if (getPoints() > 21) {
                return Status.LOSE;
            }
        }
        return Status.WIN;
    }

    /**
     * Defines the specific action the player should take, implemented in subclasses.
     *
     * @param human  the human player.
     * @param dealer the dealer.
     */
    abstract void takeCardAction(Player human, Player dealer);

    /**
     * Defines the condition for the player to continue their turn, implemented in subclasses.
     *
     * @return true if the player should continue, false otherwise.
     */
    abstract boolean shouldContinue();

    /**
     * Checks if the player has an Ace card valued at 11 points and reduces its value to 1 point if
     * the total points exceed 21. Adjusts the total points accordingly.
     */
    void checkForAce() {
        if (points > 21) {
            for (Card card : cards) {
                if (card.getPoints() == 11) {
                    card.setPoints(1);
                    setPoints(getPoints() - 10);
                }
            }
        }
    }

    /**
     * Clears the player's cards, resetting their hand for a new game or round.
     */
    void clearCards() {
        cards.clear();
    }
}
