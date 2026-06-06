package org.example.sudokufpoe.model;

import java.util.ArrayList;

/**
 * Interfaz que define el contrato del modelo del juego Sudoku.
 */
public interface ISudoku {
    void generarTablero();
    void limpiarTablero();
    boolean esValido(int numero, int fila, int col);
    boolean esValidoJugador(int numero, int fila, int col);
    boolean esFija(int fila, int col);
    ArrayList<ArrayList<Integer>> getTablero();
    int[] darAyuda();
    int contarCeldasVacias();
}