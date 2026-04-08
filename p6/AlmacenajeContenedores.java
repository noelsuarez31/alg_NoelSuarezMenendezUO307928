import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores{

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK; // Número mínimo de contenedores

    private List<List<Integer>> mejorDistribucion = new ArrayList<List<Integer>>();

    private int numLlamadas = 0;

    public AlmacenajeContenedores(int c, Integer[] toS) {
        this.capacidadC = c;
        this.conjuntoS = toS;
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());
        this.mejorK = conjuntoS.length;
    }

    public static void main(String[] args){

        try (Scanner sc = new Scanner(new FileReader(args[0]))) {
            int c = sc.nextInt();
            sc.nextLine();
            String[] parts = sc.nextLine().split(" ");
            Integer[] toS = new Integer[parts.length];

            int i = 0;
            for(String s: parts){
                toS[i] = Integer.parseInt(s);
                i++;
            }

            AlmacenajeContenedores sol = new AlmacenajeContenedores(c, toS);
            sol.resolver();
        } catch (NumberFormatException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void resolver() {
        // backtracking
        List<List<Integer>> contenedores = new ArrayList<List<Integer>>();      
        backTracking(0, contenedores);
        // mostrar solucion
        mostrarSolucion();
    }

    private void mostrarSolucion() {
        System.out.println("Lista de contenedores y objetos contenidos:\n");

        int i = 1;
        for (List<Integer> cont : mejorDistribucion) {
            System.out.print("Contenedor " + i + ": ");
            for (Integer obj : cont) {
                System.out.print(obj + " ");
            }
            System.out.println();
            i++;
        }

        System.out.println("\nEl número de contenedores necesario es " + mejorK);
        System.out.println("Número de llamadas recursivas: " + numLlamadas);
    }

    private List<List<Integer>> copiar(List<List<Integer>> contenedores){
        List<List<Integer>> copia = new ArrayList<>();

        for (List<Integer> cont : contenedores) {
            copia.add(new ArrayList<>(cont)); // copia profunda
        }

        return copia;
    }

    private void backTracking(int indexObject, List<List<Integer>> contenedores){
        numLlamadas++;
        // Caso baso (todos los objetos están colocados)
        if(indexObject == conjuntoS.length){ // Aquí compruebas que has llegado al final de la iteración, una solución
            if(contenedores.size() < mejorK){
                mejorK = contenedores.size();
                mejorDistribucion = copiar(contenedores);
            }
            return; // Acaba con la ejecución del backtracking en este punto, si agoto
        }
        // Podamos: si size de contenedores > mejorK
        if(contenedores.size() >= mejorK)
            return; 

        // Probar a meter en contenedores existentes
        for(int i = 0; i < contenedores.size(); i++){
            if(sum(contenedores.get(i)) + conjuntoS[indexObject] <= capacidadC){
                // Avanzar (probar temporalmente a meter un objeto en un contenedor que existe)
                contenedores.get(i).add(conjuntoS[indexObject]);
                backTracking(indexObject+1, contenedores);
                // Si encuentro un estado mejor, lo almaceno
                // Retroceder
                contenedores.get(i).remove(contenedores.get(i).size()-1);
    
            }
        }

        // Intentar meterlo en uno nuevo
        List<Integer> nuevoContenedor = new ArrayList<Integer>();
        nuevoContenedor.add(conjuntoS[indexObject]);
        contenedores.add(nuevoContenedor);
        // Avanzo
        backTracking(indexObject+1, contenedores);
        // Elimino el último
        contenedores.remove(contenedores.size()-1);
        
    }

    private Integer sum(List<Integer> list) {
        Integer sum = 0;
        for(Integer i: list){
            sum += i;
        }
        return sum;
    }
}