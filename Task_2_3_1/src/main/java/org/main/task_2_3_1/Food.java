package org.main.task_2_3_1;

import java.util.Random;

public class Food {
    private int x, y;

    public Food(int width, int height, Random random) {
        regenerate(width, height, random);
    }

    public void regenerate(int width, int height, Random random) {
        this.x = random.nextInt(width);
        this.y = random.nextInt(height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
