    package org.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.game.Player.Status;

/**
 * Represents the BlackJack game.
 * Manages deck creation, shuffling, and game logic.
 */
public class BlackJack {
    public static int counterOfUsedCards = 0;
    public static Card[] cards = new Card[]{
        new Card("Пиковая двойка", 2),
        new Card("Пиковая тройка", 3),
        new Card("Пиковая четвёрка", 4),
        new Card("Пиковая пятёрка", 5),
        new Card("Пиковая шестерка", 6),
        new Card("Пиковая семёрка", 7),
        new Card("Пиковая восьмёрка", 8),
        new Card("Пиковая девятка", 9),
        new Card("Пиковая десятка", 10),
        new Card("Пиковый валет", 10),
        new Card("Пиковая дама", 10),
        new Card("Пиковый король", 10),
        new Card("Пиковый туз", 11),

        new Card("Червоная двойка", 2),
        new Card("Червоная тройка", 3),
        new Card("Червоная четвёрка", 4),
        new Card("Червоная пятёрка", 5),
        new Card("Червоная шестерка", 6),
        new Card("Червоная семёрка", 7),
        new Card("Червоная восьмёрка", 8),
        new Card("Червоная девятка", 9),
        new Card("Червоная десятка", 10),
        new Card("Червонный валет", 10),
        new Card("Червоная дама", 10),
        new Card("Червонный король", 10),
        new Card("Червонный туз", 11),

        new Card("Бубновая двойка", 2),
        new Card("Бубновая тройка", 3),
        new Card("Бубновая четвёрка", 4),
        new Card("Бубновая пятёрка", 5),
        new Card("Бубновая шестерка", 6),
        new Card("Бубновая семёрка", 7),
        new Card("Бубновая восьмёрка", 8),
        new Card("Бубновая девятка", 9),
        new Card("Бубновая десятка", 10),
        new Card("Бубновый валет", 10),
        new Card("Бубновая дама", 10),
        new Card("Бубновый король", 10),
        new Card("Бубновый туз", 11),

        new Card("Трефовая двойка", 2),
        new Card("Трефовая тройка", 3),
        new Card("Трефовая четвёрка", 4),
        new Card("Трефовая пятёрка", 5),
        new Card("Трефовая шестерка", 6),
        new Card("Трефовая семёрка", 7),
        new Card("Трефовая восьмёрка", 8),
        new Card("Трефовая девятка", 9),
        new Card("Трефовая десятка", 10),
        new Card("Трефовый валет", 10),
        new Card("Трефовая дама", 10),
        new Card("Трефовый король", 10),
        new Card("Трефовый туз", 11)
    };


    /**
     * Shuffles the deck of cards.
     */
    public static void shuffle() {
        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList);
        cardList.toArray(cards);
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
        shuffle();
        if (round == 1) {
            System.out.println("Добро пожаловать в Блекджек");
        }
        System.out.printf("Раунд %d\nДилер раздал карты:\n", round);
        human.setCards();
        dealer.setCards();
        human.printCards(true, true, human);
        dealer.printCards(false, true, dealer);
        if (human.step(human, dealer) == Status.LOSE) {
            dealer.setScore(dealer.getScore() + 1);
            System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", human.getScore(), dealer.getScore());
            human.clearCards();
            dealer.clearCards();
            return;
        }
        if (dealer.step(human, dealer) == Status.LOSE) {
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
