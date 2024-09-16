package org.example;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class BlackJack {

    public static int yourScore = 0, yourPoints;
    public static int botsScore = 0, botsPoints;
    public static int counterOfUsedCards = 0;

    public static void makeDeck(Cards[] cards){
        String[] names = new String[] {
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
        for (int i = 0; i < 52; i++){
            if (i == 12 || i == 25 || i == 38 || i == 51){
                cards[i] = new Cards(names[i], 11, '0');
                point = 2;
            }
            else if (point == 10){
                cards[i] = new Cards(names[i], point, '0');
            }
            else{
                cards[i] = new Cards(names[i], point, '0');
                point++;
            }
        }
    }

    public static void game(Cards[] cards, int round){
        if (round == 1){
            System.out.println("Добро пожаловать в Блэкджэк!");
        }
        System.out.printf("Раунд %d\n", round);
        Vector<Cards> yourCards = new Vector<Cards>(), botsCards = new Vector<Cards>();
        setCards(yourCards, botsCards, cards);
        yourPoints = yourCards.getFirst().points + yourCards.get(1).points;
        botsPoints = botsCards.getFirst().points + botsCards.get(1).points;
        System.out.println("Дилер раздал карты");
        printCards(yourCards, botsCards, '0');
        char you = yourStep(yourCards, botsCards, cards);
        if (you == '0') return;
        char bot = botsStep(yourCards, botsCards, cards);
        if (bot == '0') return;
        if (yourPoints > botsPoints){
            yourScore++;
            System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
        }
        else if (yourPoints < botsPoints){
            botsScore++;
            System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
        }
        else{
            System.out.printf("Ничья! Счёт %d:%d\n", yourScore, botsScore);
        }
    }

    public static void setCards(Vector<Cards> yourCards, Vector<Cards> botsCards, Cards[] cards) {
        Random rn = new Random();
        int maximum = 51, minimum = 0;
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        if (counterOfUsedCards == 52){
            remakeDeck(cards);
        }
        for (int i = 0; i < 2; i++) {
            while (cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            yourCards.add(cards[random]);
            cards[random].flUse = '1';
            counterOfUsedCards++;
            if (counterOfUsedCards == 52){
                remakeDeck(cards);
            }
        }
        for (int i = 0; i < 2; i++) {
            random = rn.nextInt(maximum - minimum + 1) + minimum;
            while (cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            botsCards.add(cards[random]);
            cards[random].flUse = '1';
            counterOfUsedCards++;
            if (counterOfUsedCards == 52){
                remakeDeck(cards);
            }
        }
    }

    public static void printCards(Vector<Cards> yourCards, Vector<Cards> botsCards, char fl){
        System.out.printf("     Ваши карты: [");
        for (int i = 0; i < yourCards.size(); i++){
            if (i == yourCards.size() - 1){
                System.out.printf("%s (%d)] => %d\n", yourCards.get(i).nameCard, yourCards.get(i).points, yourPoints);
            }
            else System.out.printf("%s (%d), ", yourCards.get(i).nameCard, yourCards.get(i).points);
        }
        System.out.printf("     Карты дилера: [");
        if (fl == '0'){
            System.out.printf("%s (%d), <закрытая карта>]\n", botsCards.getFirst().nameCard, botsCards.getFirst().points);
        }
        else{
            for (int i = 0; i < botsCards.size(); i++){
                if (i == botsCards.size() - 1){
                    System.out.printf("%s (%d)] => %d\n", botsCards.get(i).nameCard, botsCards.get(i).points, botsPoints);
                }
                else System.out.printf("%s (%d), ", botsCards.get(i).nameCard, botsCards.get(i).points);
            }
        }
    }

    public static char yourStep(Vector<Cards> yourCards, Vector<Cards> botsCards, Cards[] cards){
        System.out.println("Ваш ход\n-------\nВведите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
        Scanner scanner = new Scanner(System.in);
        String fl = scanner.nextLine();
        int maximum = 51, minimum = 0;
        Random rn = new Random();
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        if (counterOfUsedCards == 52){
            remakeDeck(cards);
        }
        while (fl.equals("1")){
            while (cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            yourCards.add(cards[random]);
            cards[random].flUse = '1';
            counterOfUsedCards++;
            if (counterOfUsedCards == 52){
                remakeDeck(cards);
            }
            System.out.printf("Вы открыли %s (%d)\n", cards[random].nameCard, cards[random].points);
            yourPoints += cards[random].points;
            printCards(yourCards, botsCards, '0');
            if (yourPoints > 21){
                botsScore++;
                System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
                return '0';
            }
            else if (yourPoints == 21){
                yourScore++;
                System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
                return '0';
            }
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            scanner = new Scanner(System.in);
            fl = scanner.nextLine();
        }
        return '1';
    }

    public static char botsStep(Vector<Cards> yourCards, Vector<Cards> botsCards, Cards[] cards){
        System.out.printf("Ход дилера\n-------\nДилер открывает закрытую карту %s %d\n", botsCards.get(1).nameCard,
                botsCards.get(1).points);
        printCards(yourCards, botsCards, '1');
        int maximum = 51, minimum = 0;
        Random rn = new Random();
        if (counterOfUsedCards == 52){
            remakeDeck(cards);
        }
        int random = rn.nextInt(maximum - minimum + 1) + minimum;
        while (botsPoints < 17){
            while (cards[random].flUse == '1') {
                random = rn.nextInt(maximum - minimum + 1) + minimum;
            }
            botsCards.add(cards[random]);
            cards[random].flUse = '1';
            botsPoints += cards[random].points;
            counterOfUsedCards++;
            if (counterOfUsedCards == 52){
                remakeDeck(cards);
            }
            System.out.printf(" %s (%d)\n", cards[random].nameCard, cards[random].points);
            printCards(yourCards, botsCards, '1');
            if (botsPoints > 21){
                yourScore++;
                System.out.printf("Вы выиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
                return '0';
            }
            else if (botsPoints == 21){
                botsScore++;
                System.out.printf("Вы проиграли раунд! Счёт %d:%d.\n", yourScore, botsScore);
                return '0';
            }
        }
        return '1';
    }

    public  static void remakeDeck(Cards[] cards){
        for (int i = 0; i < 52; i++){
            cards[i].flUse = '0';
        }
        counterOfUsedCards = 0;
    }
}
