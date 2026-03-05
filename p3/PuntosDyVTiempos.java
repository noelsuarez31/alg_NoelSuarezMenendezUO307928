package p3p;

import java.io.*;
import java.util.*;

public class PuntosDyVTiempos {

   // Clase punto
    static class Punto {
        double x, y;

        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Clase resultado (la uso para dar el par de puntos buscado)
    static class Resultado {
        double distancia;
        Punto p1, p2;

        Resultado(double distancia, Punto p1, Punto p2) {
            this.distancia = distancia;
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    
    public static void main(String[] args) throws Exception {

        List<Punto> puntos = leerFichero(args[0]);

        // Ordenar por X
        puntos.sort(Comparator.comparingDouble(p -> p.x));

        long t1 = System.currentTimeMillis();

        Resultado res = buscarDistanciaMinima(puntos);

        long t2 = System.currentTimeMillis();

        System.out.println("TIEMPO_DyV(ms)=" + (t2 - t1));
        System.out.printf("PUNTOS MÁS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n",
                res.p1.x, res.p1.y, res.p2.x, res.p2.y);
        System.out.printf("DISTANCIA MÍNIMA = %.6f%n", res.distancia);
    }

    // Método que lee la matriz
    private static List<Punto> leerFichero(String nombre) throws Exception {

        List<Punto> puntos = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(nombre));
        int n = Integer.parseInt(br.readLine().trim());

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            double x = Double.parseDouble(partes[0]);
            double y = Double.parseDouble(partes[1]);
            puntos.add(new Punto(x, y));
        }

        br.close();
        return puntos;
    }

    // Método principal
    private static Resultado buscarDistanciaMinima(List<Punto> puntos) {

        int izq = 0;
        int der = puntos.size() - 1;

        // Primera partición explícita
        int mitad = (izq + der) / 2;
        int indiceCentralIzq = mitad;
        int indiceCentralDer = mitad + 1;


        // Llamadas recursivas
        Resultado izqRes = buscarRec(puntos, izq, mitad);
        Resultado derRes = buscarRec(puntos, mitad + 1, der);

        // Mejor resultado de ambos lados
        Resultado mejor = (izqRes.distancia < derRes.distancia) ? izqRes : derRes;

        // Aquío gracias a la recursividad ya se ha comprobado la menor distancia dentro de cada mitad
        double d = mejor.distancia;

        // Comprobación de la franja de la que hablamos en clase
        // Se comprueba si el último de la primera mitad y el primero de la segunda mitad tienen la menos distancia
        double distCentral = distancia(puntos.get(indiceCentralIzq), puntos.get(indiceCentralDer));
        if (distCentral < d) {
            mejor = new Resultado(distCentral,
                    puntos.get(indiceCentralIzq),
                    puntos.get(indiceCentralDer));
            d = distCentral;
        }

        // Ahora compruebo si la menor distancia está entre un punto de la mitad de la izquierda con uno de la de la derecha
        // Con el strip (ordenado por la cordenaad x) no compruebo todos los puntos, solo los que la distancia en x es menor que d 
        Punto puntoMedio = puntos.get(mitad);
        List<Punto> strip = new ArrayList<>();

        for (int i = izq; i <= der; i++) {
            if (Math.abs(puntos.get(i).x - puntoMedio.x) < d) {
                strip.add(puntos.get(i));
            }
        }

        strip.sort(Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1;
                j < strip.size() &&
                (strip.get(j).y - strip.get(i).y) < d;
                j++) {

                double dist = distancia(strip.get(i), strip.get(j));

                if (dist < mejor.distancia) {
                    mejor = new Resultado(dist, strip.get(i), strip.get(j));
                    d = dist;
                }
            }
        }

        return mejor;
    }

    // Método Divide y Vencerás recursivo
    private static Resultado buscarRec(List<Punto> puntos, int izq, int der) {

        // CASO BASE: 2 filas y dos columnas
        if (der - izq == 1) {
            double dist = distancia(puntos.get(izq), puntos.get(der));
            return new Resultado(dist, puntos.get(izq), puntos.get(der));
        }

        int mitad = (izq + der) / 2;

        Resultado izqRes = buscarRec(puntos, izq, mitad);
        Resultado derRes = buscarRec(puntos, mitad + 1, der);

        return (izqRes.distancia < derRes.distancia) ? izqRes : derRes;
    }

    // Método par calcular la distancia entre dos puntos
    private static double distancia(Punto a, Punto b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}