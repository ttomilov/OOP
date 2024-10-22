package org.game;

abstract class Player {
    abstract void setPoints(
            int point
    );

    abstract int getPoints();

    abstract void setScore(
            int score
    );

    abstract int getScore();

    abstract void setCards();

    abstract void printCards(
            boolean fl
    );

    abstract char step(
            Player pl
    );

    abstract void clearCards();
}
