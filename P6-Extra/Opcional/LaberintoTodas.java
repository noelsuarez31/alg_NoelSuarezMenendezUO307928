import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LaberintoTodas {

    private int[][] laberinto; // 0 camino, 1 pared, 2 camino recorrido
    private int n = 7;  // Tamaño del laberinto

    private int inicio;
    private int fin;

    private int mejorPasos = Integer.MAX_VALUE;
    private int totalSoluciones = 0;

    private int[][] mejorSolucion;

    public LaberintoTodas(String fichero, int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
        cargarLaberinto(fichero);
    }

    public void resolver() {

        System.out.println("EL LABERINTO ES INICIALMENTE DEL SIGUIENTE MODO:");
        imprimirLaberinto(laberinto);

        System.out.println("\nEl objetivo es ir desde la posición " + inicio + " a la posición " + fin);

        int filaInicio = inicio / n;
        int colInicio = inicio % n;

        backtracking(filaInicio, colInicio, 0);

        System.out.println("\nLA MEJOR SOLUCIÓN TIENE " + mejorPasos + " PASOS, HABIENDO UN TOTAL DE " + totalSoluciones + " SOLUCIONES DIFERENTES");

        imprimirLaberinto(mejorSolucion);
    }

    
    private void backtracking(int fila, int col, int pasos) {

        // Comprobar si es solución (hemos llegado al destino)
        if(fila == fin / n && col == fin % n){
            guardarSolucion(pasos);
            return;
        }

        // Marcar posición como parte del camino (poner 2)
        laberinto[fila][col] = 2;

        // Explorar movimientos (4 direcciones)
        for(int i = 0; i < 4; i++){
            int filaNueva = fila;
            int colNueva = col;
            switch (i) {
                case 0: filaNueva--; break;
                case 1: filaNueva++; break;
                case 2: colNueva--; break;
                case 3: colNueva++; break;
            }
            // Condiciones
            // dentro del tablero
            // no es pared (1)
            // no visitado (2)
            if(esValido(filaNueva, colNueva)){
                laberinto[filaNueva][colNueva] = 2;
                backtracking(filaNueva, colNueva, pasos+1);
                laberinto[filaNueva][colNueva] = 0;
            }
        }

        // 5. Desmarcar (backtracking)
        laberinto[fila][col] = 0;
    }

    private void cargarLaberinto(String fichero) {
        laberinto = new int[n][n];

        try (Scanner sc = new Scanner(new FileReader(fichero))) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    laberinto[i][j] = sc.nextInt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    private boolean esValido(int fila, int col) {
        // comprobar:
        // dentro de límites
        if(fila < 0 || col < 0){
            return false;
        }
        if(fila >= n || col >= n){
            return false;
        }
        // no es pared
        if(laberinto[fila][col] == 1){
            return false;
        }
        // no visitado
        if(laberinto[fila][col] == 2){
            return false;
        }
        return true;
    }

    private void guardarSolucion(int pasos) {
        // incrementar totalSoluciones
        totalSoluciones++;
        // imprimir solución encontrada
        imprimirLaberinto(laberinto);
        // actualizar mejor solución si procede
        if(pasos < mejorPasos){
            mejorPasos = pasos;
            mejorSolucion = copiarLaberinto(laberinto);
        }
    }

    private int[][] copiarLaberinto(int[][] original) {
        int[][] copia = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, n);
        }
        return copia;
    }

    private void imprimirLaberinto(int[][] lab) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(convertir(lab[i][j]) + " ");
            }
            System.out.println();
        }
    }

    private char convertir(int valor) {
        switch (valor) {
            case 0: return '·';
            case 1: return 'H';
            case 2: return '*';
            default: return '?';
        }
    }

    public static void main(String[] args) {

        String fichero = args[0];
        int inicio = Integer.parseInt(args[1]);
        int fin = Integer.parseInt(args[2]);

        LaberintoTodas lab = new LaberintoTodas(fichero, inicio, fin);
        lab.resolver();
    }
}