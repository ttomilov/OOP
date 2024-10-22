package org.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the BlackJack game.
 * Manages deck creation, shuffling, and game logic.
 */
public class BlackJack {
    public static int counterOfUsedCards = 0;
    public static Card[] cards = new Card[53];

    /**
     * Shuffles the deck of cards.
     */
    public static void shuffle() {
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);
    }

    /**
     * Creates a new deck of cards.
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
     * Reshuffles the deck and resets the used card counter.
     */
    public static void remakeDeck() {
        counterOfUsedCards = 0;
        shuffle();
    }

    /**
     * Main game logic.
     *
     * @param round the current round number
     */
    public static void game(int round, Player human, Player dealer) {
        if (round == 1) {
            System.out.println("Добро пожаловать в Блекджек");
        }
        System.out.printf("Раунд %d\nДилер раздал карты:\n", round);
        human.setCards();
        dealer.setCards();
        human.printCards(true);
        dealer.printCards(false);
        char plstep = human.step(dealer);
        if (plstep == '0') {
            dealer.setScore(dealer.getScore() + 1);
            System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", human.getScore(), dealer.getScore());
            human.clearCards();
            dealer.clearCards();
            return;
        }
        char dlstep = dealer.step(human);
        if (dlstep == '0') {
            human.setScore(human.getScore() + 1);
            System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", human.getScore(), dealer.getScore());
            human.clearCards();
            dealer.clearCards();
            return;
        }
        winCheck(human, dealer);
    }

    /**
     * Checks who won the current round.
     */
    public static void winCheck(Player human, Player dealer) {
        if (dealer.getPoints() > human.getPoints()) {
            dealer.setScore(dealer.getScore() + 1);
            System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", human.getScore(), dealer.getScore());
        } else if (dealer.getPoints() < human.getPoints()) {
            human.setScore(human.getScore() + 1);
            System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", human.getScore(), dealer.getScore());
        } else {
            System.out.println("Ничья!\n");

        }
        human.clearCards();
        dealer.clearCards();
    }
}
