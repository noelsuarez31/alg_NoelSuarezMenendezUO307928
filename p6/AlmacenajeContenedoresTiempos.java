import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {

        System.out.println("Test\tTiempo (ms)\n");

        for (int i = 0; i <= 9; i++) {

            String nombreArchivo = String.format("test%02d.txt", i);

            try (Scanner sc = new Scanner(new FileReader(nombreArchivo))) {

                int c = sc.nextInt();

                List<Integer> lista = new ArrayList<>();
                while (sc.hasNextInt()) {
                    lista.add(sc.nextInt());
                }

                Integer[] toS = lista.toArray(new Integer[0]);

                AlmacenajeContenedores sol = new AlmacenajeContenedores(c, toS);

                long t1 = System.currentTimeMillis();
                sol.resolver();
                long t2 = System.currentTimeMillis();

                long tiempo = t2 - t1;

                System.out.println(nombreArchivo + "\t" + tiempo);

            } catch (Exception e) {
                System.out.println("Error con fichero: " + nombreArchivo);
                e.printStackTrace();
            }
        }
    }
}