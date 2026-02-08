package p0;

public class JavaA2 {

    static boolean primoA2(int m) {
        for (int i = 2; i < m; i++) {
            if (m % i == 0)
                return false;
        }
        return true;
    }

    static void listadoPrimos(int n) {
        int contPrimos = 0;

        for (int i = 2; i <= n; i++) {
            if (primoA2(i)) {
                contPrimos++;
            }
        }

        System.out.println("Hasta " + n + " hay " + contPrimos + " primos");
    }

    public static void main(String[] args) {
        System.out.println("TIEMPOS DEL ALGORITMO A2");
        int n = 5000;

        for (int casos = 0; casos < 8; casos++) {
            long t1 = System.nanoTime();
            listadoPrimos(n);
            long t2 = System.nanoTime();

            System.out.println("n=" + n + " *** tiempo = "
                    + (t2 - t1) / 1_000_000 + " milisegundos");

            n *= 2;
        }
    }
}