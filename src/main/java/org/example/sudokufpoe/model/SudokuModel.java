package org.example.sudokufpoe.model;

import java.util.ArrayList;
import java.util.Random;

public class SudokuModel {
    // aquí guardo el tablero como lista de filas
    private ArrayList<ArrayList<Integer>> tablero;

    // constructor, aquí arranco el tablero vacío
    public SudokuModel() {
        tablero = new ArrayList<>();

        for (int fila = 0; fila < 6; fila++) {
            //Creo cada fila como una lista
            ArrayList<Integer> filaLista = new ArrayList<>();

            for (int col = 0; col < 6; col++) {
                filaLista.add(0);
            }
            tablero.add(filaLista);
        }

    }

    public void generarTablero() {
        for (int bloqFila = 0; bloqFila < 6; bloqFila += 2) {
            for (int bloqCol = 0; bloqCol < 6; bloqCol += 3) {
                Random random = new Random();
                int numerosColocados = 0;

                while (numerosColocados < 2) {
                    // generar número aleatorio del 1 al 6
                    int numero = random.nextInt(6) + 1;

                    // elegir posición aleatoria dentro del bloque
                    int fila = bloqFila + random.nextInt(2); // 0 o 1 dentro del bloque
                    int col = bloqCol + random.nextInt(3);   // 0, 1 o 2 dentro del bloque
                    if (esValido(numero, fila, col)) {
                        tablero.get(fila).set(col, numero);
                        numerosColocados++;
                    }
                }
            }
        }
    }
    // reviso si el número se puede poner en esa posición
    public boolean esValido(int numero, int fila, int col) {
        if (tablero.get(fila).get(col) != 0) {
            return false;
        }

        // reviso que no esté repetido en la fila
        for (int c = 0; c < 6; c++) {
            if (tablero.get(fila).get(c) == numero) {
                return false; // ya está en la fila, no es válido
            }
        }

        // reviso que no esté repetido en la columna
        for (int f = 0; f < 6; f++) {
            if (tablero.get(f).get(col) == numero) {
                return false; // ya está en la columna, no es válido
            }
        }

        // encuentro donde empieza el bloque 2x3
        int inicioBloqFila = (fila / 2) * 2;
        int inicioBloqCol = (col / 3) * 3;

        // reviso que no esté repetido en el bloque
        for (int f = inicioBloqFila; f < inicioBloqFila + 2; f++) {
            for (int c = inicioBloqCol; c < inicioBloqCol + 3; c++) {
                if (tablero.get(f).get(c) == numero) {
                    return false; // ya está en el bloque, no es válido
                }
            }
        }

        return true; // pasó todas las revisiones, es válido
    }
    public ArrayList<ArrayList<Integer>> getTablero() {
        return tablero;
    }
}