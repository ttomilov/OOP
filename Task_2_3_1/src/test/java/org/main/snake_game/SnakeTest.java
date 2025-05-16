package org.main.snake_game;

import org.junit.jupiter.api.Test;
import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    @Test
    void testInitialLength() {
        Snake snake = new Snake(5, 5);
        assertEquals(1, snake.getLength());
    }

    @Test
    void testGrow() {
        Snake snake = new Snake(5, 5);
        snake.grow();
        assertEquals(2, snake.getLength());
    }

    @Test
    void testGrowToLength() {
        Snake snake = new Snake(0, 0);
        snake.growToLength(5);
        assertEquals(5, snake.getLength());
    }

    @Test
    void testChangeDirectionAndMove() {
        Snake snake = new Snake(0, 0);
        snake.changeDirection(0, 1);
        snake.move();
        assertEquals(new Point(0, 1), snake.getBody().get(0));
    }

    @Test
    void testCollisionWithSelf() {
        Snake snake = new Snake(0, 0);
        snake.changeDirection(1, 0);
        snake.grow();
        snake.grow();
        snake.changeDirection(0, -1);
        snake.grow();
        snake.changeDirection(-1, 0);
        snake.grow();
        snake.changeDirection(0, 1);
        snake.grow();
        assertTrue(snake.checkCollision(100, 100));
    }
}
