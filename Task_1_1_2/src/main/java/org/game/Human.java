package org.game;

import java.util.Scanner;

import static org.game.BlackJack.counterOfUsedCards;

/**
 * Represents a human player in the Blackjack game.
 * The human player can decide whether to take a card or stop based on user input.
 */
class Human extends Player {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Defines the action taken by the human player when they choose to draw a card.
     * Draws a card from the deck, adds its points to the player's total, and adjusts for any aces if necessary.
     * Displays the player's and dealer's cards after the action.
     *
     * @param human  the human player in the game.
     * @param dealer the dealer in the game.
     */
    @Override
    void takeCardAction(Player human, Player dealer) {
        if (counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        cards.add(BlackJack.cards[counterOfUsedCards]);
        System.out.printf("Вы открыли карту %s (%d)\n", BlackJack.cards[counterOfUsedCards].getNameCard(), BlackJack.cards[counterOfUsedCards].getPoints());
        setPoints(BlackJack.cards[counterOfUsedCards].getPoints() + getPoints());
        counterOfUsedCards++;
        checkForAce();
        printCards(true, true, human);
        printCards(false, true, dealer);
    }

    /**
     * Checks if the human player wants to continue drawing cards.
     * Prompts the user to input '1' to take another card or '0' to stop.
     *
     * @return true if the player chooses to continue, false if they choose to stop.
     */
    @Override
    boolean shouldContinue() {
        System.out.println("Введите '1', чтобы взять карту, и '0', чтобы остановиться...");
        String input = scanner.nextLine();
        return input.equals("1");
    }
}
