package p3p;

import java.util.*;
import java.io.*;

public class PuntosTrivialTiempos {
    static class Punto {
        double x, y;
        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {

        List<Punto> puntos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int n = Integer.parseInt(br.readLine().trim());

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            puntos.add(new Punto(Double.parseDouble(partes[0]), Double.parseDouble(partes[1])));
        }
        br.close();

        double minDist = Double.MAX_VALUE;
        Punto p1 = null, p2 = null;

        long t1 = System.currentTimeMillis();

        // Algoritmo Trivial O(n^2)
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                double dx = puntos.get(i).x - puntos.get(j).x;
                double dy = puntos.get(i).y - puntos.get(j).y;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < minDist) {
                    minDist = dist;
                    p1 = puntos.get(i);
                    p2 = puntos.get(j);
                }
            }
        }

        long t2 = System.currentTimeMillis();
        System.out.println("n=" + n + "\tTIEMPO(ms)=" + (t2 - t1));

        System.out.printf("PUNTOS MÁS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n", 
                          p1.x, p1.y, p2.x, p2.y);
        System.out.printf("SU DISTANCIA MÍNIMA= %.6f%n", minDist);
    }
}