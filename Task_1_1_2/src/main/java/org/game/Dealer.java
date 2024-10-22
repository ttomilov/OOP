package org.game;

import static org.game.BlackJack.counterOfUsedCards;

import java.util.Vector;

public class Dealer {

    /**
     * Class to dealer.
     */
    public static int score = 0, points;
    public static Vector<Card> cards = new Vector<Card>();

    /**
     * Func to set cards to dealer.
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
     * Func to print dealer`s cards.
     */
    public static void printCards(char fl){
        System.out.printf("     Карты дилера: [");
        if (fl == '0') {
            System.out.printf("%s (%d), <закрытая карта>]\n", cards.get(0).nameCard, cards.get(0).points);
        } else {
            for (int i = 0; i < cards.size(); i++) {
                if (i == cards.size() - 1) {
                    System.out.printf("%s (%d)] => %d\n", cards.get(i).nameCard, cards.get(i).points, points);
                } else System.out.printf("%s (%d), ", cards.get(i).nameCard, cards.get(i).points);
            }
        }
    }

    /**
     * Func to dealer`s step.
     * @return char '1' '0'
     */
    public static char step() {
        System.out.printf("Ход дилера\n-------\nДилер открывает закрытую карту %s %d\n", cards.get(1).nameCard,
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
            System.out.printf("Дилер открывает карту %s (%d)\n", BlackJack.cards[counterOfUsedCards].nameCard, BlackJack.cards[counterOfUsedCards].points);
            BlackJack.counterOfUsedCards++;
            Player.printCards();
            printCards('1');
            if (points > 21) {
                Player.score++;
                System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", Player.score, score);
                return '0';
            } else if (points == 21) {
                score++;
                System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", Player.score, score);
                return '0';
            }
        }
        return '1';
    }

}
