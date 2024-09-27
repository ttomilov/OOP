package org.example;

public class BlackJack {
    /**
     * Class of game BlackJack.
     */
    public static int counterOfUsedCards = 0;
    public static Card[] cards = new Card[53];

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
                cards[i] = new Card(names[i], 11, '0');
                point = 2;
            } else if (point == 10) {
                cards[i] = new Card(names[i], point, '0');
            } else {
                cards[i] = new Card(names[i], point, '0');
                point++;
            }
        }
    }

    /**
     * Func to remake deck.
     */
    public static void remakeDeck() {
        for (int i = 0; i < 52; i++) {
            cards[i].flUse = '0';
        }
        counterOfUsedCards = 0;
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
        winCheck(plstep, dlstep);
    }

    /**
     * Func to check, whoes win.
     * @param player
     * @param dealer
     */
    private static void winCheck(char player, char dealer) {
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
