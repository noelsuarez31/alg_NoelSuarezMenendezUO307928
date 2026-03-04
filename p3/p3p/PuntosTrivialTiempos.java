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
        
        String nombreFichero = args[0];

        List<Punto> puntos = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(nombreFichero));

        int n = Integer.parseInt(br.readLine().trim());

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            double x = Double.parseDouble(partes[0]);
            double y = Double.parseDouble(partes[1]);
            puntos.add(new Punto(x, y));
        }

        br.close();

        if (puntos.size() != n) {
            System.out.println("Advertencia: número de puntos distinto al indicado en la primera línea.");
        }

        long t1 = System.currentTimeMillis();

        double minDist = Double.MAX_VALUE;
        Punto p1 = null, p2 = null;

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

        System.out.println("PUNTOS MAS CERCANOS: [" + p1.x + ", " + p1.y + "] [" 
                           + p2.x + ", " + p2.y + "]");
        System.out.println("SU DISTANCIA MINIMA= " + String.format("%.6f", minDist));
        System.out.println("TIEMPO(ms)= " + (t2 - t1));
    }
}