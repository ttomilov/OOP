package org.main.snake_game;

import javafx.application.Platform;
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
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

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
    private static final int WIN_LENGTH = 30;
    private static final long KEY_PRESS_INTERVAL = 150_000_000;

    private int width;
    private int height;
    private GameField gameField;
    private Snake snake;
    private volatile boolean running;
    private Thread gameThread;
    private long moveInterval;
    private int score;
    private Stage stage;
    private long lastKeyPressTime = 0;

    @FXML
    public void initialize() {
        startButton.setOnAction(e -> restartGame());
        exitButton.setOnAction(e -> System.exit(0));
        fileLoaderButton.setOnAction(e -> onLoadFoodConfig());
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPress);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        stage.setFullScreen(true);
        stage.setResizable(false);
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        width = (int) ((screenWidth * 0.75) / CELL_SIZE);
        height = (int) (screenHeight / CELL_SIZE);

        gameCanvas.setWidth(screenWidth * 0.75);
        gameCanvas.setHeight(screenHeight);
        sidePanel.setPrefWidth(screenWidth * 0.25);
    }

    private void handleKeyPress(KeyEvent event) {
        if (!running || snake == null) return;

        long now = System.nanoTime();
        if (now - lastKeyPressTime < KEY_PRESS_INTERVAL) return;

        lastKeyPressTime = now;

        KeyCode code = event.getCode();
        switch (code) {
            case W -> snake.changeDirection(0, -1);
            case S -> snake.changeDirection(0, 1);
            case A -> snake.changeDirection(-1, 0);
            case D -> snake.changeDirection(1, 0);
        }
    }

    private void onLoadFoodConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose food config");
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String filePath = selectedFile.getAbsolutePath();
                if (gameField != null) {
                    gameField.loadFoodTypesFromFile(filePath);
                } else {
                    GameField.setGlobalFoodFilePath(filePath);
                }
                showInfo("File was successfully loaded: " + filePath);
            } catch (Exception e) {
                showError("ERROR: Failed to load file: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    private void restartGame() {
        stopGameThread();

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

        gameThread = new Thread(() -> {
            while (running) {
                long start = System.nanoTime();

                Platform.runLater(this::updateGame);

                long duration = System.nanoTime() - start;
                long sleepTime = moveInterval - duration;
                if (sleepTime < 0) sleepTime = 0;

                try {
                    sleep(sleepTime / 1_000_000, (int)(sleepTime % 1_000_000));
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        gameThread.setDaemon(true);
        gameThread.start();

        gameCanvas.requestFocus();
    }

    private void stopGameThread() {
        if (gameThread != null && gameThread.isAlive()) {
            gameThread.interrupt();
            try {
                gameThread.join(50);
            } catch (InterruptedException ignored) {}
        }
    }

    private void updateGame() {
        if (!running || snake == null || gameField == null) return;

        if (snake.checkCollision(width, height)) {
            endGame();
            return;
        }

        Point nextHead = snake.getNextHeadPosition();
        Point tail = snake.getBody().getLast();

        Food food = gameField.getFoodAt(nextHead);

        if (food != null) {
            int oldLen = snake.getLength();
            int newLen = food.getType().applyBonus(oldLen);
            snake.growToLength(newLen);
            score++;

            gameField.occupyCell(nextHead);
            gameField.generateFood();
            increaseSpeed();

            if (newLen >= WIN_LENGTH) {
                winGame();
                return;
            }
        } else {
            snake.move();
            gameField.occupyCell(nextHead);
            gameField.releaseCell(tail);
        }

        draw();
    }

    private void endGame() {
        running = false;
        stopGameThread();
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

    private void drawVictory() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        if (gc == null) return;

        gc.setFill(Color.LIMEGREEN);
        gc.setFont(new Font(30));
        String winText = "You Win!";
        double textWidth = winText.length() * 15;
        gc.fillText(winText,
                (gameCanvas.getWidth() - textWidth) / 2,
                gameCanvas.getHeight() / 2);
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

    private void winGame() {
        running = false;
        stopGameThread();
        drawVictory();
    }
}
