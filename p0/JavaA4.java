package p0;

public class JavaA4 {

    static void primosA4(int n) {
        boolean[] esPrimo = new boolean[n + 1];

        for (int i = 0; i <= n; i++)
            esPrimo[i] = true;

        int x = 2;
        while (x * x <= n) {
            if (esPrimo[x]) {
                int paso = 2 * x;
                while (paso <= n) {
                    esPrimo[paso] = false;
                    paso += x;
                }
            }
            x++;
        }

        int contPrimos = 0;
        for (int i = 2; i <= n; i++) {
            if (esPrimo[i])
                contPrimos++;
        }

        System.out.println("Hasta " + n + " hay " + contPrimos + " primos");
    }

    public static void main(String[] args) {
        System.out.println("TIEMPOS DEL ALGORITMO A4");
        int n = 5000;

        for (int casos = 0; casos < 15; casos++) {
            long t1 = System.nanoTime();
            primosA4(n);
            long t2 = System.nanoTime();

            System.out.println("n=" + n + " *** tiempo = "
                    + (t2 - t1) / 1_000_000 + " milisegundos");

            n *= 2;
        }
    }
}