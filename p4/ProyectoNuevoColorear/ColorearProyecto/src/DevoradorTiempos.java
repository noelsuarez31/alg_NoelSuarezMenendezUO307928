import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DevoradorTiempos {
    
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
    
        int[] n_nodos = {4, 8, 16, 32, 64, 100, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536};
        
        int repeticiones = 1000;

        System.out.println("n\tTiempo total (ms)\tTiempo medio (ms)\tRepeticiones\n");

        for (int n : n_nodos) {
            String rutaArchivo = "src/grafos/g" + n + ".json"; 
            
            try (FileReader reader = new FileReader(rutaArchivo)) {
                JSONObject jsonObject = (JSONObject) parser.parse(reader);
                
                Map<?, ?> grafoLong = (Map<?, ?>) jsonObject.get("grafo");

                Map<String, List<String>> grafo = new HashMap<>();

                for (Object key : grafoLong.keySet()) {
                    String nodoString = String.valueOf(key); 
                    
                    List<?> vecinosLong = (List<?>) grafoLong.get(key);
                    List<String> vecinosString = new ArrayList<>();
                    for (Object vecino : vecinosLong) {
                        vecinosString.add(String.valueOf(vecino));
                    }
                    
                    grafo.put(nodoString, vecinosString);
                }

                Map<String, String> solucion = ColoreoGrafo.realizarVoraz(grafo);
                try (FileWriter file = new FileWriter("solucion.json")) {
                    file.write(new JSONObject(solucion).toJSONString());
                }

                long t = 0;

                for (int i = 1; i <= repeticiones; i++) {
                    long startTime = System.currentTimeMillis();
                    ColoreoGrafo.realizarVoraz(grafo);
                    long endTime = System.currentTimeMillis();
                    t += (endTime - startTime);
                }

                double tiempoMedio = (double) t / repeticiones;

                System.out.println(n + "\t" + t + "\t\t\t" + tiempoMedio + "\t\t\t" + repeticiones);

            }catch (IOException | org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }
    }
}