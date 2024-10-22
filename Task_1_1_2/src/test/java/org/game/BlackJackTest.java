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
        assertNotNull(BlackJack.cards, "Deck should not be null after creation.");
        assertEquals(52, BlackJack.cards.length - 1, "Deck should contain 52 cards.");
        assertNotSame(BlackJack.cards[0], BlackJack.cards[1], "First two cards in the deck should be different.");
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
        assertNotSame(firstCardBeforeShuffle, firstCardAfterShuffle, "First card should change after shuffling.");
    }

    /**
     * Tests the {@link Player#setCards()} method to ensure the player is dealt two cards
     * and has a positive points total.
     */
    @Test
    public void testPlayerSetCards() {
        Player.setCards();
        assertEquals(2, Player.cards.size(), "Player should be dealt two cards.");
        assertTrue(Player.points > 0, "Player's points should be greater than zero.");
    }

    /**
     * Tests the {@link Dealer#setCards()} method to ensure the dealer is dealt two cards
     * and has a positive points total.
     */
    @Test
    public void testDealerSetCards() {
        Dealer.setCards();
        assertEquals(2, Dealer.cards.size(), "Dealer should be dealt two cards.");
        assertTrue(Dealer.points > 0, "Dealer's points should be greater than zero.");
    }

    /**
     * Tests the win-check logic when the player wins. Verifies that the player's score is updated correctly.
     */
    @Test
    public void testPlayerWin() {
        Player.points = 21;
        Dealer.points = 18;
        BlackJack.winCheck();

        assertEquals(1, Player.score, "Player should win and score should be incremented.");
        assertEquals(0, Dealer.score, "Dealer's score should remain unchanged.");
    }

    /**
     * Tests the win-check logic when the dealer wins. Verifies that the dealer's score is updated correctly.
     */
    @Test
    public void testDealerWin() {
        Player.points = 13;
        Dealer.points = 21;
        BlackJack.winCheck();

        assertEquals(1, Player.score, "Player's score should remain unchanged.");
        assertEquals(1, Dealer.score, "Dealer should win and score should be incremented.");
    }

    /**
     * Tests the win-check logic when the game results in a tie. Verifies that neither player nor dealer's score is updated.
     */
    @Test
    public void testTie() {
        Player.points = 20;
        Dealer.points = 20;
        BlackJack.winCheck();

        assertEquals(0, Player.score, "Player's score should remain unchanged in case of a tie.");
        assertEquals(0, Dealer.score, "Dealer's score should remain unchanged in case of a tie.");
    }
}
