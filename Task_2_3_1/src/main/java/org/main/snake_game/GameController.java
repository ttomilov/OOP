package org.main.snake_game;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Point;
import java.io.IOException;

public class GameController {
    @FXML public Button fileLoaderButton;
    @FXML private Canvas gameCanvas;
    @FXML private VBox sidePanel;
    @FXML private Label scoreLabel;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    private static final int CELL_SIZE = 20;
    private static final long BASE_SPEED = 150_000_000;
    private static final long MIN_SPEED = 50_000_000;
    private static final long SPEED_INCREMENT = 5_000_000;

    private int width;
    private int height;
    private GameField gameField;
    private Snake snake;
    private boolean running;
    private AnimationTimer gameLoop;
    private long moveInterval;
    private int score;
    private Stage stage;

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> restartGame());
        exitButton.setOnAction(e -> System.exit(0));
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        configureFullScreen();
    }

    private void configureFullScreen() {
        if (stage == null) return;

        stage.setFullScreen(true);
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        width = (int) ((screenWidth * 0.75) / CELL_SIZE);
        height = (int) (screenHeight / CELL_SIZE);

        gameCanvas.setWidth(screenWidth * 0.75);
        gameCanvas.setHeight(screenHeight);
        sidePanel.setPrefWidth(screenWidth * 0.25);

        restartGame();
    }

    private void handleKeyPress(KeyEvent event) {
        if (!running || snake == null) return;

        KeyCode code = event.getCode();
        switch (code) {
            case W -> snake.changeDirection(0, -1);
            case S -> snake.changeDirection(0, 1);
            case A -> snake.changeDirection(-1, 0);
            case D -> snake.changeDirection(1, 0);
        }
    }

    private void restartGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        try {
            gameField = new GameField(width, height);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        snake = new Snake(width / 2, height / 2);
        running = true;
        moveInterval = BASE_SPEED;
        score = 0;

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
        gameCanvas.requestFocus();
    }

    private void updateGame() {
        if (!running || snake == null || gameField == null) return;

        if (snake.checkCollision(width, height)) {
            endGame();
            return;
        }

        Point nextHead = snake.getNextHeadPosition();
        Food food = gameField.getFoodAt(nextHead);

        if (food != null) {
            int oldLen = snake.getLength();
            int newLen = food.getType().applyBonus(oldLen);
            snake.growToLength(newLen);
            score++;
            gameField.generateFood();
            increaseSpeed();
        } else {
            snake.move();
        }

        draw();
    }

    private void endGame() {
        running = false;
        if (gameLoop != null) {
            gameLoop.stop();
        }
        drawGameOver();
    }

    private void increaseSpeed() {
        if (moveInterval > MIN_SPEED) {
            moveInterval -= SPEED_INCREMENT;
        }
    }

    private void draw() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        if (gc == null) return;

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        for (Food food : gameField.getFoodPositions()) {
            java.awt.Color awtColor = food.getType().getColor();
            Color fxColor = Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
            gc.setFill(fxColor);
            gc.fillRect(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.GREEN);
        for (Point segment : snake.getBody()) {
            gc.fillRect(segment.x * CELL_SIZE, segment.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        scoreLabel.setText("Score: " + score);
    }

    private void drawGameOver() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        if (gc == null) return;

        gc.setFill(Color.RED);
        gc.setFont(new Font(30));
        String gameOverText = "Game Over";
        double textWidth = gameOverText.length() * 15;
        gc.fillText(gameOverText,
                (gameCanvas.getWidth() - textWidth) / 2,
                gameCanvas.getHeight() / 2);
    }

    public void handleStartButton(javafx.event.ActionEvent actionEvent) {
        restartGame();
    }

    public void handleExitButton(javafx.event.ActionEvent actionEvent) {
        System.exit(0);
    }
}
