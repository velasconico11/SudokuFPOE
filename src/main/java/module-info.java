module org.example.sudokufpoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sudokufpoe to javafx.fxml;
    exports org.example.sudokufpoe;
}