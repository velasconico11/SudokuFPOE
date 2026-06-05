package org.example.sudokufpoe.model;

import java.util.ArrayList;

public class SudokuModel {
    // aquí guardo el tablero como lista de filas
    private ArrayList<ArrayList<Integer>> tablero;

    // constructor, aquí arranco el tablero vacío
    public SudokuModel(){
        tablero = new ArrayList<>();

        for (int fila = 0; fila < 6; fila++) {
            //Creo cada fila como una lista
            ArrayList<Integer> filaLista = new ArrayList<>();

            for (int col = 0; col < 6; col++){
                filaLista.add(0);
            }
            tablero.add(filaLista);

        }
    }
}
