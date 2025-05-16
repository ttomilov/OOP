package org.game;

/**
 * Represents a card in the Blackjack game.
 * Each card has a name and a point value.
 */
public class Card {
    private final String nameCard;
    private int points;

    /**
     * Constructor that initializes the card with its name and point value.
     *
     * @param name  the name of the card
     * @param point the point value of the card
     */
    public Card(String name, int point) {
        this.nameCard = name;
        this.points = point;
    }

    /**
     * Gets the point value of the card.
     *
     * @return the point value of the card.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the name of the card.
     *
     * @return the name of the card.
     */
    public String getNameCard() {
        return nameCard;
    }

    /**
     * Sets a new point value for the card.
     *
     * @param points the new point value for the card.
     */
    public void setPoints(int points) {
        this.points = points;
    }
}
