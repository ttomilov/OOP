package org.example;

import java.util.Scanner;

import static org.example.BlackJack.game;
import static org.example.BlackJack.makeDeck;

public class Main {
    public static void main(String[] args) {
        Cards[] cards = new Cards[53];
        makeDeck(cards);
        int round = 1;
        while (true) {
            game(cards, round++);
            System.out.printf("Хотите завершить игру?[Y/N]: ");
            Scanner scanner = new Scanner(System.in);
            String fl = scanner.nextLine();
            if (fl.equals("Y") || fl.equals("y") || fl.equals("Д") || fl.equals("д")) break;
        }
    }
}