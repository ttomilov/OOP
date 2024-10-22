package org.game;

import static org.game.BlackJack.counterOfUsedCards;

import java.util.Scanner;
import java.util.Vector;

/**
 * Represents a player in the BlackJack game.
 * The player has a score, a set of cards, and can take actions during their turn.
 */
public class Player {
    public static int score = 0;
    public static int points;
    public static Vector<Card> cards = new Vector<Card>();

    /**
     * Deals two cards to the player and updates the total points.
     * If the deck is exhausted, it is reshuffled.
     */
    public static void setCards() {
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
        points = cards.get(0).points + cards.get(1).points;
    }

    /**
     * Prints the player's current cards and their total point value.
     */
    public static void printCards() {
        System.out.print("     Your cards: [");
        for (int i = 0; i < cards.size(); i++) {
            if (i == cards.size() - 1) {
                System.out.printf("%s (%d)] => %d\n", cards.get(i).nameCard, cards.get(i).points, points);
            } else {
                System.out.printf("%s (%d), ", cards.get(i).nameCard, cards.get(i).points);
            }
        }
    }

    /**
     * Manages the player's turn.
     * The player can choose to draw a card or stop.
     *
     * @return '1' if the player chooses to continue, '0' if the player stops or the round ends.
     */
    public static char step() {
        System.out.println("Your turn\n-------\nEnter '1' to draw a card or '0' to stop...");
        Scanner scanner = new Scanner(System.in);
        String fl = scanner.nextLine();
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        while (fl.equals("1")) {
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            cards.add(BlackJack.cards[counterOfUsedCards]);
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            System.out.printf("You drew %s (%d)\n", BlackJack.cards[counterOfUsedCards].nameCard, BlackJack.cards[counterOfUsedCards].points);
            points += BlackJack.cards[counterOfUsedCards].points;
            BlackJack.counterOfUsedCards++;
            printCards();
            Dealer.printCards('0');
            if (points > 21) {
                Dealer.score++;
                System.out.printf("You lost the round! Score %d:%d.\n", score, Dealer.score);
                return '0';
            } else if (points == 21) {
                score++;
                System.out.printf("You won the round! Score %d:%d.\n", score, Dealer.score);
                return '0';
            }
            System.out.println("Enter '1' to draw a card or '0' to stop...");
            scanner = new Scanner(System.in);
            fl = scanner.nextLine();
        }
        return '1';
    }
}
