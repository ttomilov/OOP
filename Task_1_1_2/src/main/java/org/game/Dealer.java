package org.game;

import static org.game.BlackJack.counterOfUsedCards;

/**
 * Represents the dealer in the Blackjack game.
 * The dealer automatically draws cards until their points reach or exceed a specified limit (usually 17).
 */
class Dealer extends Player {
    /**
     * Executes the action for the dealer to draw a card from the deck.
     * Draws a card, adds its value to the dealer's total points, and adjusts for any aces if necessary.
     * After drawing a card, it displays both the player's and dealer's cards.
     *
     * @param human  the human player in the game.
     * @param dealer the dealer in the game.
     */
    @Override
    void takeCardAction(Player human, Player dealer) {
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        cards.add(BlackJack.cards[counterOfUsedCards]);
        System.out.printf("Дилер открывает карту %s (%d)\n", BlackJack.cards[counterOfUsedCards].getNameCard(), BlackJack.cards[counterOfUsedCards].getPoints());
        setPoints(BlackJack.cards[counterOfUsedCards].getPoints() + getPoints());
        counterOfUsedCards++;
        checkForAce();
        printCards(true, true, human);
        printCards(false, false, dealer);
    }

    /**
     * Determines whether the dealer should continue drawing cards.
     * The dealer draws cards until reaching at least 17 points.
     *
     * @return {@code true} if the dealer's points are below 17; {@code false} otherwise.
     */
    @Override
    boolean shouldContinue() {
        return getPoints() < 17;
    }
}
