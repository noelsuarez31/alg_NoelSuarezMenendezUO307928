package p3p;

import java.util.*;
import java.io.*;

public class PuntosDyV {

    private double minDist = Double.POSITIVE_INFINITY;
    private double[][] matrixPuntos;

    // Metodo que lee la matrix

    
    public static void main(String[] args)
    {
        buscarDistanciaMinima();
    }

    public static String[] buscarDistanciaMinima()
    {
        // Ordenar la matrix por x
        Arrays.sort(matrizPuntos, Comparator.comparingDouble(Stream.map(i, matriz -> matriz[i])));

        int izq = 0;
        int der = matrizPuntos.length() -1;

        // Caso base: 2 filas y dos columnas
        if(der - izq == 1)
        {
            // Al acabar la recursividad ya se han comparado los elementos menos el primer corte
            minDistancia = Math.sqrt(dx * dx + dy * dy); // Corregir esto
        }

        int mitad = (izq+der)/2;
        buscarDistanciaMinimaRec(matrizPuntos, izq, mitad);
        buscarDistanciaMinimaRec(matrizPuntos, mitad+1, der);

        // Crear lista de puntos y almacenar valores intermedios

        if(minDistancia > ) // Comparar con la distancia entre los elementos mitad + 1 y mitad

        return minDistancia;
    }
    buscarDistanciaMinimaRec(double[][] matriz, int izq, int der)
    {
        if(der - izq == 1)
        {
            diferencia = Math.sqrt(dx * dx + dy * dy); // Corregir esto
            if(minDist < diferencia)
            {
                
            }
        }

        buscarDistanciaMinimaRec(matrizPuntos, izq, (izq+der)/2);
        buscarDistanciaMinimaRec(matrizPuntos, (izq+der)/2 + 1, der);
    }
    
}