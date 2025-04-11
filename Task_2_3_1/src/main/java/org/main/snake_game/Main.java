package org.main.snake_game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
//        Menu fileMenu = new Menu("File");
//        MenuItem item = new MenuItem("Open food file");
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open food file");
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.txt"));
//        item.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//                fileChooser.showOpenDialog(stage);
//            }
//        });
//        MenuBar menuBar = new MenuBar(fileMenu);
//        menuBar.setTranslateX(2560);
//        menuBar.setTranslateY(1440);
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
