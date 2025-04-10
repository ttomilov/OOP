package org.main.snake_game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private LinkedList<Point> body;
    private int dx = 1, dy = 0;

    public Snake(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
    }

    public void changeDirection(int dx, int dy) {
        if (this.dx == -dx && this.dy == -dy) return;
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        Point head = getNextHeadPosition();
        body.addFirst(head);
        body.removeLast();
    }

    public void grow() {
        Point head = getNextHeadPosition();
        body.addFirst(head);
    }

    public void growToLength(int newLength) {
        while (body.size() < newLength) {
            grow();
        }
    }

    public Point getNextHeadPosition() {
        Point currentHead = body.getFirst();
        return new Point(currentHead.x + dx, currentHead.y + dy);
    }

    public boolean checkCollision(int width, int height) {
        Point head = body.getFirst();

        if (head.x < 0 || head.x >= width || head.y < 0 || head.y >= height) {
            return true;
        }

        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }

        return false;
    }

    public List<Point> getBody() {
        return body;
    }

    public int getLength() {
        return body.size();
    }
}
