package org.example.sudokufpoe.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.sudokufpoe.model.SudokuModel;
import javafx.scene.control.Button;

public class SudokuController {

    @FXML
    private GridPane gridSudoku;
    @FXML
    private Button btnJugar;

    @FXML
    public void handleJugar() {
        modelo.limpiarTablero();
        modelo.generarTablero();
        mostrarTablero();
    }

    private TextField[][] celdas = new TextField[6][6];
    // aquí conecto el modelo con el controlador
    private SudokuModel modelo = new SudokuModel();

    @FXML
    public void initialize() {
        crearTableroVisual();
    }

    private void crearTableroVisual() {
        gridSudoku.getColumnConstraints().clear();
        gridSudoku.getRowConstraints().clear();

        for (int i = 0; i < 6; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(60);
            colConst.setMinWidth(60);
            colConst.setMaxWidth(60);
            gridSudoku.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < 6; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(60);
            rowConst.setMinHeight(60);
            rowConst.setMaxHeight(60);
            gridSudoku.getRowConstraints().add(rowConst);
        }

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                TextField celda = new TextField();
                celda.setPrefSize(60, 60);
                celda.setMinSize(60, 60);
                celda.setMaxSize(60, 60);
                celda.setAlignment(Pos.CENTER);

                // Aquí se llama al metodo
                configurarRestriccionEntrada(celda);

                celdas[fila][col] = celda;
                gridSudoku.add(celda, col, fila);
            }
        }
    }

    private void configurarRestriccionEntrada(TextField celda) {
        celda.setTextFormatter(new TextFormatter<>(change -> {
            String nuevoTexto = change.getControlNewText();
            // Permite que la celda se quede vacía o tenga un número del 1 al 6
            if (nuevoTexto.matches("|[1-6]")) {
                return change;
            }
            return null; // Rechaza cualquier otra entrada (letras, otros números, etc.)
        }));
    }

    private void mostrarTablero() {
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                int valor = modelo.getTablero().get(fila).get(col);
                if (valor != 0) {
                    celdas[fila][col].setText(String.valueOf(valor));
                } else {
                    celdas[fila][col].setText("");
                }
                if (modelo.esFija(fila, col)) {
                    celdas[fila][col].setEditable(false);
                    celdas[fila][col].setStyle("-fx-background-color: #e0e0e0;"); // gris para las fijas
                } else {
                    celdas[fila][col].setEditable(true);
                    celdas[fila][col].setStyle("");
                }
            }
        }
    }
}


