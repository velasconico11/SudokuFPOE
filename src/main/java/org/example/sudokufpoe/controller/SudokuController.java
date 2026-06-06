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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class SudokuController {

    @FXML
    private GridPane gridSudoku;
    @FXML
    private Button btnJugar;
    @FXML
    private Button btnAyuda;

    @FXML
    public void handleJugar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nuevo juego");
        alert.setHeaderText("¿Estás seguro?");
        alert.setContentText("Se va a iniciar un nuevo juego y perderás el progreso actual.");

        // espero la respuesta del usuario
        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            juegoIniciado = true;
            modelo.limpiarTablero();
            modelo.generarTablero();
            mostrarTablero();
        }
    }

    @FXML
    public void handleAyuda() {
        if (!juegoIniciado) {
            return;
        }
        if (modelo.contarCeldasVacias() <= 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ayuda");
            alert.setHeaderText(null);
            alert.setContentText("¡Ya casi terminas, resuelve la última celda tú solo!");
            alert.showAndWait();
            return;
        }

        int[] ayuda = modelo.darAyuda();
        if (ayuda != null) {
            int fila = ayuda[0];
            int col = ayuda[1];
            int numero = ayuda[2];
            celdas[fila][col].setText(String.valueOf(numero));
            celdas[fila][col].setStyle(getEstiloCelda(fila, col) + "-fx-background-color: yellow;");
            modelo.getTablero().get(fila).set(col, numero);
        }
    }
    private TextField[][] celdas = new TextField[6][6];
    // aquí conecto el modelo con el controlador
    private SudokuModel modelo = new SudokuModel();
    private boolean juegoIniciado = false;

    @FXML
    public void initialize() {
        crearTableroVisual();
    }

    private void crearTableroVisual() {
        gridSudoku.getColumnConstraints().clear();
        gridSudoku.getRowConstraints().clear();
        gridSudoku.setMaxSize(360, 360);

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
                celda.setStyle(getEstiloCelda(fila, col));
                final int f = fila;
                final int c = col;
                celda.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.isEmpty()) {
                        // si borra el número, restauro el estilo normal
                        celdas[f][c].setStyle(getEstiloCelda(f, c));
                        modelo.getTablero().get(f).set(c, 0);
                    } else {
                        int numero = Integer.parseInt(newValue);
                        if (modelo.esValidoJugador(numero, f, c)) {
                            // número válido, estilo normal
                            celdas[f][c].setStyle(getEstiloCelda(f, c));
                            modelo.getTablero().get(f).set(c, numero);
                        } else {
                            // número inválido, borde rojo
                            celdas[f][c].setStyle(getEstiloCelda(f, c) + "-fx-border-color: red;");
                        }
                    }
                });

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
    // le doy el estilo de bordes a cada celda según su posición en el bloque
    private String getEstiloCelda(int fila, int col) {
        // bordes normales: arriba, derecha, abajo, izquierda
        int arriba = 1, derecha = 1, abajo = 1, izquierda = 1;

        // si es la última fila de un bloque, el borde de abajo es grueso
        if (fila == 1 || fila == 3) {
            abajo = 3;
        }

        // si es la última columna del bloque izquierdo, el borde derecho es grueso
        if (col == 2) {
            derecha = 3;
        }

        return "-fx-border-color: black; -fx-border-width: "
                + arriba + " " + derecha + " " + abajo + " " + izquierda + ";";
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
                    celdas[fila][col].setStyle(getEstiloCelda(fila, col) + "-fx-background-color: #e0e0e0;");
                } else {
                    celdas[fila][col].setEditable(true);
                    celdas[fila][col].setStyle(getEstiloCelda(fila, col));
                }
            }
        }
    }
}


