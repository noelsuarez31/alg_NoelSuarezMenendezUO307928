

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Devorador {
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		try (FileReader reader = new FileReader("grafo.json")) {
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

			if (solucion != null) {
				System.out.println("Solución encontrada: " + solucion);
			} else {
				System.out.println("No se encontró solución.");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
