package org.main.snake_game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/main/snake_game/snake-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        GameController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
