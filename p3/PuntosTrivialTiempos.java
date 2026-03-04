package p3p;

import java.util.*;

public class PuntosTrivialTiempos {
    static class Punto {
        double x, y;
        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        // Probamos con los n que pide la práctica (potencias de 2)
        int[] tamaños = {512, 1024, 2048, 4096, 8192};
        Random rnd = new Random();

        for (int n : tamaños) {
            List<Punto> puntos = new ArrayList<>();
            // Generación aleatoria en espacio 100x100
            for (int i = 0; i < n; i++) {
                puntos.add(new Punto(rnd.nextDouble() * 100, rnd.nextDouble() * 100));
            }

            long t1 = System.currentTimeMillis();

            double minDist = Double.MAX_VALUE;
            for (int i = 0; i < puntos.size(); i++) {
                for (int j = i + 1; j < puntos.size(); j++) {
                    double dx = puntos.get(i).x - puntos.get(j).x;
                    double dy = puntos.get(i).y - puntos.get(j).y;
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    if (dist < minDist) {
                        minDist = dist;
                    }
                }
            }

            long t2 = System.currentTimeMillis();
            System.out.println("n=" + n + "\tTIEMPO(ms)=" + (t2 - t1));
        }
    }
}