module org.example.sudokufpoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sudokufpoe to javafx.fxml;
    exports org.example.sudokufpoe;
    exports org.example.sudokufpoe.controller;
    opens org.example.sudokufpoe.controller to javafx.fxml;
}