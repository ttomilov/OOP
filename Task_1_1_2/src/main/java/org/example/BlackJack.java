package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlackJack {
    /**
     * Class of game BlackJack.
     */
    public static int counterOfUsedCards = 0;
    public static Card[] cards = new Card[53];

    /**
     * Func to shuffle deck.
     */
    public static void shuffle() {
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);
    }

    /**
     * Func to make deck.
     */
    public static void makeDeck() {
        String[] names = new String[]{
                "Пиковая Двойка", "Пиковая Тройка", "Пиковая Четверка", "Пиковая Пятерка", "Пиковая Шестерка",
                "Пиковая Семерка", "Пиковая Восьмерка", "Пиковая Девятка", "Пиковая Десятка", "Пиковый Валет",
                "Пиковая Дама", "Пиковый Король", "Пиковый Туз",

                "Червовая Двойка", "Червовая Тройка", "Червовая Четверка", "Червовая Пятерка", "Червовая Шестерка",
                "Червовая Семерка", "Червовая Восьмерка", "Червовая Девятка", "Червовая Десятка", "Червовый Валет",
                "Червовая Дама", "Червовый Король", "Червовый Туз",

                "Бубновая Двойка", "Бубновая Тройка", "Бубновая Четверка", "Бубновая Пятерка", "Бубновая Шестерка",
                "Бубновая Семерка", "Бубновая Восьмерка", "Бубновая Девятка", "Бубновая Десятка", "Бубновый Валет",
                "Бубновая Дама", "Бубновый Король", "Бубновый Туз",

                "Двойка Треф", "Тройка Треф", "Четверка Треф", "Пятерка Треф", "Шестерка Треф", "Семерка Треф",
                "Восьмерка Треф", "Девятка Треф", "Десятка Треф", "Валет Треф", "Дама Треф", "Король Треф", "Туз Треф"
        };
        int point = 2;
        for (int i = 0; i < 52; i++) {
            if (i == 12 || i == 25 || i == 38 || i == 51) {
                cards[i] = new Card(names[i], 11);
                point = 2;
            } else if (point == 10) {
                cards[i] = new Card(names[i], point);
            } else {
                cards[i] = new Card(names[i], point);
                point++;
            }
        }
        shuffle();
    }

    /**
     * Func to remake deck.
     */
    public static void remakeDeck() {
        counterOfUsedCards = 0;
        shuffle();
    }

    /**
     * main func of game.
     * @param round
     */
    public static void game(int round) {
        if (round == 1) {
            System.out.println("Добро пожаловать в Блекджек");
        }
        System.out.printf("Раунд %d\nДилер раздал карты:\n", round);
        Player.setCards();
        Dealer.setCards();
        Player.printCards();
        Dealer.printCards('0');
        char plstep = Player.step();
        if (plstep == '0') {
            Player.cards.clear();
            Dealer.cards.clear();
            return;
        }
        char dlstep = Dealer.step();
        if (dlstep == '0') {
            Player.cards.clear();
            Dealer.cards.clear();
            return;
        }
        winCheck();
    }

    /**
     * Func to check, whoes win.
     */
    public static void winCheck() {
        if (Dealer.points > Player.points) {
            Dealer.score++;
            System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", Player.score, Dealer.score);
        } else if (Dealer.points < Player.points) {
            Player.score++;
            System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", Player.score, Dealer.score);
        } else {
            System.out.println("Ничья!\n");

        }
        Player.cards.clear();
        Dealer.cards.clear();
    }
}
