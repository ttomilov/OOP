package org.BlackJack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.BlackJack.Card;

import static org.junit.jupiter.api.Assertions.*;

public class BlackJackTest {

    @BeforeEach
    public void setUp() {
        BlackJack.makeDeck();
    }

    @Test
    public void testMakeDeck() {
        assertNotNull(BlackJack.cards);
        assertEquals(52, BlackJack.cards.length - 1);
        assertNotSame(BlackJack.cards[0], BlackJack.cards[1]);
    }

    @Test
    public void testShuffle() {
        Card firstCardBeforeShuffle = BlackJack.cards[0];
        BlackJack.shuffle();
        Card firstCardAfterShuffle = BlackJack.cards[0];
        assertNotSame(firstCardBeforeShuffle, firstCardAfterShuffle);
    }

    @Test
    public void testPlayerSetCards() {
        Player.setCards();
        assertEquals(2, Player.cards.size());
        assertTrue(Player.points > 0);
    }

    @Test
    public void testDealerSetCards() {
        Dealer.setCards();
        assertEquals(2, Dealer.cards.size());
        assertTrue(Dealer.points > 0);
    }

    @Test
    public void testPlayerWin() {
        Player.points = 21;
        Dealer.points = 18;
        BlackJack.winCheck();

        assertEquals(1, Player.score);
        assertEquals(0, Dealer.score);
    }

    @Test
    public void testDealerWin() {
        Player.points = 13;
        Dealer.points = 21;
        BlackJack.winCheck();

        assertEquals(1, Player.score);
        assertEquals(1, Dealer.score);
    }


    @Test
    public void testTie() {
        Player.points = 20;
        Dealer.points = 20;
        BlackJack.winCheck();

        assertEquals(0, Player.score);
        assertEquals(0, Dealer.score);
    }
}
