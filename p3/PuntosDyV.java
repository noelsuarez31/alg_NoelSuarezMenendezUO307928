package p3p;

import java.util.*;
import java.io.*;

public class PuntosDyV {

    static class Punto {
        double x, y;
        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Clase auxiliar para devolver el par y su distancia en la recursividad
    static class Par {
        Punto p1, p2;
        double dist;
        Par(Punto p1, Punto p2, double dist) {
            this.p1 = p1;
            this.p2 = p2;
            this.dist = dist;
        }
    }

    public static double distancia(Punto p1, Punto p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Caso base para 3 o menos puntos
    public static Par fuerzaBruta(List<Punto> puntos) {
        double minDist = Double.MAX_VALUE;
        Punto p1 = null, p2 = null;
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                double d = distancia(puntos.get(i), puntos.get(j));
                if (d < minDist) {
                    minDist = d;
                    p1 = puntos.get(i);
                    p2 = puntos.get(j);
                }
            }
        }
        return new Par(p1, p2, minDist);
    }

    // Algoritmo Divide y Vencerás
    public static Par closestPair(List<Punto> puntosPorX) {
        int n = puntosPorX.size();
        if (n <= 3) {
            return fuerzaBruta(puntosPorX);
        }

        int mid = n / 2;
        Punto midPoint = puntosPorX.get(mid);

        // Dividir
        List<Punto> izq = puntosPorX.subList(0, mid);
        List<Punto> der = puntosPorX.subList(mid, n);

        Par parIzq = closestPair(izq);
        Par parDer = closestPair(der);

        Par minPar = parIzq.dist < parDer.dist ? parIzq : parDer;

        List<Punto> franja = new ArrayList<>();
        for (Punto p : puntosPorX) {
            if (Math.abs(p.x - midPoint.x) < minPar.dist) {
                franja.add(p);
            }
        }

        // Ordenar la franja por la coordenada Y
        franja.sort(Comparator.comparingDouble(p -> p.y));

        // Buscar pares
        for (int i = 0; i < franja.size(); i++) {
            for (int j = i + 1; j < franja.size() && (franja.get(j).y - franja.get(i).y) < minPar.dist; j++) {
                double d = distancia(franja.get(i), franja.get(j));
                if (d < minPar.dist) {
                    minPar = new Par(franja.get(i), franja.get(j), d);
                }
            }
        }

        return minPar;
    }

    public static void main(String[] args) throws Exception {

        List<Punto> puntos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int n = Integer.parseInt(br.readLine().trim());

        // Leer la matriz
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            puntos.add(new Punto(Double.parseDouble(partes[0]), Double.parseDouble(partes[1])));
        }
        br.close();

        // Ordenar la matriz por x
        puntos.sort(Comparator.comparingDouble(p -> p.x));

        // Llamadas recursivas 
        Par resultado = closestPair(puntos);

        // Revisar límite inicial
        System.out.printf("PUNTOS MÁS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n", 
                          resultado.p1.x, resultado.p1.y, resultado.p2.x, resultado.p2.y);
        System.out.printf("SU DISTANCIA MÍNIMA= %.6f%n", resultado.dist);
    }
}