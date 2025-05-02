package org.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link BlackJack} class, which represents the core logic for a Blackjack game.
 * It includes tests for deck creation, shuffling, card dealing, and win condition checks between
 * the player and dealer.
 */
public class BlackJackTest {

    @BeforeEach
    public void setUp() {
        BlackJack.counterOfUsedCards = 0;  // Reset card counter
        BlackJack.remakeDeck();            // Reset deck
    }

    /**
     * Tests the {@link BlackJack#shuffle()} method to ensure the deck is shuffled.
     * It verifies that at least one card position has changed after shuffling.
     */
    @Test
    public void testShuffle() {
        Card[] deckBeforeShuffle = Arrays.copyOf(BlackJack.cards, BlackJack.cards.length);
        BlackJack.shuffle();
        boolean isShuffled = false;

        for (int i = 0; i < deckBeforeShuffle.length; i++) {
            if (deckBeforeShuffle[i] != BlackJack.cards[i]) {
                isShuffled = true;
                break;
            }
        }
        assertTrue(isShuffled, "Deck was not shuffled correctly");
    }

    /**
     * Tests the {@link Player#setCards()} method to ensure the player is dealt two cards
     * and has a positive points total.
     */
    @Test
    public void testPlayerSetCards() {
        Player player = new Human();
        player.setCards();
        assertEquals(2, player.cards.size(), "Player should be dealt two cards");
        assertTrue(player.getPoints() > 0, "Player's points should be positive");
    }

    /**
     * Tests the dealer setCards() method to ensure the dealer is dealt two cards
     * and has a positive points total.
     */
    @Test
    public void testDealerSetCards() {
        Player dealer = new Dealer();
        dealer.setCards();
        assertEquals(2, dealer.cards.size(), "Dealer should be dealt two cards");
        assertTrue(dealer.getPoints() > 0, "Dealer's points should be positive");
    }

    /**
     * Tests the win-check logic when the player wins. Verifies that the player's score is updated correctly.
     */
    @Test
    public void testPlayerWin() {
        Player player = new Human();
        Player dealer = new Dealer();
        player.setPoints(21);
        dealer.setPoints(18);
        BlackJack.winCheck(player, dealer);

        assertEquals(1, player.getScore(), "Player score should be 1 after win");
        assertEquals(0, dealer.getScore(), "Dealer score should be 0 after player win");
    }

    /**
     * Tests the win-check logic when the dealer wins. Verifies that the dealer's score is updated correctly.
     */
    @Test
    public void testDealerWin() {
        Player player = new Human();
        Player dealer = new Dealer();
        player.setPoints(13);
        dealer.setPoints(21);
        BlackJack.winCheck(player, dealer);

        assertEquals(0, player.getScore(), "Player score should be 0 after loss");
        assertEquals(1, dealer.getScore(), "Dealer score should be 1 after win");
    }

    /**
     * Tests the win-check logic when the game results in a tie.
     * Verifies that neither player nor dealer's score is updated.
     */
    @Test
    public void testTie() {
        Player player = new Human();
        Player dealer = new Dealer();
        player.setPoints(20);
        dealer.setPoints(20);
        BlackJack.winCheck(player, dealer);

        assertEquals(0, player.getScore(), "Player score should not change after tie");
        assertEquals(0, dealer.getScore(), "Dealer score should not change after tie");
    }
}
