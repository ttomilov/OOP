package org.main.task_2_3_1;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameController {
    @FXML private Canvas gameCanvas;
    @FXML private VBox sidePanel;
    @FXML private Label scoreLabel;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    private static final int CELL_SIZE = 20;
    private static final long BASE_SPEED = 150_000_000;

    private int width;
    private int height;
    private GameField gameField;
    private Snake snake;
    private boolean running;
    private AnimationTimer gameLoop;
    private long moveInterval;
    private int score;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        configureFullScreen();
    }

    @FXML
    public void startButton() {
        startButton.setOnAction(e -> restartGame());
        exitButton.setOnAction(e -> System.exit(0));

        gameCanvas.setFocusTraversable(true);
        restartGame();
    }

    private void configureFullScreen() {
        stage.setFullScreen(true);
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        width = (int) ((screenWidth * 0.75) / CELL_SIZE);
        height = (int) (screenHeight / CELL_SIZE);

        gameCanvas.setWidth(screenWidth * 0.75);
        gameCanvas.setHeight(screenHeight);
        sidePanel.setPrefWidth(screenWidth * 0.25);

        restartGame();
    }

    private void restartGame() {
        gameField = new GameField(width, height);
        snake = new Snake(width / 2, height / 2);
        running = true;
        moveInterval = BASE_SPEED;
        score = 0;

        if (gameLoop != null) {
            gameLoop.stop();
        }

        gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= moveInterval) {
                    lastUpdate = now;
                    updateGame();
                }
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        if (!running) return;

        if (snake.checkCollision(width, height)) {
            running = false;
            gameLoop.stop();
            drawGameOver();
            return;
        }

        boolean ateFood = gameField.isFood(snake.getNextHeadPosition());
        if (ateFood) {
            snake.grow();
            score++;
            gameField.generateFood();
            increaseSpeed();
        } else {
            snake.move();
        }

        draw();
    }

    private void increaseSpeed() {
        if (moveInterval > 50_000_000) {
            moveInterval -= 5_000_000;
        }
    }

    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        gc.setFill(Color.RED);
        for (Food food : gameField.getFoodPositions()) {
            gc.fillRect(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.GREEN);
        for (var segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + score);
    }

    private void drawGameOver() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.setFont(new Font(30));
        gc.fillText("Game Over", gameCanvas.getWidth() / 3, gameCanvas.getHeight() / 2);
    }
}
