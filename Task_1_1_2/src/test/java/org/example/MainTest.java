package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.example.BlackJack.game;
import static org.example.BlackJack.makeDeck;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main() {
        Cards[] cards = new Cards[53];
        makeDeck(cards);
        ByteArrayInputStream test = new ByteArrayInputStream("1\n0\n1\n0\n1\n0\n1\n0\n1\n0\n1\n1\n0\n1\n1\n0\n1\n1\n0\n1\n".getBytes());
        System.setIn(test);
        game(cards, 1);
    }
}