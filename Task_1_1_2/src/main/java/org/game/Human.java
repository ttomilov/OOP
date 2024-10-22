package org.game;

import static org.game.BlackJack.counterOfUsedCards;

import java.util.Scanner;
import java.util.Vector;

/**
 * Represents the dealer in the BlackJack game.
 * The dealer manages their own set of cards and takes actions according to game rules.
 */
class Human extends Player {
    private static int score = 0;
    private static int points;
    private static Vector<Card> cards = new Vector<>();

    @Override    
    void setPoints(int points) {
        Human.points = points;
    }

    @Override
    int getPoints() {
        return points;
    }

    @Override
    void setScore(int score) {
        Human.score = score;
    }

    @Override
    int getScore() {
        return score;
    }

    @Override 
    void setCards() {
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
        setPoints(cards.get(0).getPoints() + cards.get(1).getPoints());
    }

    @Override
    void printCards(boolean fl) {
        System.out.print("     Ваши карты: [");
        for (int i = 0; i < cards.size(); i++) {
            if (i == cards.size() - 1) {
                System.out.printf("%s (%d)] => %d\n", cards.get(i).getNameCard(), cards.get(i).getPoints(), points);
            } else {
                System.out.printf("%s (%d), ", cards.get(i).getNameCard(), cards.get(i).getPoints());
            }
        }
    }

    @Override
    char step(Player pl) {
        if (points == 21) {
            return '1';
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ваш ход\n-------\nВведите '1', чтобы взять карту, и '0', чтобы остановиться...");
        String fl = scanner.nextLine();
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        while (fl.equals("1")) {
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            cards.add(BlackJack.cards[counterOfUsedCards]);
            System.out.printf("Вы открыли карту %s (%d)\n", BlackJack.cards[counterOfUsedCards].getNameCard(), BlackJack.cards[counterOfUsedCards].getPoints());
            setPoints(BlackJack.cards[counterOfUsedCards].getPoints() + getPoints());
            BlackJack.counterOfUsedCards++;
            if (points > 21) {
                for (Card card : cards) {
                    if (card.getPoints() == 11) {
                        card.setPoints(1);
                        setPoints(getPoints() - 10);
                    }
                }
            }
            printCards(true);
            pl.printCards(false);
            if (points == 21) {
                scanner.close();
                return '1';
            }
            if (points > 21) {
                scanner.close();
                return '0';
            }
            System.out.println("Введите '1', чтобы взять карту, и '0', чтобы остановиться...");
            fl = scanner.nextLine();
        }
        return '1';
    }

    @Override
    void clearCards() {
        cards.clear();
    }
}