package org.game;

import static org.game.BlackJack.counterOfUsedCards;

import java.util.Vector;

/**
 * Represents the dealer in the BlackJack game.
 * The dealer manages their own set of cards and takes actions according to game rules.
 */
public class Dealer {
    public static int score = 0;
    public static int points;
    public static Vector<Card> cards = new Vector<Card>();

    /**
     * Deals two cards to the dealer and updates the total points.
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
     * Prints the dealer's current cards. If `fl` is '0', one card remains hidden.
     *
     * @param fl If '0', the second card is hidden; otherwise, all cards are shown.
     */
    public static void printCards(char fl) {
        System.out.printf("     Dealer's cards: [");
        if (fl == '0') {
            System.out.printf("%s (%d), <hidden card>]\n", cards.get(0).nameCard, cards.get(0).points);
        } else {
            for (int i = 0; i < cards.size(); i++) {
                if (i == cards.size() - 1) {
                    System.out.printf("%s (%d)] => %d\n", cards.get(i).nameCard, cards.get(i).points, points);
                } else {
                    System.out.printf("%s (%d), ", cards.get(i).nameCard, cards.get(i).points);
                }
            }
        }
    }

    /**
     * Manages the dealer's turn. The dealer reveals the hidden card and continues drawing until their points reach 17 or more.
     *
     * @return '1' if the dealer's turn continues, '0' if the round ends.
     */
    public static char step() {
        System.out.printf("Dealer's turn\n-------\nDealer reveals hidden card %s (%d)\n", cards.get(1).nameCard,
                cards.get(1).points);
        Player.printCards();
        printCards('1');
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        while (points < 17) {
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            cards.add(BlackJack.cards[counterOfUsedCards]);
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            points += BlackJack.cards[counterOfUsedCards].points;
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            System.out.printf("Dealer reveals card %s (%d)\n", BlackJack.cards[counterOfUsedCards].nameCard, BlackJack.cards[counterOfUsedCards].points);
            BlackJack.counterOfUsedCards++;
            Player.printCards();
            printCards('1');
            if (points > 21) {
                Player.score++;
                System.out.printf("You won the round! Score %d:%d.\n", Player.score, score);
                return '0';
            } else if (points == 21) {
                score++;
                System.out.printf("You lost the round! Score %d:%d.\n", Player.score, score);
                return '0';
            }
        }
        return '1';
    }
}
