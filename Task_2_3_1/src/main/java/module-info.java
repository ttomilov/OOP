module org.main.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens org.main.task_2_3_1 to javafx.fxml;
    exports org.main.task_2_3_1;
}
