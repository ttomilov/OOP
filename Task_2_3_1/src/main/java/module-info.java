module org.main.snake_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.main.snake_game to javafx.fxml;
    exports org.main.snake_game;
}