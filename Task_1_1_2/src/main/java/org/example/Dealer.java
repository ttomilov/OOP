package org.example;

import java.util.Random;
import java.util.Vector;

public class Dealer {
    public static int score = 0, points;
    public static Vector<Card> cards = new Vector<Card>();

    public void setCards() {
        Random rn = new Random();
        int maximum = 51, minimum = 0;
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        for (int i = 0; i < 2; i++) {
            while (BlackJack.cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            this.cards.add(BlackJack.cards[random]);
            BlackJack.cards[random].flUse = '1';
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
        }
    }

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

    public static char step() {
        System.out.printf("Ход дилера\n-------\nДилер открывает закрытую карту %s %d\n", cards.get(1).nameCard,
                cards.get(1).points);
        Player.printCards();
        printCards('1');
        int maximum = 51, minimum = 0;
        Random rn = new Random();
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        while (points < 17) {
            while (BlackJack.cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            cards.add(BlackJack.cards[random]);
            BlackJack.cards[random].flUse = '1';
            points += BlackJack.cards[random].points;
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            System.out.printf(" %s (%d)\n", BlackJack.cards[random].nameCard, BlackJack.cards[random].points);
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
