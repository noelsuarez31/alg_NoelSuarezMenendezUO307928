package p3p;

import java.util.*;

public class PuntosDyVTiempos {

    static class Punto {
        double x, y;
        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

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

    public static Par closestPair(List<Punto> puntosPorX) {
        int n = puntosPorX.size();
        if (n <= 3) return fuerzaBruta(puntosPorX);

        int mid = n / 2;
        Punto midPoint = puntosPorX.get(mid);

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

        franja.sort(Comparator.comparingDouble(p -> p.y));

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

    public static void main(String[] args) {
        // Se añaden valores mayores para demostrar el poder de DyV
        int[] tamaños = {512, 1024, 2048, 4096, 8192, 16384, 32768};
        Random rnd = new Random();

        for (int n : tamaños) {
            List<Punto> puntos = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                puntos.add(new Punto(rnd.nextDouble() * 100, rnd.nextDouble() * 100));
            }

            long t1 = System.currentTimeMillis();

            // 1. La ordenación inicial forma parte del tiempo del algoritmo
            puntos.sort(Comparator.comparingDouble(p -> p.x));
            
            // 2. Llamada al algoritmo recursivo
            closestPair(puntos);

            long t2 = System.currentTimeMillis();
            System.out.println("n=" + n + "\tTIEMPO_DyV(ms)=" + (t2 - t1));
        }
    }
}