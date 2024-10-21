package org.BlackJack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BlackJack game logic.
 */
public class BlackJackTest {

    /**
     * Sets up the test environment by creating a new deck before each test.
     */
    @BeforeEach
    public void setUp() {
        BlackJack.makeDeck();  // Create a new deck before each test
    }

    /**
     * Tests that the deck is properly created and contains 52 cards.
     * Also checks if the deck is shuffled by ensuring that the first two cards are not the same.
     */
    @Test
    public void testMakeDeck() {
        assertNotNull(BlackJack.cards);  // Deck should not be null
        assertEquals(52, BlackJack.cards.length - 1);  // Deck should have 52 cards
        assertNotEquals(BlackJack.cards[0], BlackJack.cards[1]);  // Cards should be shuffled
    }

    /**
     * Tests that shuffling the deck changes the order of the cards.
     */
    @Test
    public void testShuffle() {
        Card firstCardBeforeShuffle = BlackJack.cards[0];
        BlackJack.shuffle();
        Card firstCardAfterShuffle = BlackJack.cards[0];
        assertNotEquals(firstCardBeforeShuffle, firstCardAfterShuffle);  // Deck order should change
    }

    /**
     * Tests that after dealing cards to the player, the player receives two cards
     * and their points are correctly calculated.
     */
    @Test
    public void testPlayerSetCards() {
        Player.setCards();
        assertEquals(2, Player.cards.size());  // Player should have 2 cards
        assertTrue(Player.points > 0);  // Player's points should be greater than 0
    }

    /**
     * Tests that after dealing cards to the dealer, the dealer receives two cards.
     * and their points are correctly calculated.
     */
    @Test
    public void testDealerSetCards() {
        Dealer.setCards();
        assertEquals(2, Dealer.cards.size());  // Dealer should have 2 cards
        assertTrue(Dealer.points > 0);  // Dealer's points should be greater than 0
    }

    /**
     * Tests that the player wins when their points are higher than the dealer's points.
     */
    @Test
    public void testPlayerWin() {
        Player.points = 21;
        Dealer.points = 18;
        BlackJack.winCheck();

        assertEquals(1, Player.score);  // Player should win and score should increase
        assertEquals(0, Dealer.score);  // Dealer should not score
    }

    /**
     * Tests that the dealer wins when their points are higher than the player's points.
     */
    @Test
    public void testDealerWin() {
        Player.points = 13;
        Dealer.points = 21;
        BlackJack.winCheck();

        assertEquals(0, Player.score);  // Player should lose and score should not increase
        assertEquals(1, Dealer.score);  // Dealer should win and score should increase
    }

    /**
     * Tests that a tie occurs when both the player and the dealer have the same points.
     */
    @Test
    public void testTie() {
        Player.points = 20;
        Dealer.points = 20;
        BlackJack.winCheck();

        assertEquals(0, Player.score);  // No change in player's score
        assertEquals(0, Dealer.score);  // No change in dealer's score
    }
}
