package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Player {
    /**
     * Player class.
     */
    public static int score = 0;
    public static int points;
    public static Vector<Card> cards = new Vector<Card>();

    /**
     * Func to set cards to player.
     */
    public static void setCards() {
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
            cards.add(BlackJack.cards[random]);
            BlackJack.cards[random].flUse = '1';
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
        }
        points = cards.get(0).points + cards.get(1).points;
    }

    /**
     * Func to print player`s cards.
     */
    public static void printCards() {
        System.out.printf("     Ваши карты: [");
        for (int i = 0; i < cards.size(); i++) {
            if (i == cards.size() - 1) {
                System.out.printf("%s (%d)] => %d\n", cards.get(i).nameCard, cards.get(i).points, points);
            } else System.out.printf("%s (%d), ", cards.get(i).nameCard, cards.get(i).points);
        }
    }

    /**
     * Func to player`s step.
     * @return char '1' '0'
     */
    public static char step(){
        System.out.println("Ваш ход\n-------\nВведите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
        Scanner scanner = new Scanner(System.in);
        String fl = scanner.nextLine();
        int maximum = 51, minimum = 0;
        Random rn = new Random();
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        while (fl.equals("1")) {
            while (BlackJack.cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            cards.add(BlackJack.cards[random]);
            BlackJack.cards[random].flUse = '1';
            BlackJack.counterOfUsedCards++;
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            System.out.printf("Вы открыли %s (%d)\n", BlackJack.cards[random].nameCard, BlackJack.cards[random].points);
            points += BlackJack.cards[random].points;
            printCards();
            Dealer.printCards('0');
            if (points > 21) {
                Dealer.score++;
                System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", score, Dealer.score);
                return '0';
            } else if (points == 21) {
                score++;
                System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", score, Dealer.score);
                return '0';
            }
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            scanner = new Scanner(System.in);
            fl = scanner.nextLine();
        }
        return '1';
    }
}
