package org.game;

import java.util.Scanner;

public class Main {
    /**
     *Class for main.
     */
    public static void main(String[] args) {
        BlackJack.makeDeck();
        int round = 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            BlackJack.game(round);
            System.out.print("Хотите завершить игру? [Y/N]: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("д")) {
                break;
            }
            System.out.print("\033[H\033[J");
            round++;
        }
        scanner.close();
    }
}