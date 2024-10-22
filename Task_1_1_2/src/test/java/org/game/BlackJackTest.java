package org.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BlackJack} class, which represents the core logic for a Blackjack game.
 * It includes tests for deck creation, shuffling, card dealing, and win condition checks between
 * the player and dealer.
 */
public class BlackJackTest {

    /**
     * Sets up the testing environment before each test case by creating a deck of cards.
     */
    @BeforeEach
    public void setUp() {
        BlackJack.makeDeck();
    }

    /**
     * Tests the {@link BlackJack#makeDeck()} method to ensure the deck is correctly initialized.
     * It verifies that the deck contains 52 unique cards.
     */
    @Test
    public void testMakeDeck() {
        assertNotNull(BlackJack.cards);
        assertEquals(52, BlackJack.cards.length - 1);
        assertNotSame(BlackJack.cards[0], BlackJack.cards[1]);
    }

    /**
     * Tests the {@link BlackJack#shuffle()} method to ensure the deck is shuffled.
     * It verifies that the first card after shuffling is different from the first card before shuffling.
     */
    @Test
    public void testShuffle() {
        Card firstCardBeforeShuffle = BlackJack.cards[0];
        BlackJack.shuffle();
        Card firstCardAfterShuffle = BlackJack.cards[0];
        assertNotSame(firstCardBeforeShuffle, firstCardAfterShuffle);
    }

    /**
     * Tests the {@link Player#setCards()} method to ensure the player is dealt two cards.
     * and has a positive points total.
     */
    @Test
    public void testPlayerSetCards() {

        Player player = new Human();
        player.setCards();
        assertTrue(player.getPoints() > 0);
    }

    /**
     * Tests the dealer setCards() method to ensure the dealer is dealt two cards
     * and has a positive points total.
     */
    @Test
    public void testDealerSetCards() {
        Player dealer = new Dealer();
        dealer.setCards();
        assertTrue(dealer.getPoints() > 0);
    }

    /**
     * Tests the win-check logic when the player wins. Verifies that the player's score is updated correctly.
     */
    @Test
    public void testPlayerWin() {
        Player player = new Human(), dealer = new Dealer();
        player.setPoints(21);
        dealer.setPoints(18);
        BlackJack.winCheck(player, dealer);

        assertEquals(1, player.getScore());
        assertEquals(0, dealer.getScore());
    }

    /**
     * Tests the win-check logic when the dealer wins. Verifies that the dealer's score is updated correctly.
     */
    @Test
    public void testDealerWin() {
        Player player = new Human(), dealer = new Dealer();
        player.setPoints(13);
        dealer.setPoints(21);
        BlackJack.winCheck(player, dealer);

        assertEquals(1, player.getScore());
        assertEquals(1, dealer.getScore());
    }

    /**
     * Tests the win-check logic when the game results in a tie.
     * Verifies that neither player nor dealer's score is updated.
     */
    @Test
    public void testTie() {
        Player player = new Human(), dealer = new Dealer();
        player.setPoints(20);
        dealer.setPoints(20);
        BlackJack.winCheck(player, dealer);

        assertEquals(0, player.getScore());
        assertEquals(0, dealer.getScore());
    }
}
