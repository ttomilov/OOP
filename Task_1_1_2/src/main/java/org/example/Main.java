package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BlackJack.makeDeck();
        int round = 1;
        while (true) {
            BlackJack.game(round++);
            System.out.printf("Хотите завершить игру?[Y/N]: ");
            Scanner scanner = new Scanner(System.in);
            String fl = scanner.nextLine();
            if (fl.equals("Y") || fl.equals("y") || fl.equals("Д") || fl.equals("д")) break;
        }
    }
}