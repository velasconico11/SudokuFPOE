package org.example.sudokufpoe.model;

import java.util.ArrayList;
import java.util.Random;

public class SudokuModel {
    // aquí guardo el tablero como lista de filas
    private ArrayList<ArrayList<Integer>> tablero;
    // aquí guardo cuáles celdas son fijas y no se pueden editar
    private ArrayList<ArrayList<Boolean>> celdasFijas;
    private ArrayList<ArrayList<Integer>> solucion;

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
        celdasFijas = new ArrayList<>();

        for (int fila = 0; fila < 6; fila++) {
            ArrayList<Boolean> filaLista = new ArrayList<>();
            for (int col = 0; col < 6; col++) {
                filaLista.add(false);
            }
            celdasFijas.add(filaLista);
        }
        solucion = new ArrayList<>();
        for (int fila = 0; fila < 6; fila++) {
            ArrayList<Integer> filaLista = new ArrayList<>();
            for (int col = 0; col < 6; col++) {
                filaLista.add(0);
            }
            solucion.add(filaLista);
        }
    }
    private boolean resolverTablero() {
        Random random = new Random();
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                if (solucion.get(fila).get(col) == 0) {
                    // creo una lista de números del 1 al 6 en orden aleatorio
                    ArrayList<Integer> numeros = new ArrayList<>();
                    for (int n = 1; n <= 6; n++) numeros.add(n);
                    java.util.Collections.shuffle(numeros, random);

                    for (int numero : numeros) {
                        if (esValidoEnSolucion(numero, fila, col)) {
                            solucion.get(fila).set(col, numero);
                            if (resolverTablero()) return true;
                            solucion.get(fila).set(col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    // reviso si el número es válido en la solución
    private boolean esValidoEnSolucion(int numero, int fila, int col) {
        for (int c = 0; c < 6; c++) {
            if (solucion.get(fila).get(c) == numero) return false;
        }
        for (int f = 0; f < 6; f++) {
            if (solucion.get(f).get(col) == numero) return false;
        }
        int inicioBloqFila = (fila / 2) * 2;
        int inicioBloqCol = (col / 3) * 3;
        for (int f = inicioBloqFila; f < inicioBloqFila + 2; f++) {
            for (int c = inicioBloqCol; c < inicioBloqCol + 3; c++) {
                if (solucion.get(f).get(c) == numero) return false;
            }
        }
        return true;
    }
    public void generarTablero() {
        // primero resuelvo el tablero completo
        resolverTablero();

        // luego copio 2 números por bloque de la solución al tablero
        Random random = new Random();
        for (int bloqFila = 0; bloqFila < 6; bloqFila += 2) {
            for (int bloqCol = 0; bloqCol < 6; bloqCol += 3) {
                int numerosColocados = 0;
                while (numerosColocados < 2) {
                    int fila = bloqFila + random.nextInt(2);
                    int col = bloqCol + random.nextInt(3);
                    if (tablero.get(fila).get(col) == 0) {
                        int numero = solucion.get(fila).get(col);
                        tablero.get(fila).set(col, numero);
                        celdasFijas.get(fila).set(col, true);
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

    // valido el número que ingresa el jugador sin revisar si la celda está ocupada
    public boolean esValidoJugador(int numero, int fila, int col) {

        // reviso que no esté repetido en la fila
        for (int c = 0; c < 6; c++) {
            if (c != col && tablero.get(fila).get(c) == numero) {
                return false; // ya está en la fila
            }
        }

        // reviso que no esté repetido en la columna
        for (int f = 0; f < 6; f++) {
            if (f != fila && tablero.get(f).get(col) == numero) {
                return false; // ya está en la columna
            }
        }

        // encuentro donde empieza el bloque 2x3
        int inicioBloqFila = (fila / 2) * 2;
        int inicioBloqCol = (col / 3) * 3;

        // reviso que no esté repetido en el bloque
        for (int f = inicioBloqFila; f < inicioBloqFila + 2; f++) {
            for (int c = inicioBloqCol; c < inicioBloqCol + 3; c++) {
                if (f != fila && c != col && tablero.get(f).get(c) == numero) {
                    return false; // ya está en el bloque
                }
            }
        }

        return true; // es válido
    }

    public ArrayList<ArrayList<Integer>> getTablero() {
        return tablero;
    }

    // le digo al controlador si una celda es fija o no
    public boolean esFija(int fila, int col) {
        return celdasFijas.get(fila).get(col);
    }

    // limpio el tablero antes de empezar uno nuevo
    public void limpiarTablero() {
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                tablero.get(fila).set(col, 0);
                celdasFijas.get(fila).set(col, false);
            }
        }
    }

    public int[] darAyuda() {
        // junto todas las celdas vacías
        ArrayList<int[]> vacias = new ArrayList<>();
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                if (tablero.get(fila).get(col) == 0) {
                    vacias.add(new int[]{fila, col});
                }
            }
        }
        // la mezclo para que sea aleatoria
        java.util.Collections.shuffle(vacias);

        for (int[] celda : vacias) {
            int fila = celda[0];
            int col = celda[1];
            int numero = solucion.get(fila).get(col);
            return new int[]{fila, col, numero};
        }
        return null;
    }
    // cuento cuántas celdas vacías quedan en el tablero
    public int contarCeldasVacias() {
        int contador = 0;
        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {
                if (tablero.get(fila).get(col) == 0) {
                    contador++;
                }
            }
        }
        return contador;
    }
}
