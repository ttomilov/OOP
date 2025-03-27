package org.main.task_2_3_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("snake-view.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        GameController controller = loader.getController();
        controller.setStage(primaryStage);
        primaryStage.show();
    }
}
