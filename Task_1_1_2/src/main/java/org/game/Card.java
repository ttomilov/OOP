package org.game;

/**
 * Represents a card in the BlackJack game.
 * Each card has a name and a point value.
 */
public class Card {
    public String nameCard;
    public int points;

    /**
     * Constructor that initializes the card with its name and point value.
     *
     * @param name the name of the card
     * @param point the point value of the card
     */
    public Card(String name, int point) {
        this.nameCard = name;
        this.points = point;
    }
}
