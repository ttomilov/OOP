package org.game;

import java.util.Scanner;

/**
 * Main class that starts the BlackJack game.
 * It contains the method to initialize and manage game rounds.
 */
public class Main {

    /**
     * The main method that launches the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        int round = 1;
        Player human = new Human();
        Player dealer = new Dealer();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            BlackJack.game(round, human, dealer);
            System.out.print("Хотите ли вы продолжить игру? [Y/N]: ");
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