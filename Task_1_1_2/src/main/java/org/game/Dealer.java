package org.game;

import static org.game.BlackJack.counterOfUsedCards;

import java.util.Vector;

/**
 * Represents the dealer in the BlackJack game.
 * The dealer manages their own set of cards and takes actions according to game rules.
 */
class Dealer extends Player {
    private static int score = 0;
    private static int points;
    private static Vector<Card> cards = new Vector<>();

    @Override
    int getPoints() {
        return points;
    }

    @Override
    void setPoints(int points) {
        Dealer.points = points;
    }

    @Override
    void setScore(int score) {
        Dealer.score = score;
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
        System.out.print("     Карты дилера: [");
        if (!fl) {
            System.out.printf("%s (%d), <зарытая карта>]\n", cards.get(0).getNameCard(), cards.get(0).getPoints());
        } else {
            for (int i = 0; i < cards.size(); i++) {
                if (i == cards.size() - 1) {
                    System.out.printf("%s (%d)] => %d\n", cards.get(i).getNameCard(), cards.get(i).getPoints(), getPoints());
                } else {
                    System.out.printf("%s (%d), ", cards.get(i).getNameCard(), cards.get(i).getPoints());
                }
            }
        }
    }

    @Override
    char step(Player pl) {
        System.out.printf("Ход дилера\n-------\nДилер открыл скрытую карту %s (%d)\n", cards.get(1).getNameCard(), cards.get(1).getPoints());
        pl.printCards(true);
        printCards(true);
        if (points == 21) {
            return '1';
        }
        if (BlackJack.counterOfUsedCards == 52) {
            BlackJack.remakeDeck();
        }
        while (points < 17) {
            if (BlackJack.counterOfUsedCards == 52) {
                BlackJack.remakeDeck();
            }
            cards.add(BlackJack.cards[counterOfUsedCards]);
            System.out.printf("Дилер открывает карту %s (%d)\n", BlackJack.cards[counterOfUsedCards].getNameCard(), BlackJack.cards[counterOfUsedCards].getPoints());
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
            pl.printCards(true);
            printCards(true);
            if (points == 21) {
                return '1';
            }
            if (points > 21) {
                return '0';
            }
        }
        return '1';
    }

    @Override
    void clearCards() {
        cards.clear();
    }
}